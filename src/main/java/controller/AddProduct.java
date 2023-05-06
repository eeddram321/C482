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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainController.confirmDialog;

/**
 * AddProduct class
 * @author Eduardo Ramirez
 */
public class AddProduct implements Initializable {
    /**
     * TextField for ID
     */
    public TextField modifyProductID;
    /**
     * TextField for Name
     */
    public TextField modifyProductName;
    /**
     * TextField for Inventory
     */
    public TextField modifyProductInventory;
    /**
     * TextField for Price
     */
    public TextField modifyProductPrice;
    /**
     * TextField for Min
     */
    public TextField modifyProductMin;
    /**
     * TextField for Max
     */
    public TextField modifyProductMax;
    /**
     * Observable List associateParts holds parts.
     */
    private ObservableList<Part> associateParts = FXCollections.observableArrayList();
    /**
     * TextField to search products
     */
    public TextField productSearchBoxAdd;

    /**
     * ID column in the relatedProduct table
     */
    public TableColumn relatedProductIDColumn;
    /**
     * Name column in related product table
     */
    public TableColumn relatedPartNameColumn;
    /**
     * Inventory or stock column in the related product table
     */
    public TableColumn relatedInvColumn;
    /**
     * price column in related product table
     */
    public TableColumn relatedPriceColumn;
    /**
     * Add button
     */
    public Button AddButtonAddProduct;
    /**
     * Save button
     */
    public Button saveButtonAddProduct;
    /**
     * Cancel button
     *
     */
    public Button cancelButtonAddProduct;
    /**
     *remove part button
     */
    public Button removePartButton;
    /**
     *ID column in PartTV
     */
    public TableColumn addProductPartIDColumn;
    /**
     * Name column in PartTV
     */
    public TableColumn addPartNameColumn;
    /**
     *Inventory or stock column in PartTV
     */
    public TableColumn addProductINVColumn;
    /**
     *Price column in PartTV
     */
    public TableColumn addProductPriceColumn;
    /**
     *Associated parts table
     */
    public TableView<Part> associatedPartTV;
    /**
     *Product Table
     */
    public  TableView<Part> partTV;
    /**
     * Gives products ID's
     */
    public static int productId = 10;


    /**
     * allows user to search by name or id of the product in the addProductForm screen.
     * @param actionEvent
     */
    public void productSearchPartAdd(ActionEvent actionEvent) {
        String searchText = productSearchBoxAdd.getText();
        ObservableList<Part> temp = FXCollections.observableArrayList();
        try {
            System.out.println("hello");
            int a = Integer.parseInt(searchText);
            Part part = Inventory.lookupPart(a);
            if(part != null){
                partTV.getSelectionModel().select(part);
            }else{
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            temp = Inventory.lookupPart(searchText);
            if(! temp.isEmpty()) {
                partTV.setItems(temp);
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No product found.");
                alert.show();
            }



            return;


        }
    }

    /**
     * adds the selected part from the top tableview to the bottom tableview on the addProductForm screen.
     * @param actionEvent
     */
    public void clickAdd_AddProduct(ActionEvent actionEvent) {
        Part selectedPart = partTV.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associateParts.add(selectedPart);
            associatedPartTV.setItems(associateParts);
           //updatePartTable();
           //updateAssociatedPartTable();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect Value");
            alert.showAndWait();
            return;
        }
    }
    /**
     */
    /**
     * Saves product being added when clicking the save button on the addProductForm screen.
     * On clickSaveProduct I was getting a RUNTIME ERROR java.lang.error nullPointerException error. I had to wrap the whole body of the code inside the "try" and "catch" blocks for my code to work.
     * @param actionEvent
     */
    public void clickSaveProduct(ActionEvent actionEvent) {
        try {

            int id = ++productId;
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInventory.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            int max = Integer.parseInt(modifyProductMax.getText());




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

            for (Part part: associateParts){
                    product.addAssociatedParts(part);
            }



            Inventory.addProduct(product);
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
     * takes user back to the main screen when cancel is clicked
     * @param event
     * @throws IOException
     */
    public void clickProductCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/MainView.fxml"));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * removes the part selected in the bottom tableview.
     * @param actionEvent
     */
    public void clickRemovePart(ActionEvent actionEvent) {
        Part deletePartSelected = associatedPartTV.getSelectionModel().getSelectedItem();
        if (deletePartSelected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("You must select a value");
            alert.showAndWait();
            return;
        } else if (confirmDialog("Warning!", "Would you like to delete this product?")) {
            Part product = associatedPartTV.getSelectionModel().getSelectedItem();

            if (associateParts.contains(deletePartSelected)) {
                associateParts.remove(deletePartSelected);
                associatedPartTV.setItems(associateParts);


            }

        }
    }

    public void updatePartTable() {
        partTV.setItems(Inventory.getAllParts());
    }

    private void updateAssociatedPartTable() {
       associatedPartTV.setItems(associateParts);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /**
         * populates tables
         */
        partTV.setItems(Inventory.getAllParts());
        addProductPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductINVColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTV.setItems(associateParts);
        relatedProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        relatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        relatedInvColumn.setCellValueFactory(new  PropertyValueFactory<>("stock"));
        relatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

}
