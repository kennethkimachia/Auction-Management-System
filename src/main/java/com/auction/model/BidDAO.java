
package main.java.com.auction.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {
    private NotificationDAO notificationDAO = new NotificationDAO();

    public boolean placeBid(Bid bid) {
        String query = "INSERT INTO bids (itemId, bidderName, bidAmount) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bid.getItemId());
            preparedStatement.setString(2, bid.getBidderName());
            preparedStatement.setDouble(3, bid.getBidAmount());
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                notifyItemOwner(bid.getItemId(), bid.getBidderName(), bid.getBidAmount());
                notifyOutbidUsers(bid.getItemId(), bid.getBidAmount());
                return true;
            }
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

    private void notifyItemOwner(int itemId, String bidderName, double bidAmount) {
        ItemDAO itemDAO = new ItemDAO();
        Item item = itemDAO.getItemById(itemId);
        UserDAO userDAO = new UserDAO();
        User itemOwner = userDAO.getUserByUsername(item.getOwnerUsername());

        if (itemOwner != null) {
            String message = "Your item \"" + item.getName() + "\" received a new bid of $" + bidAmount + " from " + bidderName;
            Notification notification = new Notification(itemOwner.getId(), message);
            notificationDAO.addNotification(notification);
        }
    }

    private void notifyOutbidUsers(int itemId, double newBidAmount) {
        String query = "SELECT bidderName, bidAmount FROM bids WHERE itemId = ? AND bidAmount < ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            preparedStatement.setDouble(2, newBidAmount);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bidderName = resultSet.getString("bidderName");
                // Notify the user that they have been outbid
                UserDAO userDAO = new UserDAO();
                User outbidUser = userDAO.getUserByUsername(bidderName);
                if (outbidUser != null) {
                    String message = "You have been outbid on item \"" + itemId + "\" with a bid of $" + newBidAmount;
                    Notification notification = new Notification(outbidUser.getId(), message);
                    notificationDAO.addNotification(notification);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
