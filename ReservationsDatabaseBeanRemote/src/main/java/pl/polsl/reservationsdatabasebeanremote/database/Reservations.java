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
@Table(name = "RESERVATIONS")
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1110572173097587524L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, targetEntity = RoomSchedule.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_NUMBER", insertable = true, nullable = true, updatable = true)
    private RoomSchedule roomNumber;

    @ManyToOne(optional = false, targetEntity = ReservationTypes.class, fetch = FetchType.LAZY)
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.roomNumber);
        hash = 79 * hash + Objects.hashCode(this.reservationType);
        hash = 79 * hash + this.startTime;
        hash = 79 * hash + this.endTime;
        hash = 79 * hash + Objects.hashCode(this.userId);
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
        final Reservations other = (Reservations) obj;
        if (this.startTime != other.startTime) {
            return false;
        }
        if (this.endTime != other.endTime) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.roomNumber, other.roomNumber)) {
            return false;
        }
        if (!Objects.equals(this.reservationType, other.reservationType)) {
            return false;
        }
        return Objects.equals(this.userId, other.userId);
    }

    @Override
    public String toString() {
        return "Reservations{" + "id=" + id + ", roomNumber=" + roomNumber + ", reservationType=" + reservationType + ", startTime=" + startTime + ", endTime=" + endTime + ", userId=" + userId + '}';
    }
    
}
