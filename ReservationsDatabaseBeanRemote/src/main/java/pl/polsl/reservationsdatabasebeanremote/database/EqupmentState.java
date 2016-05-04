package pl.polsl.reservationsdatabasebeanremote.database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "EQUPMENT_STATE")
public class EqupmentState implements Serializable {

    private static final long serialVersionUID = 2504464193763388953L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STATE_ID", updatable = true, insertable = true, nullable = false)
    private Short stateId;

    @OneToMany(targetEntity = Equipment.class, mappedBy = "equipmentState", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Equipment> equipmentCollection;

    @Column(name = "STATE_DEFINITION", updatable = true, insertable = true, nullable = false)
    private String stateDefinition;

    public EqupmentState() {

    }

    public Short getStateId() {
        return this.stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public List<Equipment> getEquipmentCollection() {
        return this.equipmentCollection;
    }

    public void setEquipmentCollection(List<Equipment> equipmentCollection) {
        this.equipmentCollection = equipmentCollection;
    }

    public String getStateDefinition() {
        return this.stateDefinition;
    }

    public void setStateDefinition(String stateDefinition) {
        this.stateDefinition = stateDefinition;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.stateId);
        hash = 37 * hash + Objects.hashCode(this.equipmentCollection);
        hash = 37 * hash + Objects.hashCode(this.stateDefinition);
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
        final EqupmentState other = (EqupmentState) obj;
        if (!Objects.equals(this.stateDefinition, other.stateDefinition)) {
            return false;
        }
        if (!Objects.equals(this.stateId, other.stateId)) {
            return false;
        }
        return Objects.equals(this.equipmentCollection, other.equipmentCollection);
    }

    @Override
    public String toString() {
        return "EqupmentState{" + "stateId=" + stateId + ", equipmentCollection=" + equipmentCollection + ", stateDefinition=" + stateDefinition + '}';
    }

}
