package main.java.com.auction.view;
import main.java.com.auction.model.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        setTitle("Login");
        setSize(350, 200); // Consistent size with the registration form
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel with a grid layout and padding
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(230, 240, 255)); // Consistent background color

        // Adding styled components to the panel
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

        // Login button with custom styling
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180)); // Steel blue
        loginButton.setForeground(Color.WHITE); // White text
        loginButton.addActionListener(new LoginButtonListener());
        panel.add(loginButton);

        // Register button redirects to the registration form
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(230, 140, 140)); // Consistent red button
        registerButton.setForeground(Color.WHITE); // White text
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationView().setVisible(true);
                dispose();
            }
        });
        panel.add(registerButton);

        add(panel);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            UserDAO userDAO = new UserDAO();
            if (userDAO.loginUser(username, password) != null) {
                JOptionPane.showMessageDialog(LoginView.this, "Login successful!");
                new DashboardView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginView.this, "Invalid username or password.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
}
