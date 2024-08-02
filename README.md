# Auction Management System

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Database Setup](#database-setup)
- [Usage](#usage)
- [Controllers](#controllers)
- [Views](#views)
- [Models](#models)
- [Utilities](#utilities)
- [Security](#security)
- [Future Enhancements](#future-enhancements)


## Introduction
The Auction Management System is a desktop application designed to facilitate the management of auctions. It provides features for user registration, item listing, bidding, and auction status tracking. The application follows the Model-View-Controller (MVC) architecture to ensure modularity and maintainability.

## Features
- User Registration and Login
- Password Hashing with bcrypt
- Role-based Access Control (Admin and Bidder)
- Item Registration with Images
- Bidding on Items
- Auction Status Tracking (Ongoing and Closed)
- Notifications for Bids and Auction Results

## Technologies Used
- Java
- Swing/AWT for GUI Development
- JDBC for Database Connectivity
- MySQL Database
- BCrypt for Password Hashing

## Project Structure
The project is structured to follow the MVC architecture. Below is an overview of the folder structure:
Auction-Management-System/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── com/
│ │ │ │ ├── auction/
│ │ │ │ │ ├── controller/
│ │ │ │ │ │ ├── LoginController.java
│ │ │ │ │ │ ├── PlaceBidController.java
│ │ │ │ │ │ ├── RegistrationController.java
│ │ │ │ │ │ ├── UserController.java
│ │ │ │ │ │ ├── ViewAuctionStatusController.java
│ │ │ │ │ ├── model/
│ │ │ │ │ │ ├── Bid.java
│ │ │ │ │ │ ├── BidDAO.java
│ │ │ │ │ │ ├── DatabaseConnection.java
│ │ │ │ │ │ ├── Item.java
│ │ │ │ │ │ ├── ItemDAO.java
│ │ │ │ │ │ ├── Notification.java
│ │ │ │ │ │ ├── NotificationDAO.java
│ │ │ │ │ │ ├── User.java
│ │ │ │ │ │ ├── UserDAO.java
│ │ │ │ │ ├── util/
│ │ │ │ │ │ ├── AuctionEndManager.java
│ │ │ │ │ ├── view/
│ │ │ │ │ │ ├── AddEditUserView.java
│ │ │ │ │ │ ├── DashboardView.java
│ │ │ │ │ │ ├── ItemDetailView.java
│ │ │ │ │ │ ├── ListItemsView.java
│ │ │ │ │ │ ├── LoginView.java
│ │ │ │ │ │ ├── ManageUsersView.java
│ │ │ │ │ │ ├── PlaceBidView.java
│ │ │ │ │ │ ├── RegisterItemView.java
│ │ │ │ │ │ ├── RegistrationView.java
│ │ │ │ │ │ ├── ViewAuctionStatusView.java


## Setup and Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server
- Integrated Development Environment (IDE) such as IntelliJ IDEA

### Installation Steps
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/kennethkimachia/Auction-Management-System.git
   cd Auction-Management-System

2. **Open in IntelliJ IDEA:**
Open IntelliJ IDEA and select "Open" from the welcome screen.
Navigate to the cloned repository and select it.

3. **Add Libraries:**
Add the bcrypt and JavaMail API JAR files to your project's classpath.

4. **Configure Database Connection:**
Update the DatabaseConnection.java file with your MySQL database credentials.

## database-setup
1. **Create Database:**
CREATE DATABASE auction_db;

2. **Create Tables:**
USE auction_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    startingPrice DOUBLE NOT NULL,
    auctionStatus VARCHAR(20) NOT NULL,
    imagePath VARCHAR(255),
    endTime TIMESTAMP,
    ownerUsername VARCHAR(50),
    FOREIGN KEY (ownerUsername) REFERENCES users(username)
);

CREATE TABLE bids (
    id INT AUTO_INCREMENT PRIMARY KEY,
    itemId INT,
    bidderName VARCHAR(50),
    bidAmount DOUBLE,
    bidTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (itemId) REFERENCES items(id),
    FOREIGN KEY (bidderName) REFERENCES users(username)
);

CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    message TEXT NOT NULL,
    isRead BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (userId) REFERENCES users(id)
);

## usage
**Running the Application**
In IntelliJ, right-click the Main class and select Run 'Main.main()'.

**User Registration and Login**
**Registration:**

-Open the application.
-Click "Register" to create a new user.

**Login:**

-Enter your username and password.
-Click "Login" to access the dashboard.

**Dashboard**

The dashboard displays options based on the user role (Admin/Bidder).

**Manage Items**

Admins can add, edit, and delete items.

**Place Bids**

Bidders can place bids on items.

**View Auction Status**

View ongoing and closed auctions.

## Controllers

-LoginController: Handles user login.
-PlaceBidController: Manages placing bids.
-RegistrationController: Handles user registration.
-UserController: Manages user data and interactions.
-ViewAuctionStatusController: Retrieves auction status data.

## Views

-AddEditUserView: Interface for adding/editing users.
-DashboardView: Main dashboard interface.
-ItemDetailView: Displays item details.
-ListItemsView: Lists all items.
-LoginView: User login interface.
-ManageUsersView: Admin interface for managing users.
-PlaceBidView: Interface for placing bids.
-RegisterItemView: Interface for registering items.
-RegistrationView: User registration interface.
-ViewAuctionStatusView: Displays auction statuses.

## models

-Bid: Represents a bid.
-BidDAO: Handles bid database operations.
-DatabaseConnection: Manages database connection.
-Item: Represents an auction item.
-ItemDAO: Handles item database operations.
-Notification: Represents a notification.
-NotificationDAO: Handles notification database operations.
-User: Represents a user.
-UserDAO: Handles user database operations.

## utilities

-AuctionEndManager: Manages auction end scheduling and notifications.
-EmailUtil: Utility for sending email notifications.

##Security

-Password Hashing: All user passwords are hashed using bcrypt for security.
-Role-based Access Control: Access to features is controlled based on user roles (Admin/Bidder).

## future-enhancements 

-Integrate payment gateways for transactions.
-Implement real-time bid updates using WebSockets.
-Add detailed reporting and analytics for admins.
-Improve UI/UX with modern design frameworks.
