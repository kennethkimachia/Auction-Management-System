// src/main/java/com/auction/view/ListItemsView.java
package main.java.com.auction.view;

import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListItemsView extends JFrame {
    private JPanel itemsPanel;
    private JButton backButton;
    private User user;

    public ListItemsView(User user) {
        this.user = user;
        setTitle("List Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = itemDAO.getAllItems();

        for (Item item : items) {
            JPanel itemPanel = createItemPanel(item);
            itemsPanel.add(itemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true);
            dispose();
        });

        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    private JPanel createItemPanel(Item item) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("<html><a href=''>" + item.getName() + "</a></html>");
        nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ItemDetailView(user, item.getId()).setVisible(true);
                dispose();
            }
        });

        JLabel descriptionLabel = new JLabel("<html>" + item.getDescription() + "</html>");
        JLabel priceLabel = new JLabel("Starting Price: $" + item.getStartingPrice());
        JLabel statusLabel = new JLabel("Auction Status: " + item.getAuctionStatus());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nameLabel);
        infoPanel.add(descriptionLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(statusLabel);

        itemPanel.add(infoPanel, BorderLayout.CENTER);

        return itemPanel;
    }
}