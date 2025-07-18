package Application_Run;

import javax.swing.*;
import java.awt.*;
import Creational.UserCatalog;
import Structural.LibraryManagementFacade;

public class RegisterGUI extends JFrame {

    public RegisterGUI() {
        setTitle("Register User");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JPasswordField passField = new JPasswordField();
        String[] roles = {"scholar", "guest", "librarian"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        LibraryManagementFacade Facade;
        JButton registerBtn = new JButton("Register");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Password:"));
        add(passField);
        add(new JLabel("Role:"));
        add(roleBox);
        add(new JLabel(""));
        add(registerBtn);

        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String password = new String(passField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            boolean success = UserCatalog.getInstance().registerUser(name, password, role);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Registration successful. Please login.");
                dispose();
           		LibraryManagementFacade facade = null;
				new UserGUI(facade);
            } else {
                JOptionPane.showMessageDialog(this, "❌ User already exists or error occurred.");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
