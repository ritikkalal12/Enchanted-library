package Creational;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Behavioral.State_Available;
import Behavioral.State_Borrowed;

public class BookCatalog {
    private static BookCatalog instance;
    private static Connection connection;

    // Private constructor for Singleton
    private BookCatalog() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to local MySQL database
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/library",
                "root", // your MySQL username
                "1234" // your MySQL password
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Singleton access method
    public static BookCatalog getInstance() {
        if (instance == null) {
            synchronized (BookCatalog.class) {
                if (instance == null) {
                    instance = new BookCatalog();
                }
            }
        }
        return instance;
    }

    public void addBook(Book book) {
        try {
            String sql = "INSERT INTO books (title, author, isbn, isDigital, lendingRestricted, preservationNote, state) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setBoolean(4, book.isDigital());
            pstmt.setBoolean(5, book.isLendingRestricted());
            pstmt.setString(6, book.getPreservationNote());
            pstmt.setString(7, book.getState()); // Insert state
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }


    // Retrieve a book from the database
    public Book getBook(String title) {
        try {
            String sql = "SELECT * FROM books WHERE title = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, title);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Book book = new Book.BookBuilder(rs.getString("title"), rs.getString("author"), rs.getString("isbn"))
                    .digitalAccess(rs.getBoolean("isDigital"))
                    .lendingRestricted(rs.getBoolean("lendingRestricted"))
                    .preservationNote(rs.getString("preservationNote"))
                    .build();
                
                // Set state from database
                String stateFromDB = rs.getString("state");
                if (stateFromDB != null) {
                    // Depending on how you have implemented your states, set the state here
                    if (stateFromDB.equals("Available")) {
                        book.setState(new State_Available());
                    } else if (stateFromDB.equals("Borrowed")) {
                        book.setState(new State_Borrowed());
                    }
                    // Add more conditions if there are more states
                }
                return book;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving book: " + e.getMessage());
        }
        return null;
    }

    
    public List<Book> listBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book.BookBuilder(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"))
                        .digitalAccess(rs.getBoolean("isDigital"))
                        .lendingRestricted(rs.getBoolean("lendingRestricted"))
                        .preservationNote(rs.getString("preservationNote"))
                        .build();
                
                // Set state
                String stateFromDB = rs.getString("state");
                if (stateFromDB != null && stateFromDB.equalsIgnoreCase("Borrowed")) {
                    book.setState(new State_Borrowed());
                } else {
                    book.setState(new State_Available());
                }

                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error listing books: " + e.getMessage());
        }
        return books;
    }

    public void updateBook(Book book) {
        try {
            String sql = "UPDATE books SET state = ? WHERE title = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, book.getState());  // e.g., "Available", "Borrowed"
            stmt.setString(2, book.getTitle());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No book found with the given title to update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}


	}


