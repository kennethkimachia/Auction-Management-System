package main.java.com.auction.view;

import main.java.com.auction.controller.UserController;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageUsersView extends JFrame {
    private User user;
    private JTable usersTable;
    private JButton backButton;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private UserController userController;

    public ManageUsersView(User user) {
        this.user = user;
        this.userController = new UserController();
        setTitle("Manage Users");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        usersTable = new JTable();
        updateUsersTable();

        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new AddUserButtonListener());
        buttonPanel.add(addUserButton);

        editUserButton = new JButton("Edit User");
        editUserButton.addActionListener(new EditUserButtonListener());
        buttonPanel.add(editUserButton);

        deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(new DeleteUserButtonListener());
        buttonPanel.add(deleteUserButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true);
            dispose();
        });
        panel.add(backButton, BorderLayout.NORTH);

        add(panel);
    }

    public void updateUsersTable() {
        List<User> users = userController.getAllUsers();
        String[] columnNames = {"ID", "Username", "Role"};
        Object[][] data = new Object[users.size()][3];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i][0] = user.getId();
            data[i][1] = user.getUsername();
            data[i][2] = user.getRole();
        }
        usersTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private class AddUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddEditUserView(user, null, ManageUsersView.this).setVisible(true);
        }
    }

    private class EditUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = usersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) usersTable.getValueAt(selectedRow, 0);
                User selectedUser = userController.getUserById(userId);
                new AddEditUserView(user, selectedUser, ManageUsersView.this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(ManageUsersView.this, "Please select a user to edit.");
            }
        }
    }

    private class DeleteUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = usersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) usersTable.getValueAt(selectedRow, 0);
                int confirmation = JOptionPane.showConfirmDialog(ManageUsersView.this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    userController.deleteUser(userId);
                    updateUsersTable();
                }
            } else {
                JOptionPane.showMessageDialog(ManageUsersView.this, "Please select a user to delete.");
            }
        }
    }
}