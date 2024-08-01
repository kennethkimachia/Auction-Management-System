package main.java.com.auction.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {
    public boolean placeBid(Bid bid) {
        String query = "INSERT INTO bids (itemId, bidderName, bidAmount) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bid.getItemId());
            preparedStatement.setString(2, bid.getBidderName());
            preparedStatement.setDouble(3, bid.getBidAmount());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getHighestBid(int itemId) {
        String query = "SELECT MAX(bidAmount) AS highestBid FROM bids WHERE itemId = ?";
        double highestBid = 0.0;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                highestBid = resultSet.getDouble("highestBid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return highestBid;
    }

    public List<Bid> getBidsByItemId(int itemId) {
        String query = "SELECT * FROM bids WHERE itemId = ?";
        List<Bid> bids = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bidderName = resultSet.getString("bidderName");
                double bidAmount = resultSet.getDouble("bidAmount");
                Timestamp bidTime = resultSet.getTimestamp("bidTime");

                Bid bid = new Bid(itemId, bidderName, bidAmount);
                bid.setId(id);
                bid.setBidTime(bidTime);
                bids.add(bid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bids;
    }
}