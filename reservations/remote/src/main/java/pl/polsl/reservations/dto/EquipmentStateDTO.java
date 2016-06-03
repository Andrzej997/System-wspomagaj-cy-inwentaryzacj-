package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class EquipmentStateDTO implements Serializable {

    private static final long serialVersionUID = 2375166869908733856L;

    private Long id;

    private String description;

    public EquipmentStateDTO() {}

    public EquipmentStateDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
