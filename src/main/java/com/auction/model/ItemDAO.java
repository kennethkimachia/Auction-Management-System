// src/main/java/com/auction/model/ItemDAO.java
package main.java.com.auction.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public boolean registerItem(Item item) {
        String query = "INSERT INTO items (name, description, startingPrice, auctionStatus, imagePath, endTime, ownerUsername) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setDouble(3, item.getStartingPrice());
            preparedStatement.setString(4, item.getAuctionStatus());
            preparedStatement.setString(5, item.getImagePath());
            preparedStatement.setTimestamp(6, item.getEndTime());
            preparedStatement.setString(7, item.getOwnerUsername());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateItem(Item item) {
        String query = "UPDATE items SET name = ?, description = ?, startingPrice = ?, auctionStatus = ?, imagePath = ?, endTime = ?, ownerUsername = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setDouble(3, item.getStartingPrice());
            preparedStatement.setString(4, item.getAuctionStatus());
            preparedStatement.setString(5, item.getImagePath());
            preparedStatement.setTimestamp(6, item.getEndTime());
            preparedStatement.setString(7, item.getOwnerUsername());
            preparedStatement.setInt(8, item.getId());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Item getItemById(int itemId) {
        String query = "SELECT * FROM items WHERE id = ?";
        Item item = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double startingPrice = resultSet.getDouble("startingPrice");
                String auctionStatus = resultSet.getString("auctionStatus");
                String imagePath = resultSet.getString("imagePath");
                Timestamp endTime = resultSet.getTimestamp("endTime");
                String ownerUsername = resultSet.getString("ownerUsername");

                item = new Item(name, description, startingPrice, auctionStatus, imagePath, endTime, ownerUsername);
                item.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public List<Item> getAllItems() {
        String query = "SELECT * FROM items";
        List<Item> items = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double startingPrice = resultSet.getDouble("startingPrice");
                String auctionStatus = resultSet.getString("auctionStatus");
                String imagePath = resultSet.getString("imagePath");
                Timestamp endTime = resultSet.getTimestamp("endTime");
                String ownerUsername = resultSet.getString("ownerUsername");

                Item item = new Item(name, description, startingPrice, auctionStatus, imagePath, endTime, ownerUsername);
                item.setId(id);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<Item> searchItems(String query, String status) {
        String sqlQuery = "SELECT * FROM items WHERE (name LIKE ? OR description LIKE ?)";
        if (!"All".equals(status)) {
            sqlQuery += " AND auctionStatus = ?";
        }

        List<Item> items = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            if (!"All".equals(status)) {
                preparedStatement.setString(3, status);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double startingPrice = resultSet.getDouble("startingPrice");
                String auctionStatus = resultSet.getString("auctionStatus");
                String imagePath = resultSet.getString("imagePath");
                Timestamp endTime = resultSet.getTimestamp("endTime");
                String ownerUsername = resultSet.getString("ownerUsername");

                Item item = new Item(name, description, startingPrice, auctionStatus, imagePath, endTime, ownerUsername);
                item.setId(id);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
