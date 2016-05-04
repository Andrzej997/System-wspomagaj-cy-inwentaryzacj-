package pl.polsl.reservationsdatabasebeanremote.database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author matis
 */
@Entity
@Table(name = "EQUPMENT_TYPE")
public class EquipmentType implements Serializable {

    private static final long serialVersionUID = 5325234234L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TYPE_ID", updatable = true, insertable = true, nullable = false)
    private Short typeId;

    @OneToMany(targetEntity = Equipment.class, mappedBy = "equipmentType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Equipment> equipmentCollection;

    @Column(name = "SHORT_DEFINITION", updatable = true, insertable = true, nullable = false)
    private String shortDescription;

    @Column(name = "LONG_DEFINITION", updatable = true, insertable = true, nullable = true)
    private String longDescription;

    public Short getId() {
        return typeId;
    }

    public void setId(Short typeId) {
        this.typeId = typeId;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.typeId);
        hash = 97 * hash + Objects.hashCode(this.equipmentCollection);
        hash = 97 * hash + Objects.hashCode(this.shortDescription);
        hash = 97 * hash + Objects.hashCode(this.longDescription);
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
        final EquipmentType other = (EquipmentType) obj;
        if (!Objects.equals(this.shortDescription, other.shortDescription)) {
            return false;
        }
        if (!Objects.equals(this.longDescription, other.longDescription)) {
            return false;
        }
        if (!Objects.equals(this.typeId, other.typeId)) {
            return false;
        }
        return Objects.equals(this.equipmentCollection, other.equipmentCollection);
    }

    @Override
    public String toString() {
        return "EquipmentType{" + "typeId=" + typeId + ", equipmentCollection=" + equipmentCollection + ", shortDescription=" + shortDescription + ", longDescription=" + longDescription + '}';
    }

}
