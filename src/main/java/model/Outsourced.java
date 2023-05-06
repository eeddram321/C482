package model;

/**
 * Outsourced class
 * @author Eduardo Ramirez
 */

public class Outsourced extends Part{

    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);

        this.companyName = companyName;
    }

    /**
     * gets companName
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * sets companyName
     * @param companyName
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
