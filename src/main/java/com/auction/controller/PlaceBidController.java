package main.java.com.auction.controller;

import main.java.com.auction.model.Bid;
import main.java.com.auction.model.BidDAO;

public class PlaceBidController {
    private BidDAO bidDAO;

    public PlaceBidController() {
        this.bidDAO = new BidDAO();
    }

    public boolean placeBid(Bid bid) {
        return bidDAO.placeBid(bid);
    }

    public double getHighestBid(int itemId) {
        return bidDAO.getHighestBid(itemId);
    }
}
