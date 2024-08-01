package main.java.com.auction.view;

import main.java.com.auction.controller.LoginController;
import main.java.com.auction.controller.NotificationController;
import main.java.com.auction.model.Notification;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private LoginController loginController;
    private NotificationController notificationController;

    public LoginView() {
        this.loginController = new LoginController();
        this.notificationController = new NotificationController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login");
        setSize(350, 220); // Slightly larger for aesthetic spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(230, 240, 255)); // Consistent light blue background

        // Adding sections for user inputs
        mainPanel.add(createInputSection("Username:", usernameField = new JTextField()));
        mainPanel.add(createInputSection("Password:", passwordField = new JPasswordField()));

        // Adding buttons with custom styling
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false); // Transparent background
        loginButton = createButton("Login", new Color(70, 130, 180));
        loginButton.addActionListener(this::handleLogin);
        buttonPanel.add(loginButton);

        registerButton = createButton("Register", new Color(230, 140, 140));
        registerButton.addActionListener(e -> {
            new RegistrationView().setVisible(true);
            dispose();
        });
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel);
        add(mainPanel);
    }

    private JPanel createInputSection(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(50, 50, 150)); // Dark blue text
        panel.add(label, BorderLayout.WEST);
        textField.setForeground(new Color(80, 80, 80)); // Dark gray text
        textField.setBackground(new Color(255, 255, 255)); // White background
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User user = loginController.loginUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            showNotifications(user);
            new DashboardView(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private void showNotifications(User user) {
        List<Notification> notifications = notificationController.getNotificationsByUserId(user.getId());
        if (!notifications.isEmpty()) {
            StringBuilder message = new StringBuilder("You have the following notifications:\n\n");
            for (Notification notification : notifications) {
                message.append(notification.getMessage()).append("\n");
                notificationController.markNotificationAsRead(notification.getId());
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Notifications", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
