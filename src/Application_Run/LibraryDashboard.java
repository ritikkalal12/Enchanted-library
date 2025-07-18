package Application_Run;

import javax.swing.*;
import Creational.*;
import Structural.LibraryManagementFacade;

public class LibraryDashboard extends JFrame {
    private LibraryManagementFacade facade;

    public LibraryDashboard(User user) {
        super("📚 Library Dashboard - Welcome " + user.getName() + " (" + user.getRole() + ")");
        facade = new LibraryManagementFacade();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Prevent guests from accessing the library
        if (user.getRole().equalsIgnoreCase("guest")) {
            JOptionPane.showMessageDialog(this, "🚫 Guests are not allowed to access the library.");
            dispose();
            return;
        }

        // Create Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Book Panel (Available to Scholar and Librarian)
        GUI bookPanel = new GUI(facade);
        tabbedPane.addTab("Books", bookPanel);

        // User Panel (Only Librarian)
        if (user.getRole().equalsIgnoreCase("librarian")) {
            UserGUI userPanel = new UserGUI(facade); // Use this for user management
            tabbedPane.addTab("Users", userPanel);
        }

        add(tabbedPane);
        setVisible(true);
    }

    

	// Optional test main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryDashboard(new Scholar("DemoScholar"));
        });
    }
}
