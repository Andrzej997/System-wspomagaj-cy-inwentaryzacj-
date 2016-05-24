package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class EquipmentTypeDTO implements Serializable {

    private static final long serialVersionUID = -6167597351834042225L;

    private long id;

    private String description;

    public EquipmentTypeDTO() {}

    public EquipmentTypeDTO(long id, String description) {
        this.id = id;
        this.description = description;
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
