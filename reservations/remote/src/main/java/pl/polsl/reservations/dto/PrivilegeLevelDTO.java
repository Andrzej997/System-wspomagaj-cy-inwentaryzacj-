package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 *
 * @author wojcdeb448
 */
public class PrivilegeLevelDTO implements Serializable{

    private static final long serialVersionUID = 7805129553199964201L;

    private Long privilegeLevel;
    
    private String description;

    public PrivilegeLevelDTO(Long privilegeLevel, String description) {
        this.privilegeLevel = privilegeLevel;
        this.description = description;
    }
    
    public PrivilegeLevelDTO(){
        
    }
    
    public Long getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(Long privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String privilegeDescription) {
        this.description = privilegeDescription;
    }
    
}
