package main.java.com.auction.view;

import main.java.com.auction.controller.RegistrationController;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistrationView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JButton backButton;
    private RegistrationController registrationController;

    public RegistrationView() {
        this.registrationController = new RegistrationController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Register");
        setSize(350, 300); // Adjusted size for better layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(230, 240, 255)); // Consistent light blue background

        // Adding sections for user inputs
        mainPanel.add(createInputSection("Username:", usernameField = new JTextField()));
        mainPanel.add(createInputSection("Password:", passwordField = new JPasswordField()));
        mainPanel.add(createInputSection("Role:", roleComboBox = new JComboBox<>(new String[]{"admin", "bidder"})));

        // Adding buttons with custom styling
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false); // Transparent background
        registerButton = createButton("Register", new Color(125, 185, 125));
        registerButton.addActionListener(this::handleRegister);
        buttonPanel.add(registerButton);

        backButton = createButton("Back", new Color(230, 140, 140));
        backButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel);
        add(mainPanel);
    }

    private JPanel createInputSection(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(50, 50, 150)); // Dark blue text
        panel.add(label, BorderLayout.WEST);
        component.setForeground(new Color(80, 80, 80)); // Dark gray text
        component.setBackground(new Color(255, 255, 255)); // White background
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void handleRegister(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.");
            return;
        }

        User newUser = new User(username, password, role);
        if (registrationController.registerUser(newUser)) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginView().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationView().setVisible(true));
    }
}
