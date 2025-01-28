import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.imageio.ImageIO;
import java.io.*;

public class MetroApp extends JFrame {
    private Connection connection;
    private Statement statement;

    public MetroApp() {
        // Database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/metrotrial", "root", "Karthik@1234");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //UI
        setTitle("Metro System App");
        setSize(600, 491);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        try {

            ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File("C:\\Users\\shivk\\OneDrive\\Desktop\\metro_final_2.jpg")));


            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(600, 491, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);


            JLabel imageLabel = new JLabel(scaledImageIcon);
            add(imageLabel, BorderLayout.NORTH);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Menus
        JMenuBar menuBar = new JMenuBar();
        JMenu userMenu = new JMenu("Users");
        JMenu feedbackMenu = new JMenu("Feedback");
        JMenu ticketMenu = new JMenu("Tickets");
        JMenu stationMenu = new JMenu("Stations");
        JMenu routeMenu = new JMenu("Metro Routes");
        JMenu paymentMenu = new JMenu("Payments");


        // Add Menu to Menu bar
        menuBar.add(userMenu);
        menuBar.add(feedbackMenu);
        menuBar.add(ticketMenu);
        menuBar.add(stationMenu);
        menuBar.add(routeMenu);
        setJMenuBar(menuBar);

        // User menu
        JMenuItem addUserItem = new JMenuItem("Add User");
        JMenuItem deleteUserItem = new JMenuItem("Delete User");
        userMenu.add(addUserItem);
        userMenu.add(deleteUserItem);
        JMenuItem updateUserItem = new JMenuItem("Update User");
        userMenu.add(updateUserItem);
        JMenuItem displayUsersItem = new JMenuItem("Display Users");
        userMenu.add(displayUsersItem);

        // Feedback menu
        JMenuItem displayFeedbackItem = new JMenuItem("Display Feedback");
        feedbackMenu.add(displayFeedbackItem);

        // Feedback menu
        JMenuItem addFeedbackItem = new JMenuItem("Add Feedback");
        JMenuItem deleteFeedbackItem = new JMenuItem("Delete Feedback");
        feedbackMenu.add(addFeedbackItem);
        feedbackMenu.add(deleteFeedbackItem);

        // Ticket menu
        JMenuItem displayTicketsItem = new JMenuItem("Display Tickets");
        ticketMenu.add(displayTicketsItem);
        JMenuItem addTicketItem = new JMenuItem("Add Ticket");
        JMenuItem deleteTicketItem = new JMenuItem("Delete Ticket");
        ticketMenu.add(addTicketItem);
        ticketMenu.add(deleteTicketItem);

        // Station menu
        JMenuItem displayStationInfoItem = new JMenuItem("Display Station Information");
        stationMenu.add(displayStationInfoItem);

        JMenuItem addStationItem = new JMenuItem("Add Station");
        stationMenu.add(addStationItem);

        JMenuItem deleteStationItem = new JMenuItem("Delete Station");
        stationMenu.add(deleteStationItem);

        // Route menu
        JMenuItem displayMetroRoutesItem = new JMenuItem("Display Metro Routes");
        routeMenu.add(displayMetroRoutesItem);
        JMenuItem addMetroRouteItem = new JMenuItem("Add Metro Route");
        routeMenu.add(addMetroRouteItem);
        JMenuItem deleteMetroRouteItem = new JMenuItem("Delete Metro Route");
        routeMenu.add(deleteMetroRouteItem);

        //Payment menu
        menuBar.add(paymentMenu);
        JMenuItem displayPaymentsItem = new JMenuItem("Display Payments");
        paymentMenu.add(displayPaymentsItem);

        JMenuItem addPaymentItem = new JMenuItem("Add Payment");
        paymentMenu.add(addPaymentItem);

        JMenuItem deletePaymentItem = new JMenuItem("Delete Payment");
        paymentMenu.add(deletePaymentItem);

        // Action listeners
        addUserItem.addActionListener(e -> addUser());
        deleteUserItem.addActionListener(e -> deleteUser());
        addFeedbackItem.addActionListener(e -> addFeedback());
        deleteFeedbackItem.addActionListener(e -> deleteFeedback());
        addTicketItem.addActionListener(e -> addTicket());
        deleteTicketItem.addActionListener(e -> deleteTicket());
        displayStationInfoItem.addActionListener(e -> displayStationInfo());
        displayMetroRoutesItem.addActionListener(e -> displayMetroRoutes());
        displayUsersItem.addActionListener(e -> displayUsers());
        displayFeedbackItem.addActionListener(e -> displayFeedback());
        displayTicketsItem.addActionListener(e -> displayTickets());
        displayPaymentsItem.addActionListener(e -> displayPayments());
        displayPaymentsItem.addActionListener(e -> displayPayments());
        updateUserItem.addActionListener(e -> updateUser());
        addStationItem.addActionListener(e -> addStation());
        deleteStationItem.addActionListener(e -> deleteStation());
        addPaymentItem.addActionListener(e -> addPayment());
        deletePaymentItem.addActionListener(e -> deletePayment());
        addMetroRouteItem.addActionListener(e -> addMetroRoute());
        deleteMetroRouteItem.addActionListener(e -> deleteMetroRoute());


