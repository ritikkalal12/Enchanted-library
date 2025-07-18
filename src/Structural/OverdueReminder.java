package Structural;

import Creational.*;

public class OverdueReminder extends BooksDecorator{
	public OverdueReminder(Book book) {
        super(book);
    }

    @Override
    public String getFeatures() {
        return book.getTitle() + " with overdue reminder enabled.";
    }
}
