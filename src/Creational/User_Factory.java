package Creational;

public class User_Factory {
	
	public static User createUser(String role, String name) {
        switch (role.toLowerCase()) {
        	case "librarian":
        		return new Librarian(name);
        	case "scholar":
        		return new Scholar(name);
        	case "guest":
        		return new Guest(name);
        	default:
        		throw new IllegalArgumentException("Invalid user role: " + role);
        }
    }
}


