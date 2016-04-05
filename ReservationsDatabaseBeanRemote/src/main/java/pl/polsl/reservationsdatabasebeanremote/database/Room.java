package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ROOM", uniqueConstraints = @UniqueConstraint(columnNames = {"ROOM_NUMBER"}))
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @OneToMany(targetEntity = Workers.class, mappedBy = "room", cascade = CascadeType.ALL)
    private List<Workers> workers;

    @Column(name = "SCHEDULE", updatable = true, insertable = true)
    private long schedule;

    @Column(name = "ROOM_NUMBER", updatable = true, insertable = true)
    private int roomNumber;

    @ManyToOne(optional = true, targetEntity = Workers.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "KEEPER_ID", insertable = true, nullable = true, updatable = true)
    private Workers keeperId;

    @OneToMany(targetEntity = Equipment.class, mappedBy = "roomId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Equipment> equipmentCollection;

    @ManyToOne(optional = false, targetEntity = Departaments.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENT_ID", insertable = true, nullable = true, updatable = true)
    private Departaments departamentId;

    @ManyToOne(optional = true, targetEntity = RoomTypes.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_TYPE", insertable = true, nullable = true, updatable = true)
    private RoomTypes roomType;

    @OneToMany(targetEntity = RoomSchedule.class, mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomSchedule> roomScheduleCollection;

    public Room() {

    }

    public List<Workers> getWorkerses() {
        return this.workers;
    }

    public void setWorkerses(List<Workers> workerses) {
        this.workers = workerses;
    }

    public long getSchedule() {
        return this.schedule;
    }

    public void setSchedule(long schedule) {
        this.schedule = schedule;
    }

    public int getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workers getKeeperId() {
        return this.keeperId;
    }

    public void setKeeperId(Workers keeperId) {
        this.keeperId = keeperId;
    }

    public List<Equipment> getEquipmentCollection() {
        return this.equipmentCollection;
    }

    public void setEquipmentCollection(List<Equipment> equipmentCollection) {
        this.equipmentCollection = equipmentCollection;
    }

    public Departaments getDepartamentId() {
        return this.departamentId;
    }

    public void setDepartamentId(Departaments departamentId) {
        this.departamentId = departamentId;
    }

    public RoomTypes getRoomType() {
        return this.roomType;
    }

    public void setRoomType(RoomTypes roomType) {
        this.roomType = roomType;
    }

    public List<RoomSchedule> getRoomScheduleCollection() {
        return this.roomScheduleCollection;
    }

    public void setRoomScheduleCollection(List<RoomSchedule> roomScheduleCollection) {
        this.roomScheduleCollection = roomScheduleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.workers);
        hash = 97 * hash + (int) (this.schedule ^ (this.schedule >>> 32));
        hash = 97 * hash + this.roomNumber;
        hash = 97 * hash + Objects.hashCode(this.keeperId);
        hash = 97 * hash + Objects.hashCode(this.equipmentCollection);
        hash = 97 * hash + Objects.hashCode(this.departamentId);
        hash = 97 * hash + Objects.hashCode(this.roomType);
        hash = 97 * hash + Objects.hashCode(this.roomScheduleCollection);
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
        final Room other = (Room) obj;
        if (this.schedule != other.schedule) {
            return false;
        }
        if (this.roomNumber != other.roomNumber) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.workers, other.workers)) {
            return false;
        }
        if (!Objects.equals(this.keeperId, other.keeperId)) {
            return false;
        }
        if (!Objects.equals(this.equipmentCollection, other.equipmentCollection)) {
            return false;
        }
        if (!Objects.equals(this.departamentId, other.departamentId)) {
            return false;
        }
        if (!Objects.equals(this.roomType, other.roomType)) {
            return false;
        }
        return Objects.equals(this.roomScheduleCollection, other.roomScheduleCollection);
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", workers=" + workers + ", schedule=" + schedule + ", roomNumber=" + roomNumber + ", keeperId=" + keeperId + ", equipmentCollection=" + equipmentCollection + ", departamentId=" + departamentId + ", roomType=" + roomType + ", roomScheduleCollection=" + roomScheduleCollection + '}';
    }
    
}
