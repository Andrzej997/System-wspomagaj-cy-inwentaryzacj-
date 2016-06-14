package pl.polsl.reservations.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

@NamedQueries({@NamedQuery(name = "getWorkersByName", query = "select w from Workers w where w.workerName = :workerName"),
                @NamedQuery(name = "getWorkersBySurname", query = "select w from Workers w where w.surname = :surname"),
                @NamedQuery(name = "getWorkersByGrade", query = "select w from Workers  w where w.grade = :grade"),
                @NamedQuery(name = "getWorkersByAdress", query = "select w from Workers  w where w.adress = :adress"),
                @NamedQuery(name = "getWorkerByPesel", query = "select w from Workers  w where w.pesel = :pesel"),
                @NamedQuery(name = "getRoomsCollectionByKeeperId", query = "select r from Room r where r.keeper.id = :id"),
                @NamedQuery(name = "getDepartamentByWorkerId", query = "select w.departament from Workers w where w.id = :id"),
                @NamedQuery(name = "getAllChiefs", query = "select w from Workers w where w.chief is null"),
                @NamedQuery(name = "getWorkersWhichHaveChief", query = "select w from Workers w where w.chief is not null")
            })

@Entity
@Table(name = "WORKERS")
@EntityListeners(LoggerImpl.class)
public class Workers implements Serializable {

    private static final long serialVersionUID = 2838010209475855091L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @Column(name = "SURNAME", updatable = true, insertable = true, nullable = false)
    private String surname;

    @Column(name = "GRADE", updatable = true, insertable = true, nullable = false, length = 50)
    private String grade;

    @OneToMany(targetEntity = Room.class, mappedBy = "keeper",
            cascade = {CascadeType.ALL}
            , fetch = FetchType.LAZY)
    private List<Room> roomCollection;

    @Column(name = "ADRESS", updatable = true, insertable = true, nullable = false)
    private String adress;

    @Column(name = "WORKER_NAME", updatable = true, insertable = true, nullable = false)
    private String workerName;

    @Column(name = "PESEL", unique = true, updatable = true, insertable = true, nullable = false, length = 11)
    private String pesel;

    @ManyToOne(optional = false, targetEntity = Departaments.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTAMENT_ID", insertable = true, nullable = false, updatable = true)
    private Departaments departament;

    @ManyToOne(optional = true, targetEntity = Room.class, fetch = FetchType.EAGER)
    private Room room;

    @ManyToOne(targetEntity = Workers.class,
            cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(updatable = true, nullable = true)
    private Workers chief;

    public Workers() {

    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<Room> getRoomCollection() {
        return this.roomCollection;
    }

    public void setRoomCollection(List<Room> roomCollection) {
        this.roomCollection = roomCollection;
    }

    public String getAdress() {
        return this.adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkerName() {
        return this.workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getPesel() {
        return this.pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Departaments getDepartament() {
        return this.departament;
    }

    public void setDepartament(Departaments departament) {
        this.departament = departament;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Workers getChief() {
        return this.chief;
    }

    public void setChief(Workers chiefID) {
        this.chief = chiefID;
    }

}
