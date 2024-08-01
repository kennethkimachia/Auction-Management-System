package main.java.com.auction.view;

import main.java.com.auction.controller.BidController;
import main.java.com.auction.controller.ItemController;
import main.java.com.auction.model.Bid;
import main.java.com.auction.model.Item;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ItemDetailView extends JFrame {
    private JButton backButton;
    private User user;
    private ItemController itemController;
    private BidController bidController;

    public ItemDetailView(User user, int itemId) {
        this.user = user;
        this.itemController = new ItemController();
        this.bidController = new BidController();
        setTitle("Item Details");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Item item = itemController.getItemById(itemId);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Item ID:"));
        panel.add(new JLabel(String.valueOf(item.getId())));

        panel.add(new JLabel("Name:"));
        panel.add(new JLabel(item.getName()));

        panel.add(new JLabel("Description:"));
        panel.add(new JLabel(item.getDescription()));

        panel.add(new JLabel("Starting Price:"));
        panel.add(new JLabel(String.valueOf(item.getStartingPrice())));

        panel.add(new JLabel("Auction Status:"));
        panel.add(new JLabel(item.getAuctionStatus()));

        panel.add(new JLabel("Image:"));
        panel.add(new JLabel(new ImageIcon(new ImageIcon(item.getImagePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH))));

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new ListItemsView(user).setVisible(true);
            dispose();
        });

        add(panel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        displayBidHistory(itemId);
        if ("Closed".equals(item.getAuctionStatus())) {
            displayWinnerInfo(itemId);
        }
    }

    private void displayBidHistory(int itemId) {
        List<Bid> bids = bidController.getBidsByItemId(itemId);

        JPanel bidHistoryPanel = new JPanel();
        bidHistoryPanel.setLayout(new BoxLayout(bidHistoryPanel, BoxLayout.Y_AXIS));
        bidHistoryPanel.setBorder(BorderFactory.createTitledBorder("Bid History"));

        for (Bid bid : bids) {
            JPanel bidPanel = new JPanel();
            bidPanel.setLayout(new GridLayout(1, 3));
            bidPanel.add(new JLabel("Bidder: " + bid.getBidderName()));
            bidPanel.add(new JLabel("Bid Amount: $" + bid.getBidAmount()));
            bidPanel.add(new JLabel("Bid Time: " + bid.getBidTime().toString()));
            bidHistoryPanel.add(bidPanel);
        }

        add(new JScrollPane(bidHistoryPanel), BorderLayout.EAST);
    }

    private void displayWinnerInfo(int itemId) {
        List<Bid> bids = bidController.getBidsByItemId(itemId);
        if (!bids.isEmpty()) {
            Bid highestBid = bids.get(bids.size() - 1);
            JPanel winnerPanel = new JPanel();
            winnerPanel.setLayout(new GridLayout(1, 2));
            winnerPanel.setBorder(BorderFactory.createTitledBorder("Winner"));

            winnerPanel.add(new JLabel("Winner: " + highestBid.getBidderName()));
            winnerPanel.add(new JLabel("Winning Bid: $" + highestBid.getBidAmount()));

            add(winnerPanel, BorderLayout.WEST);
        }
    }
}