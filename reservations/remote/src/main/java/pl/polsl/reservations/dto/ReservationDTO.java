package pl.polsl.reservations.dto;

import pl.polsl.reservations.entities.Reservations;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class ReservationDTO implements Serializable {

    private static final long serialVersionUID = 6796108171113124154L;

    private long id;

    private int roomNumber;

    private int startTime;

    private int endTime;

    private String type;

    private long userId;

    public ReservationDTO() {
    }

    public ReservationDTO(long id, int roomNumber, int startTime, int endTime, String type, long userId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.userId = userId;
    }

    public ReservationDTO(Reservations entity) {
        this.id = entity.getId();
        this.roomNumber = entity.getRoomSchedule().getRoom().getRoomNumber();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.type = entity.getReservationType().getTypeShortDescription();
        this.userId = entity.getUserId().getUserId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
