package Creational;

import Structural.*;
public class Guest implements User{

	private String name;
    private final String role = "Librarian";

    public Guest(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return role;
    }

	@Override
	public void accessLibrary(Book book) {
		System.out.println("🔒 Accessing as Guest: Limited access to book.");
        Book restrictedBook = new RestrictedSection(book);
        System.out.println(((RestrictedSection)restrictedBook).getFeatures());
	}

}
