package main.java.com.auction.view;
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

    public DashboardView() {
        setTitle("Auction Management System - Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with padding and layout adjustments
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(230, 240, 255)); // Consistent background color

        // Styling for buttons
        registerItemButton = styledButton("Register Item", new Color(70, 130, 180));
        listItemsButton = styledButton("List Items", new Color(70, 130, 180));
        placeBidButton = styledButton("Place Bid", new Color(70, 130, 180));
        viewAuctionStatusButton = styledButton("View Auction Status", new Color(70, 130, 180));
        logoutButton = styledButton("Logout", new Color(230, 140, 140));

        // Adding action listeners
        registerItemButton.addActionListener(new DashboardButtonListener());
        listItemsButton.addActionListener(new DashboardButtonListener());
        placeBidButton.addActionListener(new DashboardButtonListener());
        viewAuctionStatusButton.addActionListener(new DashboardButtonListener());
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        panel.add(registerItemButton);
        panel.add(listItemsButton);
        panel.add(placeBidButton);
        panel.add(viewAuctionStatusButton);
        panel.add(logoutButton);

        add(panel);
    }

    private JButton styledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private class DashboardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == registerItemButton) {
                new RegisterItemView().setVisible(true);
            } else if (source == listItemsButton) {
                new ListItemsView().setVisible(true);
            } else if (source == placeBidButton) {
                new PlaceBidView().setVisible(true);
            } else if (source == viewAuctionStatusButton) {
                new ViewAuctionStatusView().setVisible(true);
            }
            dispose();
        }
    }
}
