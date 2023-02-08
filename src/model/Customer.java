package model;

/**
 * Has all the information I need for the customers information that I will be using in the database.
 * Also created the setters and getters and will also make the constructor.
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostal;
    private String customerPhone;
    private String divisionName;
    private int divisionId;

    private int countryId;

    /**
     *
     * @param customerId
     * @param customerName
     * @param customerAddress
     * @param customerPostal
     * @param customerPhone
     * @param divisionId
     * @param divisionName
     * @param countryId
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerPostal, String customerPhone, int divisionId, String divisionName, int countryId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostal = customerPostal;
        this.customerPhone = customerPhone;
        this.divisionName = divisionName;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }

    /**
     * Gets division name.
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the countryId.
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the countryId.
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets division name.
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets customer name.
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets customer name.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets customers address.
     * @return customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets customer address.
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Gets customer postal code.
     * @return customerPostal
     */
    public String getCustomerPostal() {
        return customerPostal;
    }

    /**
     * Sets customer postal.
     * @param customerPostal
     */
    public void setCustomerPostal(String customerPostal) {
        this.customerPostal = customerPostal;
    }

    /**
     * Gets customers phone number.
     * @return customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Sets customers phone.
     * @param customerPhone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Gets customers ID.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer ID.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets division ID.
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division ID.
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
