package Application_Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Creational.*;
import Structural.*;

public class UserGUI extends JPanel {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public UserGUI(LibraryManagementFacade facade) {

        // Panel setup
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setVisible(true);

        // 🔐 Login Action
        loginButton.addActionListener(e -> {
            String name = nameField.getText();
            String password = new String(passwordField.getPassword());

            UserCatalog userCatalog = UserCatalog.getInstance();
            User user = userCatalog.authenticateUser(name, password);

            if (user != null) {
                if (user.getRole().equalsIgnoreCase("guest")) {
                    JOptionPane.showMessageDialog(this, "🚫 Guests are not allowed to access the library.");
                } else {
                    //this.dispose(); // Close login window
                    new LibraryDashboard(user); // Open dashboard
                }
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid username or password.");
            }
        });

        // 📝 Register Action
        registerButton.addActionListener(e -> {
            //this.dispose(); // Close login
            new RegisterGUI(); // Open registration window
        });
    }

}
