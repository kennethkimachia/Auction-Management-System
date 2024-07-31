package main.java.com.auction.model;
import java.sql.*;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/auction_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1327";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
