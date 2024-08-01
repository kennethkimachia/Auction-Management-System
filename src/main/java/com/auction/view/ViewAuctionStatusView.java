// src/main/java/com/auction/view/ViewAuctionStatusView.java
package main.java.com.auction.view;

import main.java.com.auction.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewAuctionStatusView extends JFrame {
    private JTextField itemIdField;
    private JButton viewStatusButton;
    private JButton backButton;
    private JPanel statusPanel;
    private User user;

    public ViewAuctionStatusView(User user) {
        this.user = user;
        setTitle("View Auction Status");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        panel.add(itemIdField);

        viewStatusButton = new JButton("View Status");
        viewStatusButton.addActionListener(e -> viewAuctionStatus());
        panel.add(viewStatusButton);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true);
            dispose();
        });
        panel.add(backButton);

        add(panel, BorderLayout.NORTH);

        statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(statusPanel), BorderLayout.CENTER);
    }

    private void viewAuctionStatus() {
        int itemId = Integer.parseInt(itemIdField.getText());
        ItemDAO itemDAO = new ItemDAO();
        BidDAO bidDAO = new BidDAO();

        Item item = itemDAO.getItemById(itemId);
        List<Bid> bids = bidDAO.getBidsByItemId(itemId);
        double highestBid = bidDAO.getHighestBid(itemId);

        statusPanel.removeAll();
        statusPanel.add(new JLabel("Item ID: " + item.getId()));
        statusPanel.add(new JLabel("Name: " + item.getName()));
        statusPanel.add(new JLabel("Description: " + item.getDescription()));
        statusPanel.add(new JLabel("Starting Price: $" + item.getStartingPrice()));
        statusPanel.add(new JLabel("Auction Status: " + item.getAuctionStatus()));
        statusPanel.add(new JLabel("Highest Bid: $" + highestBid));

        for (Bid bid : bids) {
            statusPanel.add(new JLabel("Bidder: " + bid.getBidderName() + ", Amount: $" + bid.getBidAmount() + ", Time: " + bid.getBidTime()));
        }

        statusPanel.revalidate();
        statusPanel.repaint();
    }
}