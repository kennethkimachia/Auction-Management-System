package main.java.com.auction.view;

import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    private User user;
    private JButton manageUsersButton;
    private JButton listItemsButton;
    private JButton registerItemButton;
    private JButton logoutButton;

    public DashboardView(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.setBackground(new Color(230, 240, 255)); // Consistent light blue background

        // Creating and adding buttons with consistent styling
        listItemsButton = createButton("List Items", new Color(70, 130, 180));
        listItemsButton.addActionListener(e -> {
            new ListItemsView(user).setVisible(true);
            dispose();
        });
        mainPanel.add(listItemsButton);

        registerItemButton = createButton("Register Item", new Color(70, 130, 180));
        registerItemButton.addActionListener(e -> {
            new RegisterItemView(user).setVisible(true);
            dispose();
        });
        mainPanel.add(registerItemButton);

        if ("admin".equalsIgnoreCase(user.getRole())) {
            manageUsersButton = createButton("Manage Users", new Color(125, 185, 125));
            manageUsersButton.addActionListener(e -> {
                new ManageUsersView(user).setVisible(true);
                dispose();
            });
            mainPanel.add(manageUsersButton);
        }

        logoutButton = createButton("Logout", new Color(230, 140, 140));
        logoutButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        mainPanel.add(logoutButton);

        add(mainPanel);
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        return button;
    }
}
