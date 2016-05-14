package pl.polsl.reservations.dto;

import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class EquipmentStateDTO implements Serializable {

    private long id;

    private String description;

    public EquipmentStateDTO() {}

    public EquipmentStateDTO(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public EquipmentStateDTO(EqupmentState es) {
        this.id = es.getStateId();
        this.description = es.getStateDefinition();
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