// src/main/java/com/auction/view/RegistrationView.java
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
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JButton backButton;

    public RegistrationView() {
        setTitle("Register");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"admin", "bidder"});
        panel.add(roleComboBox);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        panel.add(registerButton);

        backButton = new JButton("Back");
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
            String role = (String) roleComboBox.getSelectedItem();
            User user = new User(username, password, role);
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
