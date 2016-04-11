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

@Entity
@Table(name = "WORKERS")
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

    @OneToMany(targetEntity = Room.class, mappedBy = "keeperId", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Room> roomCollection;

    @Column(name = "ADRESS", updatable = true, insertable = true, nullable = false)
    private String adress;

    @Column(name = "WORKER_NAME", updatable = true, insertable = true, nullable = false)
    private String workerName;

    @Column(name = "PESEL", unique = true, updatable = true, insertable = true, nullable = false, length = 11)
    private String pesel;

    @ManyToOne(optional = false, targetEntity = Departaments.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENT_ID", insertable = true, nullable = false, updatable = true)
    private Departaments departamentId;

    @ManyToOne(optional = true, targetEntity = Room.class)
    private Room room;

    @ManyToOne(targetEntity = Workers.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(updatable = true, nullable = true)
    private Workers chiefId;

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

    public Departaments getDepartamentId() {
        return this.departamentId;
    }

    public void setDepartamentId(Departaments departamentId) {
        this.departamentId = departamentId;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Workers getChiefId() {
        return this.chiefId;
    }

    public void setChiefId(Workers chiefID) {
        this.chiefId = chiefID;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.surname);
        hash = 73 * hash + Objects.hashCode(this.grade);
        hash = 73 * hash + Objects.hashCode(this.roomCollection);
        hash = 73 * hash + Objects.hashCode(this.adress);
        hash = 73 * hash + Objects.hashCode(this.workerName);
        hash = 73 * hash + Objects.hashCode(this.pesel);
        hash = 73 * hash + Objects.hashCode(this.departamentId);
        hash = 73 * hash + Objects.hashCode(this.room);
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
        final Workers other = (Workers) obj;
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.grade, other.grade)) {
            return false;
        }
        if (!Objects.equals(this.adress, other.adress)) {
            return false;
        }
        if (!Objects.equals(this.workerName, other.workerName)) {
            return false;
        }
        if (!Objects.equals(this.pesel, other.pesel)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.roomCollection, other.roomCollection)) {
            return false;
        }
        if (!Objects.equals(this.departamentId, other.departamentId)) {
            return false;
        }
        if (!Objects.equals(this.chiefId, other.chiefId)) {
            return false;
        }
        return Objects.equals(this.room, other.room);
    }

    @Override
    public String toString() {
        return "Workers{" + "id=" + id + ", surname=" + surname + ", grade=" + grade + ", roomCollection=" + roomCollection + ", adress=" + adress + ", workerName=" + workerName + ", pesel=" + pesel + ", chiefId=" + chiefId + ", departamentId=" + departamentId + ", room=" + room + '}';
    }

}
