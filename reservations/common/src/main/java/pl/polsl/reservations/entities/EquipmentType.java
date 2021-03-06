package pl.polsl.reservations.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
 */
@Entity
@Table(name = "EQUPMENT_TYPE")
@EntityListeners(LoggerImpl.class)
public class EquipmentType implements Serializable {

    private static final long serialVersionUID = 5325234234L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TYPE_ID", updatable = true, insertable = true, nullable = false)
    private Short id;

    @OneToMany(targetEntity = Equipment.class, mappedBy = "equipmentType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipment> equipmentCollection;

    @Column(name = "SHORT_DEFINITION", updatable = true, insertable = true, nullable = false)
    private String shortDescription;

    @Column(name = "LONG_DEFINITION", updatable = true, insertable = true, nullable = true)
    private String longDescription;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public List<Equipment> getEquipmentCollection() {
        return equipmentCollection;
    }

    public void setEquipmentCollection(List<Equipment> equipmentCollection) {
        this.equipmentCollection = equipmentCollection;
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

}
