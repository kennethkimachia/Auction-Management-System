package main.java.com.auction.view;

import main.java.com.auction.model.User;
import main.java.com.auction.model.UserDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdminUserManagementView extends JFrame {
    private JTable usersTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private UserDAO userDAO;

    public AdminUserManagementView() {
        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        userDAO = new UserDAO();
        initUI();
    }

    private void initUI() {
        usersTable = new JTable();
        usersTable.setModel(new DefaultTableModel(new Object[]{"ID", "Username", "Role"}, 0));
        refreshUserTable();
        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add User");
        updateButton = new JButton("Update User");
        deleteButton = new JButton("Delete User");

        addButton.addActionListener(this::addUserAction);
        updateButton.addActionListener(this::updateUserAction);
        deleteButton.addActionListener(this::deleteUserAction);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addUserAction(ActionEvent e) {
        JDialog addUserDialog = new JDialog(this, "Add New User", true);
        addUserDialog.setLayout(new GridLayout(5, 2));
        addUserDialog.setSize(300, 200);
        addUserDialog.setLocationRelativeTo(this);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "bidder"});
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        addUserDialog.add(new JLabel("Username:"));
        addUserDialog.add(usernameField);
        addUserDialog.add(new JLabel("Password:"));
        addUserDialog.add(passwordField);
        addUserDialog.add(new JLabel("Role:"));
        addUserDialog.add(roleComboBox);
        addUserDialog.add(saveButton);
        addUserDialog.add(cancelButton);

        saveButton.addActionListener(ev -> {
            User user = new User(usernameField.getText(), new String(passwordField.getPassword()), (String) roleComboBox.getSelectedItem());
            if (userDAO.registerUser(user)) {
                JOptionPane.showMessageDialog(addUserDialog, "User added successfully!");
                refreshUserTable();
                addUserDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(addUserDialog, "Failed to add user.");
            }
        });

        cancelButton.addActionListener(ev -> addUserDialog.dispose());

        addUserDialog.setVisible(true);
    }

    private void updateUserAction(ActionEvent e) {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user to update.");
            return;
        }

        int userId = (Integer) usersTable.getValueAt(selectedRow, 0);
        User user = userDAO.getUserById(userId);  // Assuming you have this method in UserDAO

        JDialog updateUserDialog = new JDialog(this, "Update User", true);
        updateUserDialog.setLayout(new GridLayout(5, 2));
        updateUserDialog.setSize(300, 200);
        updateUserDialog.setLocationRelativeTo(this);

        JTextField usernameField = new JTextField(user.getUsername());
        JPasswordField passwordField = new JPasswordField(user.getPassword());
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "bidder"});
        roleComboBox.setSelectedItem(user.getRole());
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        updateUserDialog.add(new JLabel("Username:"));
        updateUserDialog.add(usernameField);
        updateUserDialog.add(new JLabel("Password:"));
        updateUserDialog.add(passwordField);
        updateUserDialog.add(new JLabel("Role:"));
        updateUserDialog.add(roleComboBox);
        updateUserDialog.add(updateButton);
        updateUserDialog.add(cancelButton);

        updateButton.addActionListener(ev -> {
            user.setUsername(usernameField.getText());
            user.setPassword(new String(passwordField.getPassword()));
            user.setRole((String) roleComboBox.getSelectedItem());
            if (userDAO.updateUser(user)) {
                JOptionPane.showMessageDialog(updateUserDialog, "User updated successfully!");
                refreshUserTable();
                updateUserDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(updateUserDialog, "Failed to update user.");
            }
        });

        cancelButton.addActionListener(ev -> updateUserDialog.dispose());

        updateUserDialog.setVisible(true);
    }

    private void deleteUserAction(ActionEvent e) {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }
        int userId = (Integer) usersTable.getValueAt(selectedRow, 0);
        if (userDAO.deleteUser(userId)) {
            JOptionPane.showMessageDialog(this, "User deleted successfully.");
            refreshUserTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete user.");
        }
    }

    private void refreshUserTable() {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            model.addRow(new Object[]{user.getId(), user.getUsername(), user.getRole()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminUserManagementView().setVisible(true));
    }
}
