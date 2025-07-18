package Application_Run;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import Creational.*;
import Structural.*;
import Behavioral.*;

public class GUI extends JPanel {

	 private JTextField titleSearchField;
	    private JTable bookTable;
	    private DefaultTableModel tableModel;
	    private LibraryManagementFacade facade;

	    public GUI(LibraryManagementFacade facade) {
	        this.facade = facade;
	        setLayout(new BorderLayout());

	        // Top Panel: Search & Buttons
	        JPanel topPanel = new JPanel(new FlowLayout());
	        titleSearchField = new JTextField(20);
	        JButton searchButton = new JButton("Search by Title");
	        JButton viewAllButton = new JButton("View All Books");
	        JButton addBookButton = new JButton("Add Book");
	        topPanel.add(new JLabel("Title:"));
	        topPanel.add(titleSearchField);
	        topPanel.add(searchButton);
	        topPanel.add(viewAllButton);
	        topPanel.add(addBookButton);
	        add(topPanel, BorderLayout.NORTH);

	        // Center Panel: Book Table
	        String[] columns = {"Title", "Author", "ISBN", "Digital", "Restricted", "Preservation Note", "State"};
	        tableModel = new DefaultTableModel(columns, 0) {
	            @Override public boolean isCellEditable(int row, int col) { return false; }
	        };
	        bookTable = new JTable(tableModel);
	        JScrollPane tableScroll = new JScrollPane(bookTable);
	        add(tableScroll, BorderLayout.CENTER);

	        // Bottom Panel: Borrow/Return
	        JPanel bottomPanel = new JPanel();
	        JButton borrowButton = new JButton("Borrow Book");
	        JButton returnButton = new JButton("Return Book");
	        bottomPanel.add(borrowButton);
	        bottomPanel.add(returnButton);
	        add(bottomPanel, BorderLayout.SOUTH);

	        // Actions
	        searchButton.addActionListener(e -> {
	            String title = titleSearchField.getText().trim();
	            List<Book> result = Collections.emptyList();
	            if (!title.isEmpty()) {
	                Book b = facade.getBookbyTitle(title);
	                if (b != null) result = Collections.singletonList(b);
	            }
	            refreshTable(result);
	        });

	        viewAllButton.addActionListener(e -> {
	            refreshTable(facade.listBooks());
	        });

	        borrowButton.addActionListener(e -> handleStateChange(new State_Borrowed()));
	        returnButton.addActionListener(e -> handleStateChange(new State_Available()));

	        addBookButton.addActionListener(e -> openAddBookDialog());
	    }

	    private void refreshTable(List<Book> books) {
	        tableModel.setRowCount(0);
	        for (Book b : books) {
	            tableModel.addRow(new Object[]{
	                b.getTitle(), b.getAuthor(), b.getIsbn(),
	                b.isDigital() ? "Yes" : "No",
	                b.isLendingRestricted() ? "Yes" : "No",
	                b.getPreservationNote(), b.getState().toString()
	            });
	        }
	    }

	    private void handleStateChange(BookState newState) {
	        int row = bookTable.getSelectedRow();
	        if (row == -1) {
	            JOptionPane.showMessageDialog(this, "Please select a book first.");
	            return;
	        }

	        String title = (String) tableModel.getValueAt(row, 0);
	        System.out.println("Selected Title: " + title);
	        Book book = facade.getBookbyTitle(title);
	        System.out.println("Fetched Book: " + book);

	        if (book != null) {
	            book.setState(newState);
	            facade.updateBook(book);
	            refreshTable(facade.listBooks());
	            JOptionPane.showMessageDialog(this, "Book status updated to: " + newState);
	        } else {
	            JOptionPane.showMessageDialog(this, "Book not found in system.");
	        }
	    }

        
    private void openAddBookDialog() {
        JDialog addBookDialog = new JDialog((Frame) null, "Add New Book", true);
        addBookDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Create input fields
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField isbnField = new JTextField(20);
        JTextField digitalField = new JTextField(5);
        JTextField restrictedField = new JTextField(5);
        JTextField preservationNoteField = new JTextField(20);

        // Row 0: Title
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        // Row 1: Author
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        // Row 2: ISBN
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        panel.add(isbnField, gbc);

        // Row 3: Digital Access
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Is Digital (yes/no):"), gbc);
        gbc.gridx = 1;
        panel.add(digitalField, gbc);

        // Row 4: Restricted
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Restricted (yes/no):"), gbc);
        gbc.gridx = 1;
        panel.add(restrictedField, gbc);

        // Row 5: Preservation Note
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Preservation Note:"), gbc);
        gbc.gridx = 1;
        panel.add(preservationNoteField, gbc);

        // Row 6: Save Button (span two columns)
        JButton saveButton = new JButton("Save Book");
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        // Add action listener
        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            boolean isDigital = digitalField.getText().equalsIgnoreCase("yes");
            boolean isRestricted = restrictedField.getText().equalsIgnoreCase("yes");
            String preservationNote = preservationNoteField.getText();

            Book newBook = new Book.BookBuilder(title, author, isbn)
                                .digitalAccess(isDigital)
                                .lendingRestricted(isRestricted)
                                .preservationNote(preservationNote)
                                .build();
            facade.addBook(newBook);
            addBookDialog.dispose();
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        });

        addBookDialog.getContentPane().add(panel);
        addBookDialog.pack();
        addBookDialog.setLocationRelativeTo(this);
        addBookDialog.setVisible(true);
    }

    private String displayBook(Book book) {
        return " Title: " + book.getTitle() + "\n" +
               " Author: " + book.getAuthor() + "\n" +
               " ISBN: " + book.getIsbn() + "\n" +
               " Digital: " + (book.isDigital() ? "Yes" : "No") + "\n" +
               " Restricted: " + (book.isLendingRestricted() ? "Yes" : "No") + "\n" +
               " Preservation Note: " + book.getPreservationNote() + "\n" +
               " State: " + book.getState();
    }
    
}
