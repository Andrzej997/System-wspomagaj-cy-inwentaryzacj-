package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 * Created by Krzysztof Stręk on 2016-06-04.
 * @version 1.0
 * 
 * ReservationType Data Transport Object
 */
public class ReservationTypeDTO implements Serializable {

    private static final long serialVersionUID = 1808126553198964261L;

    private Long id;

    private String shortDescription;

    private String longDescription;

    private String reservationColor;

    public ReservationTypeDTO(Long id, String shortDescription, String longDescription, String reservationColor) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.reservationColor = reservationColor;
    }

    public ReservationTypeDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getReservationColor() {
        return reservationColor;
    }

    public void setReservationColor(String reservationColor) {
        this.reservationColor = reservationColor;
    }
}
