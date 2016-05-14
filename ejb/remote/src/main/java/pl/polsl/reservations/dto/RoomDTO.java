package pl.polsl.reservations.dto;

import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class RoomDTO implements Serializable {
    private long id;

    private int number;

    private String department;

    private String keeper;

    private String type;

    public RoomDTO(long id, int number, String department, String keeper, String type) {
        this.id = id;
        this.number = number;
        this.department = department;
        this.keeper = keeper;
        this.type = type;
    }

    public RoomDTO(Room r) {
        this.id = r.getId();
        this.number = r.getRoomNumber();
        this.department = r.getDepartamentId().getDepratamentName();
        Workers w = r.getKeeperId();
        this.keeper = w.getWorkerName() + " " + w.getSurname();
        this.type = r.getRoomType().getShortDescription();
    }

    public RoomDTO() {

    }
}
