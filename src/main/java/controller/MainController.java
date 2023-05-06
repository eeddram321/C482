package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * MainController class
 * @author Eduardo Ramirez
 */


public class MainController implements Initializable {

    /**
     *Add button on Part table
     */
    public Button clickAddPart;
    /**
     *Part table
     */
    public TableView<Part> MVPartsTable;
    /**
     *Id column on Part table
     */
    public TableColumn<Part, Integer> partIDColumn;
    /**
     *Name column on Part table
     */
    public TableColumn<Part, String> partNameColumn;
    /**
     *Inventory column on Part table
     */
    public TableColumn<Part, Integer> partInventoryColumn;
    /**
     *Price column on Part table.
     */
    public TableColumn<Part, Double> partPriceColumn;
    /**
     *Modify button on Part table
     */
    public Button clickModifyPart;
    /**
     *Delete button
     */
    public Button clickDelete;
    /**
     *TextField to search parts
     */
    public TextField searchPart;
    /**
     *Product table
     */
    public TableView<Product> MVProductTable;
    /**
     *ID column for Product table
     */
    public TableColumn productIDColumn;
    /**
     *Name column for Product Table
     */
    public TableColumn productNameColumn;
    /**
     *Inventory column for Product Table
     */
    public TableColumn producktInventoryColumn;
    /**
     *Price column for product table
     */
    public TableColumn productPriceColumn;
    /**
     *Add button for Product
     */
    public Button clickAddProduct;
    /**
     *Modify button for Product
     */
    public Button clickModifyProduct;
    /**
     *Delete button for Product
     */
    public Button clickDeleteProduct;
    /**
     *TextField to search a product
     */
    public TextField SearchProduct;
    /**
     *Exit button
     */
    public Button MVExitButton;
    @FXML
    private Label welcomeText;

    /**
     * Takes user to the addPartForm screen when the add button is clicked
     * @param event
     * @throws IOException
     */
    public void MVAddParts(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/AddPartForm.fxml"));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Takes user to the modifyPartForm screen when the modify button is clicked.
     * @param event
     * @throws IOException
     */
    public void MVModifyPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/demo/ModifyPartForm.fxml"));
       Parent parent = loader.load();
       ModifyPart modifyPart = loader.getController();
       modifyPart.sendParData(MVPartsTable.getSelectionModel().getSelectedIndex(), MVPartsTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 800, 700);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes selected part from the part table in the main screen.
     * @param actionEvent
     */

    public void MVDeletePart(ActionEvent actionEvent) {
        if(MVPartsTable.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Product Selected", "Please choose product form the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?")){
            Part part = MVPartsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(part);
        }

    }

    /**
     * allows user to search for parts by character or ID
     * @param actionEvent
     */
    public void MVSearchPart(ActionEvent actionEvent) {
        String searchText = searchPart.getText();
        ObservableList<Part> temp = FXCollections.observableArrayList();
        try {
            System.out.println("hello");
            int a = Integer.parseInt(searchText);
            Part part = Inventory.lookupPart(a);
            if(part != null){
                MVPartsTable.getSelectionModel().select(part);
            }else{
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
          temp = Inventory.lookupPart(searchText);
          if(! temp.isEmpty()) {
              MVPartsTable.setItems(temp);
          }else {
              Alert alert = new Alert(Alert.AlertType.INFORMATION, "No parts found.");
              alert.show();
          }



          return;


        }
    }

    /**
     * takes you to the add product fxml screen when clicking the add button in the main screen.
     * @param event
     * @throws IOException
     */
    public void MVAddProduct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/AddProductForm.fxml"));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends you to the ModifyProductForm when clicking the modify button on the main screen.
     * @param event
     * @throws IOException
     */
    public void MVModifyProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/demo/ModifyProductForm.fxml"));
        Parent parent = loader.load();
        ModifyProduct modifyProduct = loader.getController();
        modifyProduct.sendProductData(MVProductTable.getSelectionModel().getSelectedIndex(), (Product) MVProductTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 900, 700);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Deletes selected part from the product table on the main screen.
     * @param actionEvent
     */
    public void MVDeleteProduct(ActionEvent actionEvent) {
        if(MVProductTable.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Product Selected", "Please choose product form the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this product?")){
            Product product = MVProductTable.getSelectionModel().getSelectedItem();
         //   int selectedPart = MVProductTable.getSelectionModel().getSelectedIndex();
         // Inventory.getAllProducts().remove(selectedPart);
            if(product.getAssociatedParts().size()>0) {
                //give alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Associated parts must be deleted");
                alert.showAndWait();
                return;
            }
            Inventory.deleteProduct(product);
          //  MVProductTable.getItems().remove(selectedPart);
        }
    }

    /**
     *
     * @param title
     * @param content
     * @return
     */
    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }

    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Allows user to search for product by characters or integers
     * @param actionEvent
     */
    public void MVSearchProduct(ActionEvent actionEvent) {
        String searchText = SearchProduct.getText();
        ObservableList<Product> temp = FXCollections.observableArrayList();
        try {
            int a = Integer.parseInt(searchText);
            Product product = Inventory.lookupProduct(a);
            if(product != null){
                MVProductTable.getSelectionModel().select(product);
            }else{
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            temp = Inventory.lookupProduct(searchText);
            if(! temp.isEmpty()) {
                MVProductTable.setItems(temp);
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No product found.");
                alert.show();
            }



            return;


        }
    }

    /**
     * Exits the application when clicking the exit button on the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void MainExitButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");

        /**
         * Populate the Product and Part table on the main screen.
         */

        MVPartsTable.setItems(Inventory.getAllParts());
        MVProductTable.setItems(Inventory.getAllProducts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        producktInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

}