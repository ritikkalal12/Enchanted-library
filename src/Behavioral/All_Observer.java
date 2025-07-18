package Behavioral;

public class All_Observer implements Observer {

	private String name;

    public All_Observer(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Scholar" + name + " notified: " + message);
    }
}
