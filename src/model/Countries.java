package model;

public class Countries {

    private int id;
    private String name;

    /**
     * getter for id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * id setter method
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * name setter
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * get name method
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * constructor for Countries
     * @param id
     * @param name
     */
    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * Used to return the country names in the combo boxes instead of the hex value.
     * @return
     */
    @Override
    public String toString(){
        return name;
    }
}
