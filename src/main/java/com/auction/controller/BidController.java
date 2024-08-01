// src/main/java/com/auction/controller/BidController.java
package main.java.com.auction.controller;

import main.java.com.auction.model.Bid;
import main.java.com.auction.model.BidDAO;

import java.util.List;

public class BidController {
    private BidDAO bidDAO;

    public BidController() {
        this.bidDAO = new BidDAO();
    }

    public boolean placeBid(Bid bid) {
        return bidDAO.placeBid(bid);
    }

    public double getHighestBid(int itemId) {
        return bidDAO.getHighestBid(itemId);
    }

    public List<Bid> getBidsByItemId(int itemId) {
        return bidDAO.getBidsByItemId(itemId);
    }
}
