// src/main/java/com/auction/view/ItemDetailView.java
package main.java.com.auction.view;

import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;

public class ItemDetailView extends JFrame {
    private JButton backButton;
    private User user;

    public ItemDetailView(User user, int itemId) {
        this.user = user;
        setTitle("Item Details");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ItemDAO itemDAO = new ItemDAO();
        Item item = itemDAO.getItemById(itemId);

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
    }
}