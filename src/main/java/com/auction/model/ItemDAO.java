package main.java.com.auction.model;
import java.sql.*;

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

}
