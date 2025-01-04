package pack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends Application {
    private TextField emailField;
    private PasswordField passwordField;

    @Override
    public void start(Stage stage) {
        
        Label titleLabel = new Label("Login Page");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(stage));

        VBox layout = new VBox(15, titleLabel, emailLabel, emailField, passwordLabel, passwordField, loginButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    
    private void handleLogin(Stage stage) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Failed", "Email or password cannot be empty.");
            return;
        }

        String query = "SELECT UserID, UserRole FROM msuser WHERE UserEmail = ? AND UserPassword = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("UserID");
                String role = rs.getString("UserRole");

                if ("Shopper".equalsIgnoreCase(role)) {
                    
                    ShopperPage shopperPage = new ShopperPage();
                    shopperPage.setLoggedInEmail(email, userID);
                    shopperPage.start(stage);
                } else if ("Manager".equalsIgnoreCase(role)) {
                    
                    ManagerHomePage managerPage = new ManagerHomePage();
                    managerPage.setLoggedInUserID(userID);
                    managerPage.start(stage);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Unknown role: " + role);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong. Please try again.");
        }
    }

    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
