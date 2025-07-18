package Structural;

import Creational.*;

public class RestrictedSection extends BooksDecorator {
	public RestrictedSection(Book book) {
		super(book);
	}

	@Override
	public String getFeatures() {
		return book.getTitle() + " is in the restricted section.";
	}
}
