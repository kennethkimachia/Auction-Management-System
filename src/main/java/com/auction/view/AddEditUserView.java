package main.java.com.auction.view;

import main.java.com.auction.controller.UserController;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditUserView extends JFrame {
    private User user;
    private User editingUser;
    private ManageUsersView manageUsersView;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private UserController userController;

    public AddEditUserView(User user, User editingUser, ManageUsersView manageUsersView) {
        this.user = user;
        this.editingUser = editingUser;
        this.manageUsersView = manageUsersView;
        this.userController = new UserController();
        setTitle(editingUser == null ? "Add User" : "Edit User");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener());
        panel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);

        if (editingUser != null) {
            usernameField.setText(editingUser.getUsername());
            passwordField.setText(editingUser.getPassword());
            roleComboBox.setSelectedItem(editingUser.getRole());
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(AddEditUserView.this, "Username and password cannot be empty.");
                return;
            }

            if (editingUser == null) {
                User newUser = new User(username, password, role);
                userController.addUser(newUser);
            } else {
                editingUser.setUsername(username);
                editingUser.setPassword(password);
                editingUser.setRole(role);
                userController.updateUser(editingUser);
            }

            manageUsersView.updateUsersTable();
            dispose();
        }
    }
}