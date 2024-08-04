// src/main/java/com/auction/view/DashboardView.java
package main.java.com.auction.view;

import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


    public class DashboardView extends JFrame {
        private JButton registerItemButton;
        private JButton listItemsButton;
        private JButton placeBidButton;
        private JButton viewAuctionStatusButton;
        private JButton logoutButton;
        private User user;

        public DashboardView(User user) {
            this.user = user;
            setTitle("Auction Management System - Dashboard");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 1));

            registerItemButton = new JButton("Register Item");
            listItemsButton = new JButton("List Items");
            placeBidButton = new JButton("Place Bid");
            viewAuctionStatusButton = new JButton("View Auction Status");
            logoutButton = new JButton("Logout");

            registerItemButton.addActionListener(new DashboardButtonListener());
            listItemsButton.addActionListener(new DashboardButtonListener());
            placeBidButton.addActionListener(new DashboardButtonListener());
            viewAuctionStatusButton.addActionListener(new DashboardButtonListener());
            logoutButton.addActionListener(e -> {
                new LoginView().setVisible(true);
                dispose();
            });

            if (user.getRole().equals("admin")) {
                panel.add(registerItemButton);
            }
            panel.add(listItemsButton);
            panel.add(placeBidButton);
            panel.add(viewAuctionStatusButton);
            panel.add(logoutButton);

            add(panel);
            if ("admin".equals(user.getRole())) {
                JButton userManagementButton = new JButton("Manage Users");
                userManagementButton.addActionListener(e -> new AdminUserManagementView().setVisible(true));
                panel.add(userManagementButton);
            }

        }

        private class DashboardButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                if (source == registerItemButton) {
                    new RegisterItemView(user).setVisible(true);
                } else if (source == listItemsButton) {
                    new ListItemsView(user).setVisible(true);
                } else if (source == placeBidButton) {
                    new PlaceBidView(user).setVisible(true);
                } else if (source == viewAuctionStatusButton) {
                    new ViewAuctionStatusView(user).setVisible(true);
                }
                dispose();
            }
        }
    }