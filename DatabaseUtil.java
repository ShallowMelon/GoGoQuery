package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javafx.scene.control.Alert;

public class DatabaseUtil {
	 
    private static final String URL = "jdbc:mysql://localhost:3306/gogoquery";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public static boolean registerUser(String email, String password, String gender, String dob) {
        String query = "INSERT INTO msuser (UserEmail, UserPassword, UserGender, UserDOB, UserRole) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, gender);
            stmt.setString(4, dob);
            stmt.setString(5, "Shopper"); 

            System.out.println("Executing query: " + stmt);
            stmt.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Duplicate email detected: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean additemmanagerdb(String name, String price, String desc, int stock, String Cat) {
        String query = "INSERT INTO MsItem (ItemName, ItemPrice, ItemDesc, ItemStock, ItemCategory) VALUES (?, ?, ?,?,?)";
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
        	stmt.setString(1, name);
        	stmt.setDouble(2, Double.parseDouble(price));
            stmt.setString(3, desc);
            stmt.setInt(4, stock);
            stmt.setString(5, Cat);


            System.out.println("Executing query: " + stmt);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            return false;
        }
    }
    public static String validateInput(String name, int spinnerValue, String priceStr, String description) {
        if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
            return "All fields must be filled out.";
        }
        if (name.length() < 5 || name.length() > 70) {
            return "Item name must be between 5 and 70 characters.";
        }
        if (description.length() < 10 || description.length() > 255) {
            return "Item description must be between 10 and 255 characters.";
        }
        try {
            double price = Double.parseDouble(priceStr);
            if (price < 0.50 || price > 900000) {
                return "Quantity must be between $0.50 and $900,000.";
            }
        } catch (NumberFormatException e) {
            return "Quantity must be a valid number.";
        }
        if (spinnerValue < 1 || spinnerValue > 300) {
            return "Spinner value must be a positive integer and cannot be more than 300.";
        }
        return "";
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
    
    
    
