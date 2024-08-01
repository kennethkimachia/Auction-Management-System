package main.java.com.auction.view;

import main.java.com.auction.controller.ViewAuctionStatusController;
import main.java.com.auction.model.Item;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewAuctionStatusView extends JFrame {
    private User user;
    private JPanel ongoingAuctionsPanel;
    private JPanel closedAuctionsPanel;
    private ViewAuctionStatusController viewAuctionStatusController;

    public ViewAuctionStatusView(User user) {
        this.user = user;
        this.viewAuctionStatusController = new ViewAuctionStatusController();
        initializeUI();
        displayOngoingAuctions();
        displayClosedAuctions();
    }

    private void initializeUI() {
        setTitle("Auction Status");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ongoingAuctionsPanel = new JPanel();
        ongoingAuctionsPanel.setLayout(new BoxLayout(ongoingAuctionsPanel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(ongoingAuctionsPanel), BorderLayout.CENTER);

        closedAuctionsPanel = new JPanel();
        closedAuctionsPanel.setLayout(new BoxLayout(closedAuctionsPanel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(closedAuctionsPanel), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true);
            dispose();
        });
        panel.add(backButton);

        add(panel);
    }

    private void displayOngoingAuctions() {
        List<Item> ongoingAuctions = viewAuctionStatusController.getOngoingAuctions();
        displayItems("Ongoing Auctions", ongoingAuctions, ongoingAuctionsPanel);
    }

    private void displayClosedAuctions() {
        List<Item> closedAuctions = viewAuctionStatusController.getClosedAuctions();
        displayItems("Closed Auctions", closedAuctions, closedAuctionsPanel);
    }

    private void displayItems(String title, List<Item> items, JPanel panel) {
        for (Item item : items) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new GridLayout(1, 2));
            itemPanel.add(new JLabel("Item: " + item.getName()));
            itemPanel.add(new JLabel("Status: " + item.getAuctionStatus()));
            panel.add(itemPanel);
        }
    }

    public static void main(String[] args) {
        // Example usage
        User user = new User("admin", "admin", "admin");
        new ViewAuctionStatusView(user).setVisible(true);
    }
}