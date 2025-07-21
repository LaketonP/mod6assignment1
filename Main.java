import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    // define UI components
    private TextField Id = new TextField();
    private TextField LastName = new TextField();
    private TextField FirstName = new TextField();
    private TextField Mi = new TextField();
    private TextField Address = new TextField();
    private TextField City = new TextField();
    private TextField State = new TextField();
    private TextField Telephone = new TextField();
    private TextField Email = new TextField();
    private Label lblStatus = new Label();
    // database connection
    private Connection connection;
    @Override
    public void start(Stage primaryStage) {
        connectToDB();
        // create UI
        GridPane pane = new GridPane();
        pane.setVgap(5);
        pane.setHgap(5);
        // UI components to pane
        pane.addRow(0, new Label("ID"), Id);
        pane.addRow(1, new Label("Last Name"), LastName);
        pane.addRow(2, new Label("First Name"), FirstName);
        pane.addRow(3, new Label("MI"), Mi);
        pane.addRow(4, new Label("Address"), Address);
        pane.addRow(5, new Label("City"), City);
        pane.addRow(6, new Label("State"), State);
        pane.addRow(7, new Label("Telephone"), Telephone);
        pane.addRow(8, new Label("Email"), Email);
        // buttons
        HBox buttons = new HBox(10);
        Button view = new Button("View");
        Button insert = new Button("Insert");
        Button update = new Button("Update");
        Button clear = new Button("Clear");
        // buttons to pane
        buttons.getChildren().addAll(view, insert, update, clear);
        pane.add(buttons, 1, 9);
        pane.add(lblStatus, 1, 10);
        // button actions
        view.setOnAction(e -> viewStaff());
        insert.setOnAction(e -> insertStaff());
        update.setOnAction(e -> updateStaff());
        clear.setOnAction(e -> clearFields());
        // create scene and show stage
        Scene scene = new Scene(pane, 450, 450);
        primaryStage.setTitle("Staff Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // connect to database
    private void connectToDB() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:staff.db");
        } catch (SQLException ex) {
            lblStatus.setText("Database connection failed.");
        }
    }
    // view staff function - retrieve data from database
    private void viewStaff() {
        String id = Id.getText().trim();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Staff WHERE id = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery(); // execute query
            if (rs.next()) {
                LastName.setText(rs.getString("lastName"));
                FirstName.setText(rs.getString("firstName"));
                Mi.setText(rs.getString("mi"));
                Address.setText(rs.getString("address"));
                City.setText(rs.getString("city"));
                State.setText(rs.getString("state"));
                Telephone.setText(rs.getString("telephone"));
                Email.setText(rs.getString("email"));
                lblStatus.setText("Record found");
            } else {
                lblStatus.setText("Record not found");
            }
        } catch (SQLException ex) {
            lblStatus.setText("Error viewing record.");
        }
    }
    // insert staff function - insert data into database
    private void insertStaff() {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, Id.getText().trim());
            stmt.setString(2, LastName.getText().trim());
            stmt.setString(3, FirstName.getText().trim());
            stmt.setString(4, Mi.getText().trim());
            stmt.setString(5, Address.getText().trim());
            stmt.setString(6, City.getText().trim());
            stmt.setString(7, State.getText().trim());
            stmt.setString(8, Telephone.getText().trim());
            stmt.setString(9, Email.getText().trim());
            stmt.executeUpdate();
            lblStatus.setText("Record inserted");
        } catch (SQLException ex) {
            lblStatus.setText("Insert failed: " + ex.getMessage());
        }
    }
    // update staff function - update data in database
    private void updateStaff() {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE Staff SET lastName=?, firstName=?, mi=?, address=?, city=?, state=?, telephone=?, email=? WHERE id=?")) {
            stmt.setString(1, LastName.getText().trim());
            stmt.setString(2, FirstName.getText().trim());
            stmt.setString(3, Mi.getText().trim());
            stmt.setString(4, Address.getText().trim());
            stmt.setString(5, City.getText().trim());
            stmt.setString(6, State.getText().trim());
            stmt.setString(7, Telephone.getText().trim());
            stmt.setString(8, Email.getText().trim());
            stmt.setString(9, Id.getText().trim());
            stmt.executeUpdate();
            lblStatus.setText("Record updated");
        } catch (SQLException ex) {
            lblStatus.setText("Update failed: " + ex.getMessage());
        }
    }
    // clear all fields
    private void clearFields() {
        Id.clear();
        LastName.clear();
        FirstName.clear();
        Mi.clear();
        Address.clear();
        City.clear();
        State.clear();
        Telephone.clear();
        Email.clear();
        lblStatus.setText("");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
