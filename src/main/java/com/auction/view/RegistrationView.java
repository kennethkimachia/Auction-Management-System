package main.java.com.auction.view;
import main.java.com.auction.model.User;
import main.java.com.auction.model.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegistrationView() {
        setTitle("Register");
        setSize(350, 200); // Slightly larger to accommodate better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creating a panel with a bit of padding
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set a background color for the panel
        panel.setBackground(new Color(230, 240, 255)); // Light blue background

        // Labels and text fields with custom fonts and foreground colors
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(50, 50, 150)); // Dark blue text
        usernameField = new JTextField();
        usernameField.setForeground(new Color(80, 80, 80)); // Dark gray text
        usernameField.setBackground(new Color(255, 255, 255)); // White background
        panel.add(usernameLabel);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(50, 50, 150)); // Dark blue text
        passwordField = new JPasswordField();
        passwordField.setForeground(new Color(80, 80, 80)); // Dark gray text
        passwordField.setBackground(new Color(255, 255, 255)); // White background
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Buttons with custom styling
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(125, 185, 125)); // Green button
        registerButton.setForeground(Color.WHITE); // White text
        registerButton.addActionListener(new RegisterButtonListener());
        panel.add(registerButton);

        backButton = new JButton("Back");
        backButton.setBackground(new Color(230, 140, 140)); // Red button
        backButton.setForeground(Color.WHITE); // White text
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView().setVisible(true);
                dispose();
            }
        });
        panel.add(backButton);

        add(panel);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = new User(username, password);
            UserDAO userDAO = new UserDAO();
            if (userDAO.registerUser(user)) {
                JOptionPane.showMessageDialog(RegistrationView.this, "Registration successful!");
                new LoginView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(RegistrationView.this, "Registration failed. Username might already exist.");
            }
        }
    }

}
