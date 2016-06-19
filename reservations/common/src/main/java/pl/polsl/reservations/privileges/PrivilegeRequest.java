package pl.polsl.reservations.privileges;

import java.io.Serializable;

/**
 *
 * @author wojcdeb448
 */
public class PrivilegeRequest implements Serializable {

    private static final long serialVersionUID = -4000059765778035943L;

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
