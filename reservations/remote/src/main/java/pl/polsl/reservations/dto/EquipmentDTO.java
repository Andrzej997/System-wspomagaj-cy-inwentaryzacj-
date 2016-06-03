package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class EquipmentDTO implements Serializable {

    private static final long serialVersionUID = 4479013165387722403L;

    private Long id;

    private String name;

    private Integer quantity;

    private String type;

    private String state;

    public EquipmentDTO(Long id, String name, Integer quantity, String type, String state) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.state = state;
    }

    public EquipmentDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
