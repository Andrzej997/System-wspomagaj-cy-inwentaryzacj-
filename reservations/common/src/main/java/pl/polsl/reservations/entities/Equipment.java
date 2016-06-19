package pl.polsl.reservations.entities;

import java.io.Serializable;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

@NamedQueries({
    @NamedQuery(name = "getEquipmentByRoomNumber", query = "select e from Equipment e where e.room.roomNumber = :roomNumber")
})

@Entity
@Table(name = "EQUIPMENT")
@EntityListeners(LoggerImpl.class)
public class Equipment implements Serializable {

    private static final long serialVersionUID = 5276213664855009030L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, targetEntity = EqupmentState.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "EQUIPMENT_STATE", insertable = true, nullable = false, updatable = true)
    private EqupmentState equipmentState;

    @ManyToOne(optional = false, targetEntity = EquipmentType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "EQUIPMENT_TYPE", insertable = true, nullable = false, updatable = true)
    private EquipmentType equipmentType;

    @Column(name = "QUANTITY", updatable = true, insertable = true, nullable = false)
    private Integer quantity;

    @Column(name = "EQUIPMENT_NAME", updatable = true, insertable = true, nullable = false)
    private String equipmentName;

    @ManyToOne(optional = false, targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_ID", insertable = true, nullable = false, updatable = true)
    private Room room;

    public Equipment() {

    }

    public EqupmentState getEquipmentState() {
        return this.equipmentState;
    }

    public void setEquipmentState(EqupmentState equipmentState) {
        this.equipmentState = equipmentState;
    }

    public String getEquipmentName() {
        return this.equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquipmentType getEquipmentType() {
        return this.equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
