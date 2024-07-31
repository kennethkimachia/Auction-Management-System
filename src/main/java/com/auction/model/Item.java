package main.java.com.auction.model;

public class Item {
    private int id;
    private String name;
    private String description;
    private double startingPrice;
    private String auctionStatus;
    private String imagePath;

    public Item(String name, String description, double startingPrice, String auctionStatus, String imagePath) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.auctionStatus = auctionStatus;
        this.imagePath = imagePath;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(String auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
