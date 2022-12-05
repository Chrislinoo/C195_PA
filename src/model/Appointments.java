package model;

import java.time.LocalDateTime;

public class Appointments {
    private int appointmentId;
    private String aptTitle;
    private String aptDescription;
    private String aptLocation;
    private String aptType;
    private LocalDateTime aptStartTime;
    private LocalDateTime aptEndTime;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Constructor for the Appointments class
     * @param appointmentId
     * @param aptTitle
     * @param aptDescription
     * @param aptLocation
     * @param aptType
     * @param aptStartTime
     * @param aptEndTime
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointments(int appointmentId, String aptTitle, String aptDescription, String aptLocation, String aptType, LocalDateTime aptStartTime, LocalDateTime aptEndTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.aptTitle = aptTitle;
        this.aptDescription = aptDescription;
        this.aptLocation = aptLocation;
        this.aptType = aptType;
        this.aptStartTime = aptStartTime;
        this.aptEndTime = aptEndTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * get appointment method
     * @return appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * apt ID setter
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * get apt title
     * @return aptTitle
     */
    public String getAptTitle() {
        return aptTitle;
    }

    /**
     * apt title setter
     * @param aptTitle
     */
    public void setAptTitle(String aptTitle) {
        this.aptTitle = aptTitle;
    }

    /**
     * get apt description
     * @return aptDescription
     */
    public String getAptDescription() {
        return aptDescription;
    }

    /**
     *set apt description
     * @param aptDescription
     */
    public void setAptDescription(String aptDescription) {
        this.aptDescription = aptDescription;
    }

    /**
     *get apt location
     * @return aptLocation
     */
    public String getAptLocation() {
        return aptLocation;
    }

    /**
     *set apt location
     * @param aptLocation
     */
    public void setAptLocation(String aptLocation) {
        this.aptLocation = aptLocation;
    }

    /**
     *get apt type
     * @return aptType
     */
    public String getAptType() {
        return aptType;
    }

    /**
     *set apt type
     * @param aptType
     */
    public void setAptType(String aptType) {
        this.aptType = aptType;
    }

    /**
     *get strat time
     * @return aptStartTime
     */
    public LocalDateTime getAptStartTime() {
        return aptStartTime;
    }

    /**
     *set apt start time
     * @param aptStartTime
     */
    public void setAptStartTime(LocalDateTime aptStartTime) {
        this.aptStartTime = aptStartTime;
    }

    /**
     *get apt end time
     * @return aptEndTime
     */
    public LocalDateTime getAptEndTime() {
        return aptEndTime;
    }

    /**
     *set apt end time
     * @param aptEndTime
     */
    public void setAptEndTime(LocalDateTime aptEndTime) {
        this.aptEndTime = aptEndTime;
    }

    /**
     * get customer id
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *set customer id
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *get user id
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     *set user id
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *get contact id
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     *set contact id
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
