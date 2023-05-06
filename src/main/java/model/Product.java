package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Prouduct Class
 * @author Eduardo Ramirez
 */
public class Product {


    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int productID, String name, double price, int stock, int min, int max) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getProductID() {
        return productID;
    }
    /**
     * id the id to set
     *
     */

    public void setProductID(int productID) {
        this.productID = productID;
    }
    /**
     * @return the name
     */

    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */

    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the price
     */

    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */

    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the stock
     */

    public int getStock() {
        return stock;
    }
    /**
     * @param stock the stock to set
     */

    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * @return the min
     */

    public int getMin() {
        return min;
    }
    /**
     * @param min the min to set
     */

    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the max
     */

    public int getMax() {
        return max;
    }
    /**
     * @param max the max to set
     */

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * adds part to associatedParts list.
     * @param part
     */

    public void addAssociatedParts(Part part){
        this.associatedParts.addAll(part);
    }

    /**
     * returs associatedParts list
     * @return
     */

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    /**
     *
     * @return associatedParts list.
     */

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * sets associatedParts list.
     * @param associatedParts
     */

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
}
