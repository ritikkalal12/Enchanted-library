package Behavioral;

import Creational.Book;
import Behavioral.*;

public class BorrowCommand implements Command {
	private Book book;
	
	public BorrowCommand(Book book) {
		this.book = book;
	}

	@Override
	public void execute() {
		book.setState(new State_Borrowed());
		System.out.println("✅ Book borrowed: " + book.getTitle());
	}
	
	@Override
	public void undo() {
		book.setState(new State_Available());
		System.out.println("↩️ Undo: Book returned to available: " + book.getTitle());
    }
}