// src/main/java/com/auction/view/PlaceBidView.java
package main.java.com.auction.view;

import main.java.com.auction.model.Bid;
import main.java.com.auction.model.BidDAO;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceBidView extends JFrame {
    private JTextField itemIdField;
    private JTextField bidderNameField;
    private JTextField bidAmountField;
    private JButton placeBidButton;
    private JButton backButton;
    private User user;

    public PlaceBidView(User user) {
        this.user = user;
        setTitle("Place Bid");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        panel.add(itemIdField);

        panel.add(new JLabel("Bidder Name:"));
        bidderNameField = new JTextField();
        panel.add(bidderNameField);

        panel.add(new JLabel("Bid Amount:"));
        bidAmountField = new JTextField();
        panel.add(bidAmountField);

        placeBidButton = new JButton("Place Bid");
        placeBidButton.addActionListener(new PlaceBidButtonListener());
        panel.add(placeBidButton);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true);
            dispose();
        });
        panel.add(backButton);

        add(panel);
    }

    private class PlaceBidButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int itemId = Integer.parseInt(itemIdField.getText());
            String bidderName = bidderNameField.getText();
            double bidAmount = Double.parseDouble(bidAmountField.getText());

            BidDAO bidDAO = new BidDAO();
            double highestBid = bidDAO.getHighestBid(itemId);

            if (bidAmount > highestBid) {
                Bid bid = new Bid(itemId, bidderName, bidAmount);
                if (bidDAO.placeBid(bid)) {
                    JOptionPane.showMessageDialog(PlaceBidView.this, "Bid placed successfully!");
                    notifyUser("You have placed a bid of $" + bidAmount + " on item " + itemId);
                } else {
                    JOptionPane.showMessageDialog(PlaceBidView.this, "Failed to place bid.");
                }
            } else {
                JOptionPane.showMessageDialog(PlaceBidView.this, "Bid amount must be higher than the current highest bid.");
            }
        }

        private void notifyUser(String message) {
            JOptionPane.showMessageDialog(PlaceBidView.this, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
