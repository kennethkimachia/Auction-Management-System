
package main.java.com.auction.controller;

import main.java.com.auction.model.User;
import main.java.com.auction.model.UserDAO;

public class LoginController {
    private UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    public User loginUser(String username, String password) {
        return userDAO.loginUser(username, password);
    }
}
