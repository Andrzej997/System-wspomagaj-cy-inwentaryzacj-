package pl.polsl.reservationsdatabasebeanremote.database;

import org.hibernate.annotations.Proxy;

import java.io.Serializable;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "getAllReservationsByRoomSchedule", query = "select r from Reservations  r where r.roomNumber = :roomSchedule"),
        @NamedQuery(name = "getAllWeekReservations", query = "select r from Reservations  r where r.roomNumber.week = :week and r.roomNumber._year = :year"),
        @NamedQuery(name = "getAllReservationsByType", query = "select r from Reservations r where r.reservationType.id = :typeId"),
        @NamedQuery(name = "getAllReservationsByUser", query = "select r from Reservations r where r.userId.id = :userId")
})

@Proxy(lazy = false)
@Entity
@Table(name = "RESERVATIONS")
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1110572173097587524L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, targetEntity = RoomSchedule.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_NUMBER", insertable = true, nullable = true, updatable = true)
    private RoomSchedule roomNumber;

    @ManyToOne(optional = false, targetEntity = ReservationTypes.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "RESERVATION_TYPE", insertable = true, nullable = true, updatable = true)
    private ReservationTypes reservationType;

    @Column(name = "START_TIME", updatable = true, insertable = true, nullable = false)
    private int startTime;

    @Column(name = "END_TIME", updatable = true, insertable = true, nullable = false)
    private int endTime;

    @ManyToOne(optional = true, targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", insertable = true, nullable = true, updatable = true)
    private Users userId;

    public Reservations() {

    }

    public RoomSchedule getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(RoomSchedule roomNumber) {
        this.roomNumber = roomNumber;
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

    public Users getUserId() {
        return this.userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

}
