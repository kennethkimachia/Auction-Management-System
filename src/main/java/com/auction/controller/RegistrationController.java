package main.java.com.auction.controller;

import main.java.com.auction.model.User;
import main.java.com.auction.model.UserDAO;

public class RegistrationController {
    private UserDAO userDAO;

    public RegistrationController() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }
}
