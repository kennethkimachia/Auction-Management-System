// src/main/java/com/auction/view/ListItemsView.java
package main.java.com.auction.view;

import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListItemsView extends JFrame {
    private JPanel itemsPanel;
    private JTextField searchField;
    private JComboBox<String> statusComboBox;
    private JButton searchButton;
    private JButton backButton;
    private User user;
    private ItemDAO itemDAO;

    public ListItemsView(User user) {
        this.user = user;
        this.itemDAO = new ItemDAO();
        initializeUI();
        displayOngoingAuctions();
        displayClosedAuctions();
    }

    private void initializeUI() {
        setTitle("List Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        searchField = new JTextField(20);
        topPanel.add(searchField);

        statusComboBox = new JComboBox<>(new String[]{"All", "Open", "Closed"});
        topPanel.add(statusComboBox);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        topPanel.add(searchButton);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new DashboardView(user).setVisible(true);
            dispose();
        });
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
    }

    private void displayOngoingAuctions() {
        List<Item> items = itemDAO.getOngoingAuctions();
        displayItems("Ongoing Auctions", items);
    }

    private void displayClosedAuctions() {
        List<Item> items = itemDAO.getClosedAuctions();
        displayItems("Closed Auctions", items);
    }

    private void displayItems(String title, List<Item> items) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder(title));

        JPanel itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));

        for (Item item : items) {
            JPanel itemPanel = createItemPanel(item);
            itemListPanel.add(itemPanel);
        }

        sectionPanel.add(new JScrollPane(itemListPanel), BorderLayout.CENTER);
        itemsPanel.add(sectionPanel);
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

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = searchField.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();
            List<Item> items = itemDAO.searchItems(query, status);
            itemsPanel.removeAll();
            displayItems("Search Results", items);
            itemsPanel.revalidate();
            itemsPanel.repaint();
        }
    }
}
