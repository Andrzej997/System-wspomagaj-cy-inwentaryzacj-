package pl.polsl.reservationsdatabasebeanremote.database;

import org.hibernate.annotations.Proxy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@NamedQueries({@NamedQuery(name = "getAllSchedulesByYearAndSemester", query = "select rs from RoomSchedule rs where rs.semester = :semester and rs._year = :year "),
                @NamedQuery(name = "getAllSchedulesAtSession", query = "select rs from RoomSchedule rs where rs.semester = :semester and rs._year = :year and rs.examinationSession = true "),
                @NamedQuery(name = "getCurrentDateSchedule", query = "select rs from RoomSchedule  rs where rs._year = :year and rs.week = :week"),
                @NamedQuery(name = "getCurrentScheduleForRoom", query = "select rs from RoomSchedule rs where rs = :RoomSchedule and rs.room.roomNumber = :roomNumber")

})

@Entity
@Table(name = "ROOM_SCHEDULE")
public class RoomSchedule implements Serializable {

    private static final long serialVersionUID = -4139906567566932774L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @Column(name = "WEEK", updatable = true, insertable = true, nullable = true)
    private Integer week;

    @OneToMany(targetEntity = Reservations.class, mappedBy = "roomNumber", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
        return (Date) this._year.clone();
    }

    public void set_year(Date sYear) {
        this._year = (Date) sYear.clone();
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

}
