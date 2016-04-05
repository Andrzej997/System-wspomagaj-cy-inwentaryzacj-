package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ROOM_SCHEDULE")
public class RoomSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @Column(name = "WEEK", updatable = true, insertable = true, nullable = true)
    private Integer week;

    @OneToMany(targetEntity = Reservations.class, mappedBy = "roomNumber", cascade = CascadeType.ALL)
    private List<Reservations> reservationsCollection;

    @Column(name = "SEMESTER", updatable = true, insertable = true, nullable = false)
    private Boolean semester;

    @Column(name = "S_YEAR", updatable = true, insertable = true, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date _year;

    @ManyToOne(optional = true, targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM", insertable = true, nullable = true, updatable = true)
    private Room room;

    @Column(name = "EXAMINATION_SESSION", updatable = true, insertable = true, nullable = false)
    private Boolean examinationSession;

    public RoomSchedule() {

    }

    public Integer getWeek() {
        return this.week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public List<Reservations> getReservationsCollection() {
        return this.reservationsCollection;
    }

    public void setReservationsCollection(List<Reservations> reservationsCollection) {
        this.reservationsCollection = reservationsCollection;
    }

    public Boolean isSemester() {
        return this.semester;
    }

    public void setSemester(Boolean semester) {
        this.semester = semester;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date get_year() {
        return this._year;
    }

    public void set_year(Date sYear) {
        this._year = sYear;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean isExaminationSession() {
        return this.examinationSession;
    }

    public void setExaminationSession(Boolean examinationSession) {
        this.examinationSession = examinationSession;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.week);
        hash = 83 * hash + Objects.hashCode(this.reservationsCollection);
        hash = 83 * hash + Objects.hashCode(this.semester);
        hash = 83 * hash + Objects.hashCode(this._year);
        hash = 83 * hash + Objects.hashCode(this.room);
        hash = 83 * hash + Objects.hashCode(this.examinationSession);
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
        final RoomSchedule other = (RoomSchedule) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.week, other.week)) {
            return false;
        }
        if (!Objects.equals(this.reservationsCollection, other.reservationsCollection)) {
            return false;
        }
        if (!Objects.equals(this.semester, other.semester)) {
            return false;
        }
        if (!Objects.equals(this._year, other._year)) {
            return false;
        }
        if (!Objects.equals(this.room, other.room)) {
            return false;
        }
        return Objects.equals(this.examinationSession, other.examinationSession);
    }

    @Override
    public String toString() {
        return "RoomSchedule{" + "id=" + id + ", week=" + week + ", reservationsCollection=" + reservationsCollection + ", semester=" + semester + ", _year=" + _year + ", room=" + room + ", examinationSession=" + examinationSession + '}';
    }
    
}
