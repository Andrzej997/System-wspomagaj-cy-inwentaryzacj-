package pl.polsl.reservations.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

@Entity
@Table(name = "RESERVATION_TYPES")
@EntityListeners(LoggerImpl.class)
public class ReservationTypes implements Serializable {

    private static final long serialVersionUID = 4761701704443454076L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TYPE_ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @OneToMany(targetEntity = Reservations.class, mappedBy = "reservationType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Reservations> reservationsCollection;

    @Column(name = "TYPE_SHORT_DESCRIPTION", updatable = true, insertable = true, nullable = false)
    private String typeShortDescription;

    @Column(name = "TYPE_LONG_DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String typeLongDescription;

    @Column(name = "RESERVATION_COLOR", nullable = false)
    private String reservationColor;

    public ReservationTypes() {

    }

    public List<Reservations> getReservationsCollection() {
        return this.reservationsCollection;
    }

    public void setReservationsCollection(List<Reservations> reservationsCollection) {
        this.reservationsCollection = reservationsCollection;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeShortDescription() {
        return this.typeShortDescription;
    }

    public void setTypeShortDescription(String typeShortDescription) {
        this.typeShortDescription = typeShortDescription;
    }

    public String getTypeLongDescription() {
        return this.typeLongDescription;
    }

    public void setTypeLongDescription(String typeLongDescription) {
        this.typeLongDescription = typeLongDescription;
    }

    public String getReservationColor() {
        return reservationColor;
    }

    public void setReservationColor(String reservationColor) {
        this.reservationColor = reservationColor;
    }

}
