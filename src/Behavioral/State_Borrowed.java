package Behavioral;

import Creational.Book;

public class State_Borrowed implements BookState{
	@Override
    public String getStateName() {
        return "Borrowed";
    }

	@Override
	public void handleState(Book context) {
		//System.out.println(" Book is borrowed.");
	}
}
