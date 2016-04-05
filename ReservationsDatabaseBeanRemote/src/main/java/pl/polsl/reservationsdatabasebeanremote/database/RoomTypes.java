package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROOM_TYPES")
public class RoomTypes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_TYPE", updatable = true, insertable = true, nullable = false)
    private Short roomType;

    @Column(name = "LONG_DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String longDescription;

    @OneToMany(targetEntity = Room.class, mappedBy = "roomType", cascade = CascadeType.ALL)
    private List<Room> roomCollection;

    @Column(name = "SHORT_DESCRIPTION", updatable = true, insertable = true, nullable = false)
    private String shortDescription;

    public RoomTypes() {

    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<Room> getRoomCollection() {
        return this.roomCollection;
    }

    public void setRoomCollection(List<Room> roomCollection) {
        this.roomCollection = roomCollection;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Short getRoomType() {
        return this.roomType;
    }

    public void setRoomType(Short roomType) {
        this.roomType = roomType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.roomType);
        hash = 83 * hash + Objects.hashCode(this.longDescription);
        hash = 83 * hash + Objects.hashCode(this.roomCollection);
        hash = 83 * hash + Objects.hashCode(this.shortDescription);
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
        final RoomTypes other = (RoomTypes) obj;
        if (!Objects.equals(this.longDescription, other.longDescription)) {
            return false;
        }
        if (!Objects.equals(this.shortDescription, other.shortDescription)) {
            return false;
        }
        if (!Objects.equals(this.roomType, other.roomType)) {
            return false;
        }
        return Objects.equals(this.roomCollection, other.roomCollection);
    }

    @Override
    public String toString() {
        return "RoomTypes{" + "roomType=" + roomType + ", longDescription=" + longDescription + ", roomCollection=" + roomCollection + ", shortDescription=" + shortDescription + '}';
    }
    
}
