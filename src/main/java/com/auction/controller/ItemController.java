package main.java.com.auction.controller;

import main.java.com.auction.model.Item;
import main.java.com.auction.model.ItemDAO;

import java.util.List;

public class ItemController {
    private ItemDAO itemDAO;

    public ItemController() {
        this.itemDAO = new ItemDAO();
    }

    public boolean addItem(Item item) {
        return itemDAO.registerItem(item);
    }

    public boolean updateItem(Item item) {
        return itemDAO.updateItem(item);
    }

    public Item getItemById(int itemId) {
        return itemDAO.getItemById(itemId);
    }

    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public List<Item> searchItems(String query, String status) {
        return itemDAO.searchItems(query, status);
    }

    public List<Item> getOngoingAuctions() {
        return itemDAO.getOngoingAuctions();
    }

    public List<Item> getClosedAuctions() {
        return itemDAO.getClosedAuctions();
    }
}
