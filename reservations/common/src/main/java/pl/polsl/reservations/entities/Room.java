package pl.polsl.reservations.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

@NamedQueries({
    @NamedQuery(name = "getRoomByNumber", query = "select r from Room  r where r.roomNumber = :roomNumber"),
    @NamedQuery(name = "getRoomWithNumOfSeatsHigherOrEqualThan", query="select r from Room r where r.numberOfSeats >= :numberOfSeats"),
    @NamedQuery(name = "getRoomByKeeper", query = "select r from Room  r where r.keeper = :keeper")
})

@Entity
@Table(name = "ROOM", uniqueConstraints = @UniqueConstraint(columnNames = {"ROOM_NUMBER"}))
@EntityListeners(LoggerImpl.class)
public class Room implements Serializable {

    private static final long serialVersionUID = -4970767790381180082L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @OneToMany(targetEntity = Workers.class, mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Workers> workers;

    @Column(name = "ROOM_NUMBER", updatable = true, insertable = true)
    private int roomNumber;

    @ManyToOne(optional = true, targetEntity = Workers.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "KEEPER_ID", insertable = true, nullable = true, updatable = true)
    private Workers keeper;

    @OneToMany(targetEntity = Equipment.class, mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Equipment> equipmentCollection;

    @ManyToOne(optional = false, targetEntity = Departaments.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTAMENT_ID", insertable = true, nullable = true, updatable = true)
    private Departaments departament;

    @ManyToOne(optional = true, targetEntity = RoomTypes.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_TYPE", insertable = true, nullable = true, updatable = true)
    private RoomTypes roomType;

    @OneToMany(targetEntity = RoomSchedule.class, mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomSchedule> roomScheduleCollection;

    @Column(name = "NUMBER_OF_SEATS", updatable = true, insertable = true, nullable = true)
    private Integer numberOfSeats;

    public Room() {

    }

    public List<Workers> getWorkerses() {
        return this.workers;
    }

    public void setWorkerses(List<Workers> workerses) {
        this.workers = workerses;
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

    public Workers getKeeper() {
        return this.keeper;
    }

    public void setKeeper(Workers keeper) {
        this.keeper = keeper;
    }

    public List<Equipment> getEquipmentCollection() {
        return this.equipmentCollection;
    }

    public void setEquipmentCollection(List<Equipment> equipmentCollection) {
        this.equipmentCollection = equipmentCollection;
    }

    public Departaments getDepartament() {
        return this.departament;
    }

    public void setDepartament(Departaments departament) {
        this.departament = departament;
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

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

}
