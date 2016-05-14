package pl.polsl.reservations.dto;

import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class EquipmentTypeDTO implements Serializable {

    private long id;

    private String description;

    public EquipmentTypeDTO() {}

    public EquipmentTypeDTO(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public EquipmentTypeDTO(EquipmentType et) {
        this.id = et.getId();
        this.description = et.getShortDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
