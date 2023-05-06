package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddPart class
 * @author Eduardo Ramirez
 */
public class AddPart implements Initializable {
    /**
     * Inhouse Radio Button
     */
    public RadioButton radioInHousePart;
    /**
     * Toggles radio buttons
     */
    public ToggleGroup tGroup;
    /**
     * Outsourced radio button
     */
    public RadioButton radioOutsourcedPart;
    /**
     * MachineID and Company name
     */
    public Label machineIDComp;
    /**
     * TextField for Max
     */

    public TextField MaxAddPart;
    /**
     * Save button
     */
    public Button SaveAddPart;
    /**
     * Cancel button
     */
    public Button CancelAddPart;
    /**
     * Assigns random ID's to parts
     */
    public static  int machineID = 10;
    /**
     * TextField for ID
     */
    public TextField addPartID;
    /**
     * TextField for name
     */
    public TextField addPartName;
    /**
     * TextField for Inventory
     */
    public TextField addPartInventory;
    /**
     * TextField for Price
     */
    public TextField addPartPrice;
    /**
     * TextField for MachineID
     */
    public TextField addPartMachineID;
    /**
     * TextField for MIN
     */
    public TextField addPartMin;

    /**
     * machineID++ generates a different id for all the parts.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartID.setText(String.valueOf(machineID++));

        
    }

    /**
     * Inhouse
     * @param actionEvent
     */
    public void InHouseAddPart(ActionEvent actionEvent) {
        machineIDComp.setText("Machine ID");
    }

    /**
     * Outsourced
     * @param actionEvent
     */

    public void OSAddPart(ActionEvent actionEvent) {

        machineIDComp.setText("Company Name");
    }

    /**
     *
     *
     * Saves the part being added when save button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void SaveAddPartButton(ActionEvent actionEvent) throws IOException {
        try {

            int id = Integer.parseInt(addPartID.getText());
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInventory.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(MaxAddPart.getText());


            String companyName;

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            }
            //Inventory should be between the min and max values.
            else if (stock < min || max < stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }
            System.out.println(radioInHousePart.isSelected() + "inHouse");
            System.out.println(radioOutsourcedPart.isSelected() + "Outsourced");
            if (radioOutsourcedPart.isSelected()) {
                companyName = addPartMachineID.getText();
                Outsourced addPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(addPart);
            }
            if (radioInHousePart.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                InHouse addPart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.addPart(addPart);
            }
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/MainView.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();


            machineIDComp.setText("Save was clicked.");
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect Value");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Cancel takes user back to the main screen.
     * @param event
     * @throws IOException
     */
    public void CancelAddPartButton(ActionEvent event) throws IOException {
        machineIDComp.setText("Cancel was clicked.");
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/MainView.fxml"));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

}
