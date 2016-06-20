package pl.polsl.reservations.privileges;

import java.io.Serializable;

/**
 *
 * @author wojcdeb448
 * @version 1.0
 * 
 * Class represents privilege request, needed to obtain higher privilege level
 */
public class PrivilegeRequest implements Serializable {

    private static final long serialVersionUID = -4000059765778035943L;

    /**
     * Requested level
     */
    private Long privilegeLevel;
    
    /**
     * Request reason
     */
    private String reason;
    
    /**
     * Requesting user id
     */
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
