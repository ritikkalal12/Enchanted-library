package Creational;

import Behavioral.BookState;
import Behavioral.State_Available;
import Behavioral.State_Borrowed;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isDigital;
    private boolean lendingRestricted;
    private String preservationNote;
    private BookState state;

    // Private constructor for builder
    private Book(BookBuilder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.isbn = builder.isbn;
        this.isDigital = builder.isDigital;
        this.lendingRestricted = builder.lendingRestricted;
        this.preservationNote = builder.preservationNote;
        this.state = new State_Available();
    }

    public Book(String title2, String author2, String isbn2) {
		this.title = title2;
		this.author = author2;
		this.isbn = isbn2;
	}

	// ✅ Getter methods
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isDigital() {
        return isDigital;
    }

    public boolean isLendingRestricted() {
        return lendingRestricted;
    }

    public String getPreservationNote() {
        return preservationNote;
    }

    public void setState(BookState state) {
        this.state = state;
        state.handleState(this);
    }

    public String getState() {
        return state.getStateName();
    }
    
    // ✅ Builder class
public static class BookBuilder {
        private String title;
        private String author;
        private String isbn;
        private boolean isDigital;
        private boolean lendingRestricted;
        private String preservationNote;
		private BookState state;

        public BookBuilder(String title, String author, String isbn) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
        }

        public BookBuilder digitalAccess(boolean isDigital) {
            this.isDigital = isDigital;
            return this;
        }

        public BookBuilder lendingRestricted(boolean restricted) {
            this.lendingRestricted = restricted;
            return this;
        }

        public BookBuilder preservationNote(String note) {
            this.preservationNote = note;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
