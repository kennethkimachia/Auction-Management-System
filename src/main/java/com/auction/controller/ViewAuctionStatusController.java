package main.java.com.auction.controller;

import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;

import java.util.List;

public class ViewAuctionStatusController {
    private ItemDAO itemDAO;

    public ViewAuctionStatusController() {
        this.itemDAO = new ItemDAO();
    }

    public List<Item> getOngoingAuctions() {
        return itemDAO.getOngoingAuctions();
    }

    public List<Item> getClosedAuctions() {
        return itemDAO.getClosedAuctions();
    }
}
