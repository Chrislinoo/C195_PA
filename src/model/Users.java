package model;

public class Users {
    private int userId;
    private String userName;
    private String userPassword;

    /**
     * Constructor for the Users class.
     * @param userId
     * @param userName
     * @param userPassword
     */
    public Users(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Gets the user ID. Used for the login portion of the task.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the user name.
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets user password.
     * @return userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets user ID.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets username.
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets user password.
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
