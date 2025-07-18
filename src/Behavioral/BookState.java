package Behavioral;

import Creational.*;

public interface BookState {
	    void handleState(Book context);
	    String getStateName();
}
