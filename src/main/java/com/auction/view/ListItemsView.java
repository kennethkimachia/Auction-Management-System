package main.java.com.auction.view;

import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;
import javax.swing.*;
import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListItemsView extends JFrame {
    private JTable itemsTable;
    private JButton backButton;

    public ListItemsView() {
        setTitle("List Items");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // Add components to list items here

        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = itemDAO.getAllItems();
        String[] columnNames = {"ID", "Name", "Description", "Starting Price", "Auction Status", "Image"};
        Object[][] data = new Object[items.size()][6];

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            data[i][0] = item.getId();
            data[i][1] = item.getName();
            data[i][2] = item.getDescription();
            data[i][3] = item.getStartingPrice();
            data[i][4] = item.getAuctionStatus();
            data[i][5] = new ImageIcon(new ImageIcon(item.getImagePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        }

        itemsTable = new JTable(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? ImageIcon.class : Object.class;
            }
        };

        itemsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = itemsTable.getSelectedRow();
                    int itemId = (int) itemsTable.getValueAt(row, 0);
                    new ItemDetailView(itemId).setVisible(true);
                    dispose();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(itemsTable);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new DashboardView().setVisible(true);
            dispose();
        });

        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
    }

