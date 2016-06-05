package pl.polsl.reservations.privileges;

/**
 *
 * @author wojcdeb448
 */
public class PrivilegeRequest {

    private Long privilegeLevel;
    private String reason;
    private Long userID;

    public PrivilegeRequest(Long privilegeLevel, Long userID, String reason) {
        this.privilegeLevel = privilegeLevel;
        this.reason = reason;
        this.userID = userID;
    }

    public void setPrivilegeLevel(Long privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public Long getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getUserID() {
        return userID;
    }
}
