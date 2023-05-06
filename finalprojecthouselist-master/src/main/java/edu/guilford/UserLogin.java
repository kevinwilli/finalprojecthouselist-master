package edu.guilford;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JavaFX App
 */
public class UserLogin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // sets the title of the window
        primaryStage.setTitle("Welcome Screen");

        // Create buttons for owner and customer login
        Button ownerLoginButton = new Button("Owner Login");
        Button customerLoginButton = new Button("Customer Login");

        // Set event handlers for the buttons
        ownerLoginButton.setOnAction(event -> openOwnerLogin());
        customerLoginButton.setOnAction(event -> openCustomerLogin());

        // Create a vertical layout and add the buttons
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(ownerLoginButton, customerLoginButton);

        // Set the layout as the scene content
        Scene scene = new Scene(layout, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openOwnerLogin() {
        // Create a dialog for login
        TextInputDialog loginDialog = new TextInputDialog();
        loginDialog.setTitle("Owner Login");
        loginDialog.setHeaderText("Please enter your username and password:");
        loginDialog.setContentText("Username:");

        // Show the login dialog and wait for user input
        Optional<String> usernameResult = loginDialog.showAndWait();

        if (usernameResult.isPresent()) {
            // Username entered, prompt for password
            loginDialog.setContentText("Password:");
            Optional<String> passwordResult = loginDialog.showAndWait();

            if (passwordResult.isPresent()) {
                // Password entered, validate login credentials
                String username = usernameResult.get();
                String password = passwordResult.get();

                // Perform login validation (replace with your actual validation logic)
                boolean isLoginValid = validateOwnerLogin(username, password);

                if (isLoginValid) {
                    // Login successful, open owner screen
                    openOwnerScreen(username);
                } else {
                    // Login failed, show an error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid username or password.");
                    alert.setContentText("Please try again.");
                    alert.showAndWait();
                }
            }
        }
    }

    private boolean validateOwnerLogin(String username, String password) {
        // Replace with your actual login validation logic
        // Example: Check against a database, file, or hardcoded values
        // Return true if the login is valid, false otherwise
        return username.equals("admin") && password.equals("admin1");
    }

    private void openOwnerScreen(String username) {
        // Create a new stage for the owner screen
        Stage ownerStage = new Stage();
        ownerStage.setTitle("Owner Screen");

        // Create GUI components for the owner screen
        Label titleLabel = new Label("Properties Owned by " + username + ":");
        ListView<String> propertyListView = new ListView<>();

        // Add sample property details to the property list view
        propertyListView.getItems().addAll(
                "Property 1: For Sale - $250,000\n" +
                        "Available Dates: May 15, 2023\n" +
                        "Lease Duration: N/A\n" +
                        "Rooms: 3\n" +
                        "Bathrooms: 2\n" +
                        "Closets: 4\n" +
                        "Garage: Yes\n" +
                        "Property Type: House\n" +
                        "Size: 2000 sq ft\n" +
                        "Address: 123 Main St, Anytown, USA",
                "Property 2: For Rent - $1,200/month\n" +
                        "Available Dates: June 1, 2023\n" +
                        "Lease Duration: 1 year\n" +
                        "Rooms: 2\n" +
                        "Bathrooms: 1\n" +
                        "Closets: 2\n" +
                        "Garage: No\n" +
                        "Property Type: Apartment\n" +
                        "Size: 800 sq ft\n" +
                        "Address: 456 Elm St, Anytown, USA");

        // Create a button to add a new property
        Button addPropertyButton = new Button("Add Property");
        addPropertyButton.setOnAction(e -> openAddPropertyDialog(propertyListView));

        // Create a vertical layout for the owner screen
        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, propertyListView, addPropertyButton);

        // Set the layout as the scene content
        Scene scene = new Scene(layout, 400, 300);
        ownerStage.setScene(scene);

        // Show the owner screen
        ownerStage.show();
    }

    private void openAddPropertyDialog(ListView<String> propertyListView) {
        // Create a dialog for adding a new property
        Dialog<String> addPropertyDialog = new Dialog<>();
        addPropertyDialog.setTitle("Add Property");
        addPropertyDialog.setHeaderText("Enter property details:");

        // Create dialog components
        // Note: You can add more components to the dialog if needed
        // For example, you can add a combo box to select the property type

        // Create a text field for entering property details
        //create a label for the text field
        Label propertyDetailsLabel = new Label("Property Details:");

        TextField propertyDetailsField = new TextField();
        propertyDetailsField.setPromptText("Enter property details");

        //get the text from the text field and set it to the label
        propertyDetailsLabel.textProperty().bind(propertyDetailsField.textProperty());

        // Set the dialog content
       // addPropertyDialog.getDialogPane().setContent(propertyDetailsField);

        // Add buttons to the dialog
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Add buttons to the dialog
        addPropertyDialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        // Enable or disable the Add button based on the text input
        Node addButtonNode = addPropertyDialog.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);
        propertyDetailsField.textProperty()
                .addListener((observable, oldValue, newValue) -> addButtonNode.setDisable(newValue.trim().isEmpty()));

        // Set the result converter to return the property details entered
        addPropertyDialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton)
                return propertyDetailsField.getText();
            return null;
        });

        // Show the add property dialog and wait for user input
        addPropertyDialog.showAndWait().ifPresent(propertyDetails -> {
            // Add the new property to the property list view
            propertyListView.getItems().add(propertyDetails);
        });
    }

    private void openCustomerLogin() {
        // Create a dialog for login
        TextInputDialog loginDialog = new TextInputDialog();
        loginDialog.setTitle("Customer Login");
        loginDialog.setHeaderText("Please enter your username and password:");
        loginDialog.setContentText("Username:");

        // Show the login dialog and wait for user input
        Optional<String> usernameResult = loginDialog.showAndWait();

        if (usernameResult.isPresent()) {
            // Username entered, prompt for password
            loginDialog.setContentText("Password:");
            Optional<String> passwordResult = loginDialog.showAndWait();

            if (passwordResult.isPresent()) {
                // Password entered, validate login credentials
                String username = usernameResult.get();
                String password = passwordResult.get();

                // Perform login validation (replace with your actual validation logic)
                boolean isLoginValid = validateCustomerLogin(username, password);

                if (isLoginValid) {
                    // Login successful, open owner screen
                    openCustomerScreen(username);
                } else {
                    // Login failed, show an error message ERROR EXCEPTION HANDLER (WE HAVE TO BUILD
                    // ONE)
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid username or password.");
                    alert.setContentText("Please try again.");
                    alert.showAndWait();
                }
            }
        }
    }

    private void openCustomerScreen(String username) {
        // Create a new stage for the owner screen
        Stage customerStage = new Stage();
        customerStage.setTitle("Customer Screen");

        // Create GUI components for the owner screen
        Label titleLabel = new Label("Properties Available: ");
        ListView<String> propertyListView = new ListView<>();

        // Add sample property details to the property list view
        propertyListView.getItems().addAll(
                "Property 1: For Sale - $250,000\n" +
                        "Available Dates: May 15, 2023\n" +
                        "Lease Duration: N/A\n" +
                        "Rooms: 3\n" +
                        "Bathrooms: 2\n" +
                        "Closets: 4\n" +
                        "Garage: Yes\n" +
                        "Property Type: House\n" +
                        "Size: 2000 sq ft\n" +
                        "Address: 123 Main St, Anytown, USA",
                "Property 2: For Rent - $1,200/month\n" +
                        "Available Dates: June 1, 2023\n" +
                        "Lease Duration: 1 year\n" +
                        "Rooms: 2\n" +
                        "Bathrooms: 1\n" +
                        "Closets: 2\n" +
                        "Garage: No\n" +
                        "Property Type: Apartment\n" +
                        "Size: 800 sq ft\n" +
                        "Address: 456 Elm St, Anytown, USA");

        // Create a vertical layout for the Customer screen
        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, propertyListView);

        // Set the layout as the scene content
        Scene scene = new Scene(layout, 400, 300);
        customerStage.setScene(scene);

        // Show the owner screen
        customerStage.show();
    }

    private boolean validateCustomerLogin(String username, String password) {
        // Replace with your actual login validation logic
        // Example: Check against a database, file, or hardcoded values
        // Return true if the login is valid, false otherwise
        return username.equals("custo") && password.equals("custo1");
    
    }
}
