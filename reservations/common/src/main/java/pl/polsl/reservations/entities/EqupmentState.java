package pl.polsl.reservations.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

@Entity
@Table(name = "EQUPMENT_STATE")
@EntityListeners(LoggerImpl.class)
public class EqupmentState implements Serializable {

    private static final long serialVersionUID = 2504464193763388953L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STATE_ID", updatable = true, insertable = true, nullable = false)
    private Short id;

    @OneToMany(targetEntity = Equipment.class, mappedBy = "equipmentState", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipment> equipmentCollection;

    @Column(name = "STATE_DEFINITION", updatable = true, insertable = true, nullable = false)
    private String stateDefinition;

    public EqupmentState() {

    }

    public Short getId() {
        return this.id;
    }

    public void setId(Short id) {
        this.id = id;
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

}
