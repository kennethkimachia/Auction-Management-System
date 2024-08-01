package main.java.com.auction.controller;

import main.java.com.auction.model.Notification;
import main.java.com.auction.model.NotificationDAO;

import java.util.List;

public class NotificationController {
    private NotificationDAO notificationDAO;

    public NotificationController() {
        this.notificationDAO = new NotificationDAO();
    }

    public boolean addNotification(Notification notification) {
        return notificationDAO.addNotification(notification);
    }

    public List<Notification> getNotificationsByUserId(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    public boolean markNotificationAsRead(int notificationId) {
        return notificationDAO.markNotificationAsRead(notificationId);
    }
}
