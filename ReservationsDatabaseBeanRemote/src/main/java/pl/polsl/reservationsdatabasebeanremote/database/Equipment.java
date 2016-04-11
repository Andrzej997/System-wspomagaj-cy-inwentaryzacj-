package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EQUIPMENT")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 5276213664855009030L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, targetEntity = EqupmentState.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "EQUIPMENT_STATE", insertable = true, nullable = false, updatable = true)
    private EqupmentState equipmentState;

    @ManyToOne(optional = false, targetEntity = EquipmentType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "EQUIPMENT_TYPE", insertable = true, nullable = false, updatable = true)
    private EquipmentType equipmentType;

    @Column(name = "EQUIPMENT_DATA", updatable = true, insertable = true, nullable = false)
    private String equipmentData;

    @Column(name = "EQUIPMENT_NAME", updatable = true, insertable = true, nullable = false)
    private String equipmentName;

    @ManyToOne(optional = false, targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID", insertable = true, nullable = false, updatable = true)
    private Room roomId;

    public Equipment() {

    }

    public EqupmentState getEquipmentState() {
        return this.equipmentState;
    }

    public void setEquipmentState(EqupmentState equipmentState) {
        this.equipmentState = equipmentState;
    }

    public String getEquipmentData() {
        return this.equipmentData;
    }

    public void setEquipmentData(String equipmentData) {
        this.equipmentData = equipmentData;
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

    public Room getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.equipmentState);
        hash = 17 * hash + Objects.hashCode(this.equipmentData);
        hash = 17 * hash + Objects.hashCode(this.equipmentName);
        hash = 17 * hash + Objects.hashCode(this.equipmentType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Equipment other = (Equipment) obj;
        if (!Objects.equals(this.equipmentData, other.equipmentData)) {
            return false;
        }
        if (!Objects.equals(this.equipmentName, other.equipmentName)) {
            return false;
        }
        if (!Objects.equals(this.equipmentType, other.equipmentType)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.equipmentState, other.equipmentState)) {
            return false;
        }
        return Objects.equals(this.roomId, other.roomId);
    }

    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + ", equipmentState=" + equipmentState + ", equipmentData=" + equipmentData + ", equipmentName=" + equipmentName + ", equipmentType=" + equipmentType + ", roomId=" + roomId + '}';
    }

}
