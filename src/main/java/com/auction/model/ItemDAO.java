package main.java.com.auction.model;
import java.sql.*;
import java.util.*;

public class ItemDAO {
    public boolean registerItem(Item item) {
        String query = "INSERT INTO items (name, description, startingPrice, auctionStatus, imagePath) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setDouble(3, item.getStartingPrice());
            preparedStatement.setString(4, item.getAuctionStatus());
            preparedStatement.setString(5, item.getImagePath());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

                Item item = new Item(name, description, startingPrice, auctionStatus, imagePath);
                item.setId(id);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
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

                item = new Item(name, description, startingPrice, auctionStatus, imagePath);
                item.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

}
