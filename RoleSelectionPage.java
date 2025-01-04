package pack;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoleSelectionPage extends Application {
    private String userEmail;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("Select Your Role");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Button shopperButton = new Button("Register as Shopper");
        shopperButton.setOnAction(e -> {
            if (updateUserRole(userEmail, "Shopper")) {
                showAlert(AlertType.INFORMATION, "Success", "You are now registered as a Shopper!");
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to update role. Please try again.");
            }
        });

        Button managerButton = new Button("Register as Manager");
        managerButton.setOnAction(e -> {
            if (updateUserRole(userEmail, "Manager")) {
                showAlert(AlertType.INFORMATION, "Success", "You are now registered as a Manager!");
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to update role. Please try again.");
            }
        });

        root.getChildren().addAll(titleLabel, shopperButton, managerButton);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Role Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean updateUserRole(String email, String role) {
        String query = "UPDATE msuser SET UserRole = ? WHERE UserEmail = ?";
        try (java.sql.Connection conn = DatabaseUtil.connect();
             java.sql.PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role);
            stmt.setString(2, email);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
