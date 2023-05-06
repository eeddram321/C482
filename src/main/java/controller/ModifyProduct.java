package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainController.confirmDialog;

/**
 *Modify Product Class
 * @author Eduardo Ramirez
 */
public class ModifyProduct implements Initializable {
    /**
     *TextField for ID
     */
    public TextField modifyProductID;
    /**
     *TextField for Name
     */
    public TextField modifyProductName;
    /**
     *TextField for Inventory
     */
    public TextField modifyProductInventory;
    /**
     *TextField for Price
     */
    public TextField modifyProductPrice;
    /**
     *TextField for Min
     */
    public TextField modifyProductMin;
    /**
     *TextField for Max
     */
    public TextField modifyProductMax;
    /**
     *Observable List contains all parts
     */
    private ObservableList<Part> associateParts = FXCollections.observableArrayList();
    /**
     *Selected variable
     */
    private static int selected;
    /**
     *index variable
     */
    private int index = 0;
    /**
     *Search TextField
     */
    public TextField productSearchBoxAdd;
    /**
     *Relate Product Table
     */
    public TableView<Part> relateProductTable;
    /**
     *ID column for relate product table
     */
    public TableColumn relatedProductIDColumn;
    /**
     *Name column for relate product table
     */
    public TableColumn relatedPartNameColumn;
    /**
     *Inventory column for relate product table
     */
    public TableColumn relatedInvColumn;
    /**
     * Price Column for relate product table
     */
    public TableColumn relatedPriceColumn;
    /**
     * Add button
     */
    public Button AddButtonAddProduct;
    /**
     *Save button
     */
    public Button saveButtonAddProduct;
    /**
     *Cancel button
     */
    public Button cancelButtonAddProduct;
    /**
     *remove part button
     */
    public Button removePartButton;
    /**
     *add product table
     */
    public TableView<Part> addProductTV;
    /**
     *ID column in add product table
     */
    public TableColumn addProductPartIDColumn;
    /**
     *Name column in add prodcut table
     */
    public TableColumn addPartNameColumn;
    /**
     *Inventory column in add product table
     */
    public TableColumn addProductINVColumn;
    /**
     *price column in add product table
     */
    public TableColumn addProductPriceColumn;

    /**
     * allows user to search by name or id of product.
     * @param actionEvent
     */
    public void productSearchPartAdd(ActionEvent actionEvent) {
        String searchText = productSearchBoxAdd.getText();
        ObservableList<Part> temp = FXCollections.observableArrayList();
        try {
            int a = Integer.parseInt(searchText);
            Part part = Inventory.lookupPart(a);
            if(part != null){
                addProductTV.getSelectionModel().select(part);
            }else{
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            temp = Inventory.lookupPart(searchText);
            if(! temp.isEmpty()) {
                addProductTV.setItems(temp);
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No product found.");
                alert.show();
            }



            return;

        }
    }


    /**
     * On clickSaveProduct I was getting a RUNTIME ERROR java.lang.error nullPointerException error. I had to wrap the whole body of the code inside the "try" and "catch" blocks for my code to work.
     * saves the product being modified
     * @param actionEvent
     */
    public void clickSaveProduct(ActionEvent actionEvent) {
        try {

        int id = Integer.parseInt(modifyProductID.getText());
        String name = modifyProductName.getText();
        int stock = Integer.parseInt(modifyProductInventory.getText());
        double price = Double.parseDouble(modifyProductPrice.getText());
        int min = Integer.parseInt(modifyProductMin.getText());
        int max = Integer.parseInt(modifyProductMax.getText());

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
    Product product = new Product(id, name, price,stock,min,max);
        for(Part part: associateParts){
                   product.addAssociatedParts(part);
           }
Inventory.updateProduct(selected,product);

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/MainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();


    }catch (NumberFormatException | IOException e){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setContentText("Incorrect Value");
        alert.showAndWait();
        return;
    }
    }

    /**
     * sends product data
     * @param selectedRow
     * @param product
     */
    public void sendProductData(int selectedRow, Product product ) {
        selected = selectedRow;

        modifyProductID.setText(String.valueOf(product.getProductID()));
        modifyProductName.setText((product.getName()));
        modifyProductInventory.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
        modifyProductMax.setText(String.valueOf(product.getMax()));

        for(Part part : product.getAssociatedParts()) {
            associateParts.add(part);

        }
      relateProductTable.setItems(associateParts);

    }



    /**
     *the add button, adds the part selected from the above table to the bottom table.
     * @param actionEvent
     */
    public void clickAdd_AddProduct(ActionEvent actionEvent) {
        Part selectedPart = addProductTV.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("You must select a value.");
            alert.showAndWait();
        }else {
            if (!associateParts.contains(selectedPart)) {
                associateParts.add(selectedPart);
                updateAssociatedPartTable();

            }
        }

    }

    private void updateAssociatedPartTable(){
        relateProductTable.setItems(associateParts);
    }


    /**
     * When cancel button is clicked it takes user back to the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void clickProductCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/MainView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * removes the parts selected from the bottom table of the modifyProductForm screen.
     * @param actionEvent
     */
    public void clickRemovePart(ActionEvent actionEvent) {
        Part deleteSelectedPart = (Part) relateProductTable.getSelectionModel().getSelectedItem();

        if (deleteSelectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("You must select a value!");
            alert.showAndWait();
            return;
        }else if(confirmDialog("Warning!", "Would you like to delete this product?")) {
            Part product = relateProductTable.getSelectionModel().getSelectedItem();
            if (associateParts.contains(deleteSelectedPart)){
            associateParts.remove(deleteSelectedPart);
            relateProductTable.setItems(associateParts);

        }

    }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Populates tables
         */
        addProductTV.setItems(Inventory.getAllParts());
        addProductPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductINVColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        relateProductTable.setItems(associateParts);
        relatedProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        relatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        relatedInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        relatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));





    }
}
