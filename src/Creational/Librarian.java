package Creational;

public class Librarian implements User {
	private String name;
    private final String role = "Librarian";

    public Librarian(String name) {
        this.name = name;
    }

    @Override
	public void accessLibrary(Book book) {
		System.out.println(name + " (Librarian) has full access.");
	}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return role;
    }

}
