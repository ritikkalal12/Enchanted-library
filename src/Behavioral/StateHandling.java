package Behavioral;

import Creational.Book;

public class StateHandling {
	private BookState state;

	public void setState(BookState state) {
	    this.state = state;
	}

	public BookState getState() {
	    return state;
	}
}

