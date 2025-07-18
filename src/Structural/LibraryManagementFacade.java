package Structural;

import java.util.List;

import Creational.*;


public class LibraryManagementFacade {

    private BookCatalog bookCatalog;
    private UserCatalog userCatalog;

    public LibraryManagementFacade() {
        this.bookCatalog = BookCatalog.getInstance();
        this.userCatalog = UserCatalog.getInstance();
    }

    public void addBook(Book book) {
        bookCatalog.addBook(book);
    }
    
    public Book getBookbyTitle(String title) {
        return bookCatalog.getBook(title);
    }

    // ✅ Add a user
    public void addUser(User user) {
        userCatalog.addUser(user);
    }

    public User getUser(String username) {
        return userCatalog.getUser(username);
    }
    
    public List<Book> listBooks() {
        return bookCatalog.listBooks();
    }

    public String listUsers() {
        return userCatalog.listUsers();
    }

	public void updateBook(Book book) {
		bookCatalog.updateBook(book);
	}
}
