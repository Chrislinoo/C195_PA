package model;

public class Contacts {

    private int contactId;
    private String contactName;
    private String email;

    /**
     * constructor for Contacts
     * @param contactId
     * @param contactName
     * @param email
     */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * get contact id method
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * set contact id method
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * get contact name method
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * set contact name method
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * get email method
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * email setter method
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
