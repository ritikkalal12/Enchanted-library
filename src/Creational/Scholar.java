package Creational;

public class Scholar implements User{
	private String name;
    private final String role = "Librarian";

    public Scholar(String name) {
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
		System.out.println(name + " (Librarian) has full access.");
	}

}
