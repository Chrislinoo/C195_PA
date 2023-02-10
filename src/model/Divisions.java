package model;

public class Divisions {

    private int divisionId;
    private String divisionName;
    private int countryId;

    /**
     * Constructor for Divisions class.
     * @param divisionId
     * @param divisionName
     * @param countryId
     */
    public Divisions(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * Gets division ID.
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division ID.
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the division name.
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the division name.
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets the country ID.
     * @return countryId.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the country ID.
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Used to return the division names in the combo boxes instead of the hex value.
     * @return divisionName
     */
    @Override
    public String toString(){
        return divisionId + " - " + divisionName;
    }
}
