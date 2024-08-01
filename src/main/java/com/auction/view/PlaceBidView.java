package main.java.com.auction.view;

import main.java.com.auction.controller.PlaceBidController;
import main.java.com.auction.model.Bid;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceBidView extends JFrame {
    private User user;
    private int itemId;
    private JTextField bidAmountField;
    private JButton placeBidButton;
    private JButton backButton;
    private PlaceBidController placeBidController;

    public PlaceBidView(User user, int itemId) {
        this.user = user;
        this.itemId = itemId;
        this.placeBidController = new PlaceBidController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Place Bid");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

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
            String bidAmountText = bidAmountField.getText().replaceAll("[^\\d.]", "");
            double bidAmount = Double.parseDouble(bidAmountText);
            Bid bid = new Bid(itemId, user.getUsername(), bidAmount);

            if (placeBidController.placeBid(bid)) {
                JOptionPane.showMessageDialog(PlaceBidView.this, "Bid placed successfully!");
                new DashboardView(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(PlaceBidView.this, "Failed to place bid.");
            }
        }
    }

    public static void main(String[] args) {
        // Example usage
        User user = new User("bidder", "password", "bidder");
        new PlaceBidView(user, 1).setVisible(true);
    }
}