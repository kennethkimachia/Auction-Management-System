package main.java.com.auction.model;

import java.sql.*;

public class Bid {
    private int id;
    private int itemId;
    private String bidderName;
    private double bidAmount;
    private Timestamp bidTime;

    public Bid(int itemId, String bidderName, double bidAmount) {
        this.itemId = itemId;
        this.bidderName = bidderName;
        this.bidAmount = bidAmount;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Timestamp getBidTime() {
        return bidTime;
    }

    public void setBidTime(Timestamp bidTime) {
        this.bidTime = bidTime;
    }

}
