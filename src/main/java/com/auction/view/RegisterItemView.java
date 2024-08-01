// src/main/java/com/auction/view/RegisterItemView.java
package main.java.com.auction.view;

import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;
import main.java.com.auction.model.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RegisterItemView extends JFrame {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField startingPriceField;
    private JComboBox<String> auctionStatusComboBox;
    private JTextField imagePathField;
    private JButton browseButton;
    private JButton registerButton;
    private JButton backButton;
    private User user;

    public RegisterItemView(User user) {
        this.user = user;
        setTitle("Register Item");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        panel.add(new JLabel("Starting Price:"));
        startingPriceField = new JTextField();
        panel.add(startingPriceField);

        panel.add(new JLabel("Auction Status:"));
        auctionStatusComboBox = new JComboBox<>(new String[]{"Open", "Closed"});
        panel.add(auctionStatusComboBox);

        panel.add(new JLabel("Image Path:"));
        imagePathField = new JTextField();
        imagePathField.setEditable(false);
        panel.add(imagePathField);

        browseButton = new JButton("Browse...");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
                int result = fileChooser.showOpenDialog(RegisterItemView.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        panel.add(browseButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        panel.add(registerButton);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DashboardView(user).setVisible(true);
                dispose();
            }
        });
        panel.add(backButton);

        add(panel);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String startingPriceText = startingPriceField.getText().replaceAll("[^\\d.]", ""); // Remove non-numeric characters
            double startingPrice = Double.parseDouble(startingPriceText);
            String auctionStatus = (String) auctionStatusComboBox.getSelectedItem();
            String imagePath = imagePathField.getText();

            Item item = new Item(name, description, startingPrice, auctionStatus, imagePath);
            ItemDAO itemDAO = new ItemDAO();

            if (itemDAO.registerItem(item)) {
                JOptionPane.showMessageDialog(RegisterItemView.this, "Item registered successfully!");
                new DashboardView(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(RegisterItemView.this, "Failed to register item.");
            }
        }
    }
}
