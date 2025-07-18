package Structural;

import Creational.*;

public abstract class BooksDecorator extends Book {
    protected Book book;

    public BooksDecorator(Book book) {
        super(book.getTitle(), book.getAuthor(), book.getIsbn());
        this.book = book;
    }

    public abstract String getFeatures();
}
