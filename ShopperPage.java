package pack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopperPage extends Application {
    private ObservableList<String> itemsList = FXCollections.observableArrayList();
    private ListView<String> listView;
    private TextField searchBar;
    private ComboBox<String> categoryFilter;
    private String loggedInEmail;
    private int loggedInUserID;

    @Override
    public void start(Stage stage) {
        
        BackgroundImage backgroundImage = new BackgroundImage(
            new Image(getClass().getResource("/blu.jpg").toExternalForm()),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true)
        );
        ImageView logo = new ImageView(new Image(getClass().getResource("/logo.png").toExternalForm()));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);

        
        Label welcomeLabel = new Label("Welcome, Shopper!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Button cartButton = new Button("My Cart");
        cartButton.setOnAction(e -> openCart(stage));

        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(stage);
        });

        HBox headerBox = new HBox(20, logo, welcomeLabel, cartButton, logoutButton);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10));

        
        searchBar = new TextField();
        searchBar.setPromptText("Search for an item...");
        categoryFilter = new ComboBox<>();
        categoryFilter.setPromptText("Select Category");
        loadCategories();

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchItems(searchBar.getText(), categoryFilter.getValue()));

        HBox searchBox = new HBox(10, searchBar, categoryFilter, searchButton);
        searchBox.setAlignment(Pos.CENTER);

        
        listView = new ListView<>();
        listView.setPrefSize(500, 400);
        listView.setOnMouseClicked(e -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) showItemDetails(selectedItem, stage);
        });

        loadItemsFromDatabase();

        VBox content = new VBox(20, searchBox, listView);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        VBox root = new VBox(headerBox, content);
        root.setAlignment(Pos.TOP_CENTER);
        root.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(root, 700, 600);
        stage.setTitle("Shopper Page");
        stage.setScene(scene);
        stage.show();
    }

    
    private void openCart(Stage stage) {
        Stage cartStage = new Stage();
        VBox cartLayout = new VBox(10);
        cartLayout.setPadding(new Insets(20));
        cartLayout.setAlignment(Pos.CENTER);

        ListView<String> cartView = new ListView<>();
        ObservableList<String> cartItems = FXCollections.observableArrayList();
        double total = loadCartItems(cartItems);
        cartView.setItems(cartItems);

        Label totalLabel = new Label("Grand Total: $" + String.format("%.2f", total));
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setOnAction(e -> processCheckout(stage));

        cartLayout.getChildren().addAll(new Label("Your Cart:"), cartView, totalLabel, checkoutButton);

        Scene cartScene = new Scene(cartLayout, 400, 400);
        cartStage.setScene(cartScene);
        cartStage.setTitle("My Cart");
        cartStage.show();
    }

    private double loadCartItems(ObservableList<String> cartItems) {
        double total = 0.0;
        String query = "SELECT msitem.ItemName, msitem.ItemPrice, mscart.Quantity " +
                       "FROM mscart JOIN msitem ON mscart.ItemID = msitem.ItemID " +
                       "WHERE mscart.UserID = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, loggedInUserID);
            ResultSet rs = stmt.executeQuery();

            cartItems.clear();
            while (rs.next()) {
                String name = rs.getString("ItemName");
                double price = rs.getDouble("ItemPrice");
                int quantity = rs.getInt("Quantity");

                cartItems.add(name + " - $" + price + " x " + quantity);
                total += price * quantity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    private void processCheckout(Stage stage) {
        try (Connection conn = DatabaseUtil.connect()) {
            String headerQuery = "INSERT INTO transactionheader (UserID, DateCreated, Status) VALUES (?, CURDATE(), 'In Queue')";
            PreparedStatement headerStmt = conn.prepareStatement(headerQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            headerStmt.setInt(1, loggedInUserID);
            headerStmt.executeUpdate();

            ResultSet keys = headerStmt.getGeneratedKeys();
            if (keys.next()) {
                int transactionID = keys.getInt(1);

                String detailQuery = "INSERT INTO transactiondetail (TransactionID, ItemID, Quantity) " +
                                     "SELECT ?, mscart.ItemID, mscart.Quantity FROM mscart WHERE mscart.UserID = ?";
                PreparedStatement detailStmt = conn.prepareStatement(detailQuery);
                detailStmt.setInt(1, transactionID);
                detailStmt.setInt(2, loggedInUserID);
                detailStmt.executeUpdate();

                String clearCartQuery = "DELETE FROM mscart WHERE UserID = ?";
                PreparedStatement clearCartStmt = conn.prepareStatement(clearCartQuery);
                clearCartStmt.setInt(1, loggedInUserID);
                clearCartStmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Checkout completed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Checkout failed!");
        }
    }

    
    private void showItemDetails(String selectedItem, Stage stage) {
        String itemName = selectedItem.split(" \\(")[0];
        String query = "SELECT * FROM msitem WHERE ItemName = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("ItemStock");
                double price = rs.getDouble("ItemPrice");
                String description = rs.getString("ItemDesc");
                int itemID = rs.getInt("ItemID");

                
                Stage detailStage = new Stage();
                VBox layout = new VBox(10);
                layout.setPadding(new Insets(20));

                Label nameLabel = new Label(itemName);
                nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                Label priceLabel = new Label("Price: $" + price);
                Label stockLabel = new Label("Stock: " + stock + " left");
                Label descLabel = new Label("Description: " + description);

                Spinner<Integer> quantitySpinner = new Spinner<>(1, stock, 1);
                Button addToCartButton = new Button("Add to Cart");

                addToCartButton.setOnAction(e -> {
                    int selectedQuantity = quantitySpinner.getValue();
                    addItemToCart(loggedInUserID, itemID, selectedQuantity, stock, detailStage);
                });

                layout.getChildren().addAll(nameLabel, priceLabel, stockLabel, descLabel, quantitySpinner, addToCartButton);

                Scene scene = new Scene(layout, 400, 300);
                detailStage.setTitle("Item Details");
                detailStage.setScene(scene);
                detailStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load item details.");
        }
    }


    private void addItemToCart(int userID, int itemID, int quantity, int stock, Stage stage) {
        String checkQuery = "SELECT Quantity FROM mscart WHERE UserID = ? AND ItemID = ?";
        String updateQuery = "UPDATE mscart SET Quantity = Quantity + ? WHERE UserID = ? AND ItemID = ?";
        String insertQuery = "INSERT INTO mscart (UserID, ItemID, Quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, userID);
            checkStmt.setInt(2, itemID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("Quantity");
                if (currentQuantity + quantity > stock) {
                    showAlert(Alert.AlertType.WARNING, "Stock Limit Exceeded",
                            "Cannot add more items. Stock limit reached!");
                } else {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setInt(2, userID);
                        updateStmt.setInt(3, itemID);
                        updateStmt.executeUpdate();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Item quantity updated in cart!");
                    }
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, userID);
                    insertStmt.setInt(2, itemID);
                    insertStmt.setInt(3, quantity);
                    insertStmt.executeUpdate();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Item added to cart!");
                }
            }
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add item to cart.");
        }
    }


    private void loadCategories() {
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT ItemCategory FROM msitem");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categoryFilter.getItems().add(rs.getString("ItemCategory"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadItemsFromDatabase() {
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT ItemName FROM msitem WHERE ItemStock > 0");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                itemsList.add(rs.getString("ItemName"));
            }
            listView.setItems(itemsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchItems(String searchText, String category) {
        itemsList.clear();
        String query = "SELECT ItemName FROM msitem WHERE ItemName LIKE ? AND ItemStock > 0";
        if (category != null) query += " AND ItemCategory = ?";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            if (category != null) stmt.setString(2, category);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) itemsList.add(rs.getString("ItemName"));
            listView.setItems(itemsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setLoggedInEmail(String email, int userID) {
        this.loggedInEmail = email;
        this.loggedInUserID = userID;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
