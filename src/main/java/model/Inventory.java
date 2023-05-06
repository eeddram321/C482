package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 * Inventory class
 * @author Eduardo Ramirez
 */
public class Inventory {

    /**
     * list of all parts in Inventory class.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * list of all products in Inventory class.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * gets list of all the parts in the Inventory class.
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * adds part to the Inventory class
     * @param newPart
     */

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }


    /**
     * gets all products from the Inventory class.
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Adds a product to the inventory class.
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * searches list of parts by id.
     * @param id
     * @return
     */
    public static Part lookupPart(int id){
        Part temporary = null;
        for (Part part: allParts){
            if (id == part.getId()){
                temporary = part;
                return temporary;
            }
        }
        return temporary;
    }

    /**
     * searches list of products by id.
     * @param productID
     * @return
     */
    public static Product lookupProduct(int productID){
        Product temporary = null;
        for(Product product : allProducts){
            if(productID == product.getProductID()){
                temporary = product;
            }
        }
        return temporary;
    }

    /**
     * searches list of parts by name or characters.
     * @param partNameSearch
     * @return
     */
    public static ObservableList<Part> lookupPart(String partNameSearch){
        ObservableList<Part> partFound = FXCollections.observableArrayList();

        if (partNameSearch.length() == 0){
            partFound = allParts;
        }
        else {
            for (Part part : allParts){
                if(part.getName().toLowerCase().contains(partNameSearch.toLowerCase())){
                    partFound.add(part);
                }
            }
        }
        return partFound;
    }

    /**
     * searches products by names or characters
     * @param productNameSearch
     * @return
     */
    public static ObservableList lookupProduct(String productNameSearch) {
        ObservableList<Product> productFound = FXCollections.observableArrayList();

        if (productNameSearch.length() == 0){
            productFound = allProducts;
        }
        else {
            for (Product product : allProducts){
                if(product.getName().toLowerCase().contains(productNameSearch.toLowerCase())){
                    productFound.add(product);
                }
            }
        }
        return productFound;
    }

    /**
     * replaces parts in the parts list
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * replaces product in the product list
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes part from parts list
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * deletes product from the product list
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
}
