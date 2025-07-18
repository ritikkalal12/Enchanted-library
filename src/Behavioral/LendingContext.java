package Behavioral;

import Creational.*;

public class LendingContext {
	 private LendingStrategy strategy;

	    public void setStrategy(LendingStrategy strategy) {
	        this.strategy = strategy;
	    }

	    public void executeStrategy(Book book, User user) {
	        strategy.lendBook(book, user);
	    }
}

class AcademicLending implements LendingStrategy {
    @Override
    public void lendBook(Book book, User user) {
        System.out.println(user.getName() + " borrowed (Academic Rules): " + book.getTitle());
    }
}

class PublicLending implements LendingStrategy {
    @Override
    public void lendBook(Book book, User user) {
        System.out.println(user.getName() + " borrowed (Public Rules): " + book.getTitle());
    }
}

class ReadingRoomLending implements LendingStrategy {
    @Override
    public void lendBook(Book book, User user) {
        System.out.println(book.getTitle() + " can only be read in the restricted reading room.");
    }
}
