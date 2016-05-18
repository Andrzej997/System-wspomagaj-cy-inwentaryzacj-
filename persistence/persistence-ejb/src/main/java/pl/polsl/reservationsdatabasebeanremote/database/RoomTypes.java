package pl.polsl.reservationsdatabasebeanremote.database;

import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ROOM_TYPES")
@EntityListeners(LoggerImpl.class)
public class RoomTypes implements Serializable {

    private static final long serialVersionUID = 2452760095506900391L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_TYPE", updatable = true, insertable = true, nullable = false)
    private Short roomType;

    @Column(name = "LONG_DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String longDescription;

    @OneToMany(targetEntity = Room.class, mappedBy = "roomType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

}
