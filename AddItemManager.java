package pack;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddItemManager extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		// Create a new Stage for the popup window
        Stage popupStage = new Stage();

        // Make the popup window modal (blocks interaction with the main window)
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add Item");

        // Main layout
        BorderPane borderPane = new BorderPane();

        // Title at the top-right corner
        Label titleLabel = new Label("Add Item");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 10;");
        borderPane.setTop(titleBox);

        // Center layout for form fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-padding: 20;");

        // Add labels and text fields
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);

	Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(3); // Set preferred height
        descriptionArea.setWrapText(true);  // Enable word wrap
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionArea, 1, 1);

	
	Label catLabel = new Label("Item Category:");
        TextField catField = new TextField();
        gridPane.add(catLabel, 0, 2);
        gridPane.add(catField, 1, 2);

	Label priceLabel = new Label("Item Price:");
        TextField priceField = new TextField();
        gridPane.add(priceLabel, 0, 3);
        gridPane.add(priceField, 1, 3);


        Label quantityLabel = new Label("Quantity:");
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 1000, 1); // Min: 1, Max: 1000, Initial: 1
        quantitySpinner.setEditable(true); // Allow manual entry
        gridPane.add(quantityLabel, 0, 4);
        gridPane.add(quantitySpinner, 1, 4);

        

        // Buttons at the bottom
        Button submitButton = new Button("Submit");
        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> popupStage.close()); // Close the popup

        submitButton.setOnAction(e -> {
            // Get data from fields
            String name = nameField.getText();
	    String desc = descriptionArea.getText();
 	    String cat = catField.getText();
	    String priceStr = priceField.getText();
            Integer quantity = quantitySpinner.getValue();
            
            // Validate input
            String errorMessage = DatabaseUtil.validateInput(name, quantity, priceStr, desc);
            if (!errorMessage.isEmpty()) {
                DatabaseUtil.showAlert(Alert.AlertType.ERROR, "Validation Error", errorMessage);
                return;
            }

            try {
                double quantitas = Double.parseDouble(priceStr);

                // Insert data into database
                if (DatabaseUtil.additemmanagerdb(name, priceStr, desc, quantity, cat)) {
                	DatabaseUtil.showAlert(Alert.AlertType.INFORMATION, "Success", "Item added successfully!");
                    // Clear fields after submission
                    nameField.clear();
                    priceField.clear();
                    descriptionArea.clear();
                    catField.clear();
                    
                } else  {
                	DatabaseUtil.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add item. Please try again.");
                }
            } catch (Exception ex) {
            	DatabaseUtil.showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred!");
                ex.printStackTrace();
            }
        });

        HBox buttonBox = new HBox(10, closeButton, submitButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-padding: 20;");
        borderPane.setBottom(buttonBox);

        // Add the grid to the center
        borderPane.setCenter(gridPane);

        // Create and set the scene
        Scene popupScene = new Scene(borderPane, 800, 550);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
}
		
	}


