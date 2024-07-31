package main.java.com.auction.view;
import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;


public class RegisterItemView extends JFrame{
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField startingPriceField;
    private JComboBox<String> auctionStatusComboBox;
    private JTextField imagePathField;
    private JButton browseButton;
    private JButton registerButton;
    private JButton backButton;

    public RegisterItemView() {
        setTitle("Register Item");
        setSize(400, 500); // Adjusted size for better layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(230, 240, 255)); // Light blue background

        // Creating sections
        mainPanel.add(createSection("Item Name:", nameField = new JTextField()));
        mainPanel.add(createSection("Description:", descriptionField = new JTextField()));
        mainPanel.add(createSection("Starting Price:", startingPriceField = new JTextField()));
        mainPanel.add(createSection("Auction Status:", auctionStatusComboBox = new JComboBox<>(new String[]{"Open", "Closed"})));
        mainPanel.add(createImagePathSection());
        mainPanel.add(createButtonPanel());

        add(mainPanel);
    }

    private JPanel createSection(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(new Color(50, 50, 150)); // Dark blue text
        panel.add(jLabel, BorderLayout.WEST);
        component.setForeground(new Color(80, 80, 80)); // Dark gray text
        component.setBackground(new Color(255, 255, 255)); // White background
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createImagePathSection() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel("Image Path:");
        label.setForeground(new Color(50, 50, 150)); // Dark blue text
        imagePathField = new JTextField();
        imagePathField.setEditable(false);
        imagePathField.setBackground(new Color(255, 255, 255)); // White background
        panel.add(label, BorderLayout.WEST);
        panel.add(imagePathField, BorderLayout.CENTER);

        browseButton = new JButton("Browse...");
        browseButton.setBackground(new Color(70, 130, 180)); // Steel blue
        browseButton.setForeground(Color.WHITE); // White text
        browseButton.addActionListener(this::browseForImage);
        panel.add(browseButton, BorderLayout.EAST);

        return panel;
    }

    private void browseForImage(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(RegisterItemView.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(125, 185, 125)); // Green button
        registerButton.setForeground(Color.WHITE); // White text
        registerButton.addActionListener(new RegisterButtonListener());

        backButton = new JButton("Back");
        backButton.setBackground(new Color(230, 140, 140)); // Red button
        backButton.setForeground(Color.WHITE); // White text
        backButton.addActionListener(e -> {
            new DashboardView().setVisible(true);
            dispose();
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        return buttonPanel;
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
                new DashboardView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(RegisterItemView.this, "Failed to register item.");
            }
        }
    }

}
