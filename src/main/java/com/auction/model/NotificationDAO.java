
package main.java.com.auction.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NotificationDAO {
    public boolean addNotification(Notification notification) {
        String query = "INSERT INTO notifications (userId, message, isRead) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, notification.getUserId());
            preparedStatement.setString(2, notification.getMessage());
            preparedStatement.setBoolean(3, notification.isRead());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Notification> getNotificationsByUserId(int userId) {
        String query = "SELECT * FROM notifications WHERE userId = ? AND isRead = FALSE";
        List<Notification> notifications = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String message = resultSet.getString("message");
                boolean isRead = resultSet.getBoolean("isRead");

                Notification notification = new Notification(userId, message);
                notification.setId(id);
                notification.setRead(isRead);
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public boolean markNotificationAsRead(int notificationId) {
        String query = "UPDATE notifications SET isRead = TRUE WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, notificationId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
