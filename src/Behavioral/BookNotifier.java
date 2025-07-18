package Behavioral;

import Creational.*;
import java.util.ArrayList;
import java.util.List;

public class BookNotifier {
    private List<Observer> observers = new ArrayList<>();

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public void bookOverdue(Book book) {
        notifyObservers("Book overdue: " + book.getTitle());
    }

    public void bookNeedsRestoration(Book book) {
        notifyObservers("Book needs restoration: " + book.getTitle());
    }
}