        setVisible(true);
    }

    private void addUser() {
        try {
            //Input
            String userIdInput = JOptionPane.showInputDialog("Enter User ID:");
            int userId = Integer.parseInt(userIdInput);

            String firstName = JOptionPane.showInputDialog("Enter First Name:");
            String middleName = JOptionPane.showInputDialog("Enter Middle Name:");
            String lastName = JOptionPane.showInputDialog("Enter Last Name:");
            String password = JOptionPane.showInputDialog("Enter Password:");

            // SQL query to add user
            String query = "INSERT INTO Users (User_ID, Password, First_Name, Middle_Name, Last_Name) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, middleName);
            preparedStatement.setString(5, lastName);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add user. Please try again.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid User ID. Please enter a valid integer value.");
        }
    }


    private void deleteUser() {
        try {
            //Input
            int userID = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID to delete:"));

            // SQL Query to delete user
            String query = "DELETE FROM Users WHERE User_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No user found with the given ID.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete user. Please try again.");
        }
    }

    private void updateUser() {
        try {
            //Input
            int userId = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID to update:"));

            // Check for exception
            String checkQuery = "SELECT * FROM Users WHERE User_ID = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, userId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "No user found with the given ID.");
                return;
            }

            // Prompt user for updated information
            String firstName = JOptionPane.showInputDialog("Enter First Name:");
            String middleName = JOptionPane.showInputDialog("Enter Middle Name:");
            String lastName = JOptionPane.showInputDialog("Enter Last Name:");
            String password = JOptionPane.showInputDialog("Enter Password:");

            //SQL Query to Update user
            String query = "UPDATE Users SET Password = ?, First_Name = ?, Middle_Name = ?, Last_Name = ? WHERE User_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, middleName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "User updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to update user. Please try again.");
        }
    }


    private void addFeedback() {
        try {
            // Prompt user for input
            int feedbackID = Integer.parseInt(JOptionPane.showInputDialog("Enter Feedback ID:"));
            String comments = JOptionPane.showInputDialog("Enter Feedback Comments:");
            int userID = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));

            // Construct and execute SQL query to add feedback
            String query = "INSERT INTO Feedback (Feedback_ID, Feedback_comments, User_ID) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, feedbackID);
            preparedStatement.setString(2, comments);
            preparedStatement.setInt(3, userID);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Feedback added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add feedback.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add feedback. Please try again.");
        }
    }


    private void deleteFeedback() {
        try {
            // Prompt user for input
            int feedbackID = Integer.parseInt(JOptionPane.showInputDialog("Enter Feedback ID to delete:"));

            // Construct and execute SQL query to delete feedback
            String query = "DELETE FROM Feedback WHERE Feedback_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, feedbackID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Feedback deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No feedback found with the given ID.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete feedback. Please try again.");
        }
    }




    private void addTicket() {
        try {
            // Prompt user for input
            int ticketID = Integer.parseInt(JOptionPane.showInputDialog("Enter Ticket ID:"));
            int trainID = Integer.parseInt(JOptionPane.showInputDialog("Enter Train ID:"));
            int userID = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
            String status = JOptionPane.showInputDialog("Enter Status:");
            int bookingID = Integer.parseInt(JOptionPane.showInputDialog("Enter Booking ID:"));
            int scheduleID = Integer.parseInt(JOptionPane.showInputDialog("Enter Schedule ID:"));

            // Construct and execute SQL query to add ticket
            String query = "INSERT INTO Tickets (Ticket_ID, Train_ID, User_ID, Status, Booking_ID, Schedule_ID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ticketID);
            preparedStatement.setInt(2, trainID);
            preparedStatement.setInt(3, userID);
            preparedStatement.setString(4, status);
            preparedStatement.setInt(5, bookingID);
            preparedStatement.setInt(6, scheduleID);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Ticket added successfully!");
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add ticket. Please try again.");
        }
    }



    private void deleteTicket() {
        try {
            // Prompt user for input
            int ticketID = Integer.parseInt(JOptionPane.showInputDialog("Enter Ticket ID to delete:"));

            // Construct and execute SQL query to delete ticket
            String query = "DELETE FROM Tickets WHERE Ticket_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ticketID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Ticket deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No ticket found with the given ID.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete ticket. Please try again.");
        }
    }


    private void displayStationInfo() {
        try {
            // Construct and execute SQL query to retrieve station information
            String query = "SELECT * FROM Stations";
            ResultSet resultSet = statement.executeQuery(query);

            // Display station information in a dialog
            StringBuilder stationInfo = new StringBuilder("Station Information:\n");
            while (resultSet.next()) {
                int stationID = resultSet.getInt("Station_ID");
                String location = resultSet.getString("Location");
                String managerName = resultSet.getString("Manager_name");
                int capacity = resultSet.getInt("Capacity");
                String stationName = resultSet.getString("Station_Name");
                String stationTime = resultSet.getString("Station_time");

                stationInfo.append("Station ID: ").append(stationID).append(", ")
                        .append("Location: ").append(location).append(", ")
                        .append("Manager Name: ").append(managerName).append(", ")
                        .append("Capacity: ").append(capacity).append(", ")
                        .append("Station Name: ").append(stationName).append(", ")
                        .append("Station Time: ").append(stationTime).append("\n");
            }
            JOptionPane.showMessageDialog(null, stationInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve station information. Please try again.");
        }
    }

    private void addStation() {
        try {
            // Prompt user for input
            int stationID = Integer.parseInt(JOptionPane.showInputDialog("Enter Station ID:"));
            String location = JOptionPane.showInputDialog("Enter Location:");
            String managerName = JOptionPane.showInputDialog("Enter Manager Name:");
            int capacity = Integer.parseInt(JOptionPane.showInputDialog("Enter Capacity:"));
            String stationName = JOptionPane.showInputDialog("Enter Station Name:");
            String stationTime = JOptionPane.showInputDialog("Enter Station Time:");

            // Construct and execute SQL query to add station
            String query = "INSERT INTO Stations (Station_ID, Location, Manager_name, Capacity, Station_Name, Station_time) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, stationID);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, managerName);
            preparedStatement.setInt(4, capacity);
            preparedStatement.setString(5, stationName);
            preparedStatement.setString(6, stationTime);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Station added successfully!");
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add station. Please try again.");
        }
    }

    private void deleteStation() {
        try {
            // Prompt user for input
            int stationID = Integer.parseInt(JOptionPane.showInputDialog("Enter Station ID to delete:"));

            // Construct and execute SQL query to delete station
            String query = "DELETE FROM Stations WHERE Station_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, stationID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Station deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No station found with the given ID.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete station. Please try again.");
        }
    }


    private void displayUsers() {
        try {
            // Construct and execute SQL query to retrieve users
            String query = "SELECT * FROM Users";
            ResultSet resultSet =statement.executeQuery(query);

            // Display users in a dialog
            StringBuilder usersInfo = new StringBuilder("Users Information:\n");
            while (resultSet.next()) {
                int userID = resultSet.getInt("User_ID");
                String firstName = resultSet.getString("First_Name");
                String middleName = resultSet.getString("Middle_Name");
                String lastName = resultSet.getString("Last_Name");

                usersInfo.append("User ID: ").append(userID).append(", ")
                        .append("First Name: ").append(firstName).append(", ")
                        .append("Middle Name: ").append(middleName).append(", ")
                        .append("Last Name: ").append(lastName).append("\n");
            }
            JOptionPane.showMessageDialog(null, usersInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve users information. Please try again.");
        }
    }

    private void displayFeedback() {
        try {
            // Construct and execute SQL query to retrieve feedback
            String query = "SELECT * FROM Feedback";
            ResultSet resultSet = statement.executeQuery(query);

            // Display feedback in a dialog
            StringBuilder feedbackInfo = new StringBuilder("Feedback Information:\n");
            while (resultSet.next()) {
                int feedbackID = resultSet.getInt("Feedback_ID");
                String comments = resultSet.getString("Feedback_comments");
                int userID = resultSet.getInt("User_ID");

                feedbackInfo.append("Feedback ID: ").append(feedbackID).append(", ")
                        .append("Comments: ").append(comments).append(", ")
                        .append("User ID: ").append(userID).append("\n");
            }
            JOptionPane.showMessageDialog(null, feedbackInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve feedback information. Please try again.");
        }
    }

    private void displayTickets() {
        try {
            // Construct and execute SQL query to retrieve tickets
            String query = "SELECT * FROM Tickets";
            ResultSet resultSet = statement.executeQuery(query);

            // Display tickets in a dialog
            StringBuilder ticketsInfo = new StringBuilder("Tickets Information:\n");
            while (resultSet.next()) {
                int ticketID = resultSet.getInt("Ticket_ID");
                int trainID = resultSet.getInt("Train_ID");
                int userID = resultSet.getInt("User_ID");
                String status = resultSet.getString("Status");
                int bookingID = resultSet.getInt("Booking_ID");
                int scheduleID = resultSet.getInt("Schedule_ID");

                ticketsInfo.append("Ticket ID: ").append(ticketID).append(", ")
                        .append("Train ID: ").append(trainID).append(", ")
                        .append("User ID: ").append(userID).append(", ")
                        .append("Status: ").append(status).append(", ")
                        .append("Booking ID: ").append(bookingID).append(", ")
                        .append("Schedule ID: ").append(scheduleID).append("\n");
            }
            JOptionPane.showMessageDialog(null, ticketsInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve tickets information. Please try again.");
        }
    }


    private void displayMetroRoutes() {
        try {
            // Construct and execute SQL query to retrieve metro routes
            String query = "SELECT * FROM Metro_Routes";
            ResultSet resultSet = statement.executeQuery(query);

            // Display metro routes in a dialog
            StringBuilder metroRoutes = new StringBuilder("Metro Routes:\n");
            while (resultSet.next()) {
                int routeID = resultSet.getInt("Route_ID");
                int startStationID = resultSet.getInt("Start_station_ID");
                int endStationID = resultSet.getInt("End_station_ID");
                int trainID = resultSet.getInt("Train_ID");

                metroRoutes.append("Route ID: ").append(routeID).append(", ")
                        .append("Start Station ID: ").append(startStationID).append(", ")
                        .append("End Station ID: ").append(endStationID).append(", ")
                        .append("Train ID: ").append(trainID).append("\n");
            }
            JOptionPane.showMessageDialog(null, metroRoutes.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve metro routes. Please try again.");
        }
    }

    private void addMetroRoute() {
        try {
            // Prompt user for input
            int routeID = Integer.parseInt(JOptionPane.showInputDialog("Enter Route ID:"));
            int startStationID = Integer.parseInt(JOptionPane.showInputDialog("Enter Start Station ID:"));
            int endStationID = Integer.parseInt(JOptionPane.showInputDialog("Enter End Station ID:"));
            int trainID = Integer.parseInt(JOptionPane.showInputDialog("Enter Train ID:"));

            // Construct and execute SQL query to add metro route
            String query = "INSERT INTO Metro_Routes (Route_ID, Start_station_ID, End_station_ID, Train_ID) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, routeID);
            preparedStatement.setInt(2, startStationID);
            preparedStatement.setInt(3, endStationID);
            preparedStatement.setInt(4, trainID);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Metro route added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add metro route.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add metro route. Please try again.");
        }
    }

    private void deleteMetroRoute() {
        try {
            // Prompt user for input
            int routeID = Integer.parseInt(JOptionPane.showInputDialog("Enter Route ID to delete:"));

            // Construct and execute SQL query to delete metro route
            String query = "DELETE FROM Metro_Routes WHERE Route_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, routeID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Metro route deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No metro route found with the given ID.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete metro route. Please try again.");
        }
    }

    // Method to display payments
    private void displayPayments() {
        try {
            // Construct and execute SQL query to retrieve payment information
            String query = "SELECT * FROM Payment";
            ResultSet resultSet = statement.executeQuery(query);

            // Display payment information in a dialog
            StringBuilder paymentInfo = new StringBuilder("Payment Information:\n");
            while (resultSet.next()) {
                int paymentID = resultSet.getInt("Payment_ID");
                double amount = resultSet.getDouble("Amount");
                int userID = resultSet.getInt("User_ID");
                int ticketID = resultSet.getInt("Ticket_ID");

                paymentInfo.append("Payment ID: ").append(paymentID).append(", ")
                        .append("Amount: ").append(amount).append(", ")
                        .append("User ID: ").append(userID).append(", ")
                        .append("Ticket ID: ").append(ticketID).append("\n");
            }
            JOptionPane.showMessageDialog(null, paymentInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve payment information. Please try again.");
        }
    }

    private void addPayment() {
        try {
            // Prompt user for input
            int paymentID = Integer.parseInt(JOptionPane.showInputDialog("Enter Payment ID:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Amount:"));
            int userID = Integer.parseInt(JOptionPane.showInputDialog("Enter User ID:"));
            int ticketID = Integer.parseInt(JOptionPane.showInputDialog("Enter Ticket ID:"));

            // Construct and execute SQL query to add payment
            String query = "INSERT INTO Payment (Payment_ID, Amount, User_ID, Ticket_ID) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, paymentID);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setInt(3, userID);
            preparedStatement.setInt(4, ticketID);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Payment added successfully!");
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add payment. Please try again.");
        }
    }

    private void deletePayment() {
        try {
            // Prompt user for input
            int paymentID = Integer.parseInt(JOptionPane.showInputDialog("Enter Payment ID to delete:"));

            // Construct and execute SQL query to delete payment
            String query = "DELETE FROM Payment WHERE Payment_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, paymentID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Payment deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No payment found with the given ID.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete payment. Please try again.");
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(MetroApp::new);
    }
}

