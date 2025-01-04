package pack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueueManager {

    private TableView<Transaction> tableView;

    public void openQueuePopup(Stage parentStage) {
        
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Queue Manager");

        BorderPane root = new BorderPane();

        
        TableColumn<Transaction, Integer> transactionIDCol = new TableColumn<>("Transaction ID");
        transactionIDCol.setCellValueFactory(cellData -> cellData.getValue().transactionIDProperty().asObject());

        TableColumn<Transaction, Integer> customerIDCol = new TableColumn<>("Customer ID");
        customerIDCol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());

        TableColumn<Transaction, String> customerEmailCol = new TableColumn<>("Customer Email");
        customerEmailCol.setCellValueFactory(cellData -> cellData.getValue().customerEmailProperty());

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateCreatedProperty());

        TableColumn<Transaction, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        tableView = new TableView<>();
        tableView.getColumns().addAll(transactionIDCol, customerIDCol, customerEmailCol, dateCol, totalCol, statusCol);
        loadQueueData();

        
        Button sendPackageButton = new Button("Send Package");
        sendPackageButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        sendPackageButton.setOnAction(e -> markTransactionAsSent());

        VBox buttonBox = new VBox(sendPackageButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        root.setCenter(tableView);
        root.setBottom(buttonBox);

        Scene popupScene = new Scene(root, 800, 500);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void loadQueueData() {
        ObservableList<Transaction> queueList = FXCollections.observableArrayList();
        String query = "SELECT transactionheader.TransactionID, transactionheader.UserID, msuser.UserEmail, " +
                       "transactionheader.DateCreated, SUM(msitem.ItemPrice * transactiondetail.Quantity) AS Total, " +
                       "transactionheader.Status " +
                       "FROM transactionheader " +
                       "JOIN msuser ON transactionheader.UserID = msuser.UserID " +
                       "JOIN transactiondetail ON transactionheader.TransactionID = transactiondetail.TransactionID " +
                       "JOIN msitem ON transactiondetail.ItemID = msitem.ItemID " +
                       "GROUP BY transactionheader.TransactionID";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                queueList.add(new Transaction(
                    rs.getInt("TransactionID"),
                    rs.getInt("UserID"),
                    rs.getString("UserEmail"),
                    rs.getString("DateCreated"),
                    rs.getDouble("Total"),
                    rs.getString("Status")
                ));
            }
            tableView.setItems(queueList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markTransactionAsSent() {
        Transaction selectedTransaction = tableView.getSelectionModel().getSelectedItem();
        if (selectedTransaction == null) {
            showAlert(Alert.AlertType.ERROR, "Reference Error", "Please select a transaction.");
            return;
        }

        String updateQuery = "UPDATE transactionheader SET Status = 'Sent' WHERE TransactionID = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, selectedTransaction.getTransactionID());
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction marked as Sent.");
            loadQueueData(); 
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
}
