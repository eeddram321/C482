package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.io.IOException;


/**
 * FUTURE ENHANCEMENT: All parts that are added when the application is open will display on the main screen after exiting and reopening the application
 * Main Class
 * @author Eduardo Ramirez
 */
public class Main extends Application {
    /**
     * Opens the MainView Form
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/demo/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Test Data for the application
     */
    private  static void addTestData(){
        //Test data for the main page
        InHouse o = new InHouse(1, "Wheel", 10.00, 10, 5, 8, 3 );
        Inventory.addPart(o);
        InHouse b = new InHouse(2, "Rim", 25.00, 2, 1, 8, 2);
        Inventory.addPart(b);

        Outsourced a = new Outsourced(3, "Handle", 4.00, 6, 4, 9, "gee" );
        Inventory.addPart(a);

        Inventory.addProduct(new Product(1, "Mongoose Bike", 10.00, 4, 3, 6));
        Inventory.addProduct(new Product(2, "Huffy Bike", 200.00, 2, 1, 4));

    }

    /**
     * Launches application
     * @param args
     */

    public static void main(String[] args) {

        addTestData();

        launch();
    }
}