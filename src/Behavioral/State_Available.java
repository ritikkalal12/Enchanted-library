package Behavioral;

import Creational.Book;

public class State_Available implements BookState {
	@Override
	public void handleState(Book context) {
		//System.out.println("Book is available.");
	}

	@Override
	public String getStateName() {
		return "Available";
	}
}
