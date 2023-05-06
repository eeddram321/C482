package model;

/**
 * Inhouse class
 * @author Eduardo Ramirez
 */

public class InHouse extends Part{



    private int machineID;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);

        this.machineID = machineID;
    }
    /**
     * @return the machineid
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * sets machineID
     * @param machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }


}
