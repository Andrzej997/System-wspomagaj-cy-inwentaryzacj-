package pl.polsl.reservations.dto;

import java.io.Serializable;
import pl.polsl.reservationsdatabasebeanremote.database.PriviligeLevels;

/**
 *
 * @author wojcdeb448
 */
public class PrivilegeLevelDTO implements Serializable{
    
    private Long privilegeLevel;
    
    private String description;

    public PrivilegeLevelDTO(Long privilegeLevel, String description) {
        this.privilegeLevel = privilegeLevel;
        this.description = description;
    }
    
    public PrivilegeLevelDTO(PriviligeLevels prLvl) {
        this.privilegeLevel = prLvl.getPriviligeLevel();
        this.description = prLvl.getDescription();
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
