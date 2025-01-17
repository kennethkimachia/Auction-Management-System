// src/main/java/com/auction/view/LoginView.java
package main.java.com.auction.view;

import main.java.com.auction.model.Notification;
import main.java.com.auction.model.NotificationDAO;
import main.java.com.auction.model.User;
import main.java.com.auction.model.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        panel.add(loginButton);

        registerButton = new JButton("Register");
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
            User user = userDAO.loginUser(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(LoginView.this, "Login successful!");
                showNotifications(user);
                new DashboardView(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginView.this, "Invalid username or password.");
            }
        }
    }

    private void showNotifications(User user) {
        NotificationDAO notificationDAO = new NotificationDAO();
        List<Notification> notifications = notificationDAO.getNotificationsByUserId(user.getId());
        if (!notifications.isEmpty()) {
            StringBuilder message = new StringBuilder("You have the following notifications:\n\n");
            for (Notification notification : notifications) {
                message.append(notification.getMessage()).append("\n");
                notificationDAO.markNotificationAsRead(notification.getId());
            }
            JOptionPane.showMessageDialog(LoginView.this, message.toString(), "Notifications", JOptionPane.INFORMATION_MESSAGE);
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
