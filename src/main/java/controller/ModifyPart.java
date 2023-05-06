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
 * ModifyPart class
 * @author Eduardo Ramirez
 */

public class ModifyPart implements Initializable {
    /**
     * InHouse radio button
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
     * Label for the machineID and Company
     */
    public Label machineIDComp;


    /**
     * Save button modifyPart
     */
    public Button SaveAddPart;
    /**
     * Cancel button on modifyPart
     */
    public Button CancelAddPart;
    /**
     *
     */

    private static int selected;
    /**
     * Textfield for ID
     */
    public TextField modifyPartID;
    /**
     * Textfield for name
     */
    public TextField modifyPartName;
    /**
     * TextField for Inventory
     */
    public TextField modifyPartInventory;
    /**
     * TextField for price
     */
    public TextField modifyPartPrice;
    /**
     * TextField for machineID
     */
    public TextField modifyPartMachineID;
    /**
     * TextField for Min
     */
    public TextField modifyPartMin;
    /**
     * TextField for Max
     */
    public TextField modifyPartMax;

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
     * Saves the part being modified in the modifyPartForm screen when the "Save" button is clicked.
     * @param actionEvent
     */

    public void SaveAddPartButton(ActionEvent actionEvent) {
        try {

            int id = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int stock = Integer.parseInt(modifyPartInventory.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());

            int machineID = 0;
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
                companyName = modifyPartMachineID.getText();
                Outsourced addPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(selected,addPart);
            }
            if (radioInHousePart.isSelected()) {
                machineID = Integer.parseInt(modifyPartMachineID.getText());
                InHouse addPart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.updatePart(selected,addPart);
            }
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/MainView.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();


            machineIDComp.setText("Save was clicked.");
        }catch (NumberFormatException | IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect Value");
            alert.showAndWait();
            return;
        }
    }

    /**
     * sends parts
     * @param selectedRow
     * @param part
     */
    public void sendParData(int selectedRow, Part part) {
        selected = selectedRow;
        if (part instanceof Outsourced) {
            radioOutsourcedPart.setSelected(true);
            machineIDComp.setText("CompanyName");
            modifyPartMachineID.setText(((Outsourced) part).getCompanyName());
        }else {
            radioInHousePart.setSelected(true);
            machineIDComp.setText("Machine Id");
            modifyPartMachineID.setText(String.valueOf(((InHouse) part).getMachineID()));
        }

        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText((part.getName()));
        modifyPartInventory.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
        modifyPartMax.setText(String.valueOf(part.getMax()));



}


    /**
     * Cancel button takes user back to the main screen when clicked.
     * @param event
     * @throws IOException
     */
    public void CancelAddPartButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/MainView.fxml"));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
