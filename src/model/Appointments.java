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

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAptTitle() {
        return aptTitle;
    }

    public void setAptTitle(String aptTitle) {
        this.aptTitle = aptTitle;
    }

    public String getAptDescription() {
        return aptDescription;
    }

    public void setAptDescription(String aptDescription) {
        this.aptDescription = aptDescription;
    }

    public String getAptLocation() {
        return aptLocation;
    }

    public void setAptLocation(String aptLocation) {
        this.aptLocation = aptLocation;
    }

    public String getAptType() {
        return aptType;
    }

    public void setAptType(String aptType) {
        this.aptType = aptType;
    }

    public LocalDateTime getAptStartTime() {
        return aptStartTime;
    }

    public void setAptStartTime(LocalDateTime aptStartTime) {
        this.aptStartTime = aptStartTime;
    }

    public LocalDateTime getAptEndTime() {
        return aptEndTime;
    }

    public void setAptEndTime(LocalDateTime aptEndTime) {
        this.aptEndTime = aptEndTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
