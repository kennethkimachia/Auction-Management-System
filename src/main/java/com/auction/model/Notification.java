package main.java.com.auction.model;

public class Notification {
    private int id;
    private int userId;
    private String message;
    private boolean isRead;

    public Notification(int userId, String message) {
        this.userId = userId;
        this.message = message;
        this.isRead = false;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
