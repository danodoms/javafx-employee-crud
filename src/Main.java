/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
      // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeMgmt.fxml"));
        Parent root = loader.load();
        
        // Create a scene with the loaded FXML content
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("employeemgmt.css").toExternalForm());

        
        primaryStage.setTitle("Manage Employee");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
