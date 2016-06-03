package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-05-14.
 */
public class RoomDTO implements Serializable {

    private static final long serialVersionUID = 907737187968943198L;

    private Long id;

    private Integer number;

    private String department;

    private String keeper;

    private String type;

    private Integer numberOfSeats;

    public RoomDTO(Long id, Integer number, String department, String keeper, String type, Integer numberOfSeats) {
        this.id = id;
        this.number = number;
        this.department = department;
        this.keeper = keeper;
        this.type = type;
        this.numberOfSeats = numberOfSeats;
    }

    public RoomDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
