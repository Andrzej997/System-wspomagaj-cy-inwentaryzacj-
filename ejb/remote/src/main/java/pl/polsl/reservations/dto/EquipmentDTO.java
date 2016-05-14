package pl.polsl.reservations.dto;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class EquipmentDTO implements Serializable {

    private long id;

    private String name;

    private int quantity;

    private String type;

    private String state;

    public EquipmentDTO(long id, String name, int quantity, String type, String state) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.state = state;
    }

    public EquipmentDTO() {}

    public EquipmentDTO(Equipment e) {
        this.id = e.getId();
        this.name = e.getEquipmentName();
        this.quantity = e.getQuantity();
        this.type = e.getEquipmentState().getStateDefinition();
        this.state = e.getEquipmentType().getShortDescription();
    }
}
