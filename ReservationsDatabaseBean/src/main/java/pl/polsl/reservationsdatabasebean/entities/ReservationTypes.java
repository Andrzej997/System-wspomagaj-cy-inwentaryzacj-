package pl.polsl.reservationsdatabasebean.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RESERVATION_TYPES")
public class ReservationTypes implements Serializable {

    private static final long serialVersionUID = 4761701704443454076L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TYPE_ID", updatable = true, insertable = true, nullable = false)
    private Long typeId;

    @OneToMany(targetEntity = Reservations.class, mappedBy = "reservationType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Reservations> reservationsCollection;

    @Column(name = "TYPE_SHORT_DESCRIPTION", updatable = true, insertable = true, nullable = false)
    private String typeShortDescription;

    @Column(name = "TYPE_LONG_DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String typeLongDescription;

    public ReservationTypes() {

    }

    public List<Reservations> getReservationsCollection() {
        return this.reservationsCollection;
    }

    public void setReservationsCollection(List<Reservations> reservationsCollection) {
        this.reservationsCollection = reservationsCollection;
    }

    public Long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.typeId);
        hash = 89 * hash + Objects.hashCode(this.reservationsCollection);
        hash = 89 * hash + Objects.hashCode(this.typeShortDescription);
        hash = 89 * hash + Objects.hashCode(this.typeLongDescription);
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
        final ReservationTypes other = (ReservationTypes) obj;
        if (!Objects.equals(this.typeShortDescription, other.typeShortDescription)) {
            return false;
        }
        if (!Objects.equals(this.typeLongDescription, other.typeLongDescription)) {
            return false;
        }
        if (!Objects.equals(this.typeId, other.typeId)) {
            return false;
        }
        return Objects.equals(this.reservationsCollection, other.reservationsCollection);
    }

    @Override
    public String toString() {
        return "ReservationTypes{" + "typeId=" + typeId + ", reservationsCollection=" + reservationsCollection + ", typeShortDescription=" + typeShortDescription + ", typeLongDescription=" + typeLongDescription + '}';
    }

}
