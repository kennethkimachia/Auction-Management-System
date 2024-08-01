
package main.java.com.auction.util;

import main.java.com.auction.controller.BidController;
import main.java.com.auction.controller.ItemController;
import main.java.com.auction.controller.NotificationController;
import main.java.com.auction.controller.UserController;
import main.java.com.auction.model.Bid;
import main.java.com.auction.model.Item;
import main.java.com.auction.model.Notification;
import main.java.com.auction.model.User;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionEndManager {
    private Timer timer;
    private ItemController itemController;
    private BidController bidController;
    private NotificationController notificationController;
    private UserController userController;

    public AuctionEndManager() {
        timer = new Timer(true);
        itemController = new ItemController();
        bidController = new BidController();
        notificationController = new NotificationController();
        userController = new UserController();
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
            itemController.updateItem(item);

            List<Bid> bids = bidController.getBidsByItemId(item.getId());
            if (!bids.isEmpty()) {
                Bid highestBid = bids.get(bids.size() - 1);
                notifyWinner(highestBid);
            }
        }

        private void notifyWinner(Bid highestBid) {
            User winner = userController.getUserByUsername(highestBid.getBidderName());
            if (winner != null) {
                String message = "You have won the auction for item \"" + item.getName() + "\" with a bid of $" + highestBid.getBidAmount();
                Notification notification = new Notification(winner.getId(), message);
                notificationController.addNotification(notification);

                // Optionally, send an email notification
                // EmailUtil.sendEmail(winner.getUsername(), "Auction Won", message);
            }
        }
    }
}
