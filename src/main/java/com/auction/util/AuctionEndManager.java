// src/main/java/com/auction/util/AuctionEndManager.java
package main.java.com.auction.util;

import main.java.com.auction.model.*;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionEndManager {
    private Timer timer;
    private ItemDAO itemDAO;
    private BidDAO bidDAO;
    private NotificationDAO notificationDAO;

    public AuctionEndManager() {
        timer = new Timer(true);
        itemDAO = new ItemDAO();
        bidDAO = new BidDAO();
        notificationDAO = new NotificationDAO();
    }

    public void scheduleAuctionEnd(Item item) {
        long delay = item.getEndTime().getTime() - System.currentTimeMillis();
        timer.schedule(new AuctionEndTask(item), delay);
    }

    private class AuctionEndTask extends TimerTask {
        private Item item;

        public AuctionEndTask(Item item) {
            this.item = item;
        }

        @Override
        public void run() {
            item.setAuctionStatus("Closed");
            itemDAO.updateItem(item);

            List<Bid> bids = bidDAO.getBidsByItemId(item.getId());
            if (!bids.isEmpty()) {
                Bid highestBid = bids.get(bids.size() - 1);
                notifyWinner(highestBid);
            }
        }

        private void notifyWinner(Bid highestBid) {
            // Notify the winner of the auction
            System.out.println("Notification: User " + highestBid.getBidderName() + " has won the auction for item " + highestBid.getItemId() + " with a bid of $" + highestBid.getBidAmount());
            JOptionPane.showMessageDialog(null, "User " + highestBid.getBidderName() + " has won the auction for item " + highestBid.getItemId() + " with a bid of $" + highestBid.getBidAmount(), "Auction Ended", JOptionPane.INFORMATION_MESSAGE);

            // Add notification to the database
            UserDAO userDAO = new UserDAO();
            User winner = userDAO.getUserByUsername(highestBid.getBidderName());
            if (winner != null) {
                String message = "You have won the auction for item \"" + item.getName() + "\" with a bid of $" + highestBid.getBidAmount();
                Notification notification = new Notification(winner.getId(), message);
                notificationDAO.addNotification(notification);
            }
        }
    }
}
