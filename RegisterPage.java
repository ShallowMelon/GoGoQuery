package pack;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);

        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        
        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();

        
        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();

        
        Label genderLabel = new Label("Gender:");
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);

        
        CheckBox termsCheckBox = new CheckBox("I agree to the terms and conditions");

        
        Button registerButton = new Button("Register");

        
        Label errorLabel = new Label();

        registerButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String dob = (dobPicker.getValue() != null) ? dobPicker.getValue().toString() : null;
            RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
            String gender = (selectedGender != null) ? selectedGender.getText() : null;
            boolean termsAccepted = termsCheckBox.isSelected();

            
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dob == null || gender == null || !termsAccepted) {
                errorLabel.setText("All fields are required.");
            } else if (!email.matches("^[\\w_.]+@gomail\\.com$")) {
                errorLabel.setText("Invalid email format. It must end with '@gomail.com'.");
            } else if (!password.equals(confirmPassword)) {
                errorLabel.setText("Passwords do not match.");
            } else if (java.time.Period.between(dobPicker.getValue(), java.time.LocalDate.now()).getYears() < 17) {
                errorLabel.setText("You must be at least 17 years old.");
            } else {
                boolean success = DatabaseUtil.registerUser(email, password, gender, dob);
                if (success) {
                    errorLabel.setText("Registration successful!");
                    RoleSelectionPage rolePage = new RoleSelectionPage();
                    rolePage.setUserEmail(email);
                    rolePage.start(primaryStage);
                } else {
                    errorLabel.setText("Error registering user. Please try again.");
                }
            }
        });

        root.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, confirmPasswordLabel, confirmPasswordField,
                dobLabel, dobPicker, genderLabel, male, female, termsCheckBox, registerButton, errorLabel);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
