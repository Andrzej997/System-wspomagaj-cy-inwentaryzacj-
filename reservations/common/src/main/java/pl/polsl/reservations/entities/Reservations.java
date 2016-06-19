package pl.polsl.reservations.entities;

import java.io.Serializable;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

@NamedQueries({
    @NamedQuery(name = "getAllReservationsByRoomSchedule", query = "select r from Reservations  r where r.roomSchedule = :roomSchedule"),
    @NamedQuery(name = "getAllWeekReservations", query = "select r from Reservations  r where r.roomSchedule.week = :week and r.roomSchedule.year = :year"),
    @NamedQuery(name = "getAllReservationsByType", query = "select r from Reservations r where r.reservationType.id = :id"),
    @NamedQuery(name = "getAllReservationsByUser", query = "select r from Reservations r where r.user.id = :id")
})

@Entity
@Table(name = "RESERVATIONS")
@EntityListeners(LoggerImpl.class)
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1110572173097587524L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, targetEntity = RoomSchedule.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_NUMBER", insertable = true, nullable = true, updatable = true)
    private RoomSchedule roomSchedule;

    @ManyToOne(optional = false, targetEntity = ReservationTypes.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "RESERVATION_TYPE", insertable = true, nullable = true, updatable = true)
    private ReservationTypes reservationType;

    @Column(name = "START_TIME", updatable = true, insertable = true, nullable = false)
    private int startTime;

    @Column(name = "END_TIME", updatable = true, insertable = true, nullable = false)
    private int endTime;

    @ManyToOne(optional = true, targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", insertable = true, nullable = true, updatable = true)
    private Users user;

    public Reservations() {

    }

    public RoomSchedule getRoomSchedule() {
        return this.roomSchedule;
    }

    public void setRoomSchedule(RoomSchedule roomNumber) {
        this.roomSchedule = roomNumber;
    }

    public ReservationTypes getReservationType() {
        return this.reservationType;
    }

    public void setReservationType(ReservationTypes reservationType) {
        this.reservationType = reservationType;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
