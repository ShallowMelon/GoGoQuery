package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerHomePage extends Application {
    private int loggedInUserID;

    public void setLoggedInUserID(int userID) {
        this.loggedInUserID = userID;
    }

    @Override
    public void start(Stage stage) {
        
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");

        MenuItem addItemMenu = new MenuItem("Add Item");
        MenuItem manageQueueMenu = new MenuItem("Manage Queue");
        MenuItem logoutMenu = new MenuItem("Log Out");

        menu.getItems().addAll(addItemMenu, manageQueueMenu, logoutMenu);
        menuBar.getMenus().add(menu);

        
        addItemMenu.setOnAction(e -> {
            AddItemManager addItemManager = new AddItemManager();
            try {
                addItemManager.start(new Stage()); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        manageQueueMenu.setOnAction(e -> {
            QueueManager queueManager = new QueueManager();
            queueManager.openQueuePopup(stage);
        });


        logoutMenu.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(stage);
        });

        
        Label welcomeLabel = new Label("Welcome, Manager!");
        welcomeLabel.setFont(new Font("Arial", 24));
        welcomeLabel.setPadding(new Insets(20));

        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(welcomeLabel);

        Scene scene = new Scene(layout, 600, 400);
        stage.setTitle("Manager Home Page");
        stage.setScene(scene);
        stage.show();
    }

    private void openAddItemPopup(Stage parentStage) {
        
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Add Item");

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(20));

        
        Label titleLabel = new Label("Add New Item");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField itemNameField = new TextField();
        itemNameField.setPromptText("Item Name");

        TextField itemDescField = new TextField();
        itemDescField.setPromptText("Item Description");

        TextField itemPriceField = new TextField();
        itemPriceField.setPromptText("Item Price");

        TextField itemStockField = new TextField();
        itemStockField.setPromptText("Item Stock");

        Button addItemButton = new Button("Add Item");
        addItemButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        addItemButton.setOnAction(e -> {
            String name = itemNameField.getText();
            String desc = itemDescField.getText();
            String price = itemPriceField.getText();
            String stock = itemStockField.getText();

            if (name.isEmpty() || desc.isEmpty() || price.isEmpty() || stock.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            } else {
                addItemToDatabase(name, desc, price, stock);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item added successfully!");
                popupStage.close();
            }
        });

        popupLayout.getChildren().addAll(titleLabel, itemNameField, itemDescField, itemPriceField, itemStockField, addItemButton);

        Scene popupScene = new Scene(popupLayout, 350, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void addItemToDatabase(String name, String desc, String price, String stock) {
        String query = "INSERT INTO msitem (ItemName, ItemDesc, ItemPrice, ItemStock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, desc);
            stmt.setDouble(3, Double.parseDouble(price));
            stmt.setInt(4, Integer.parseInt(stock));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
