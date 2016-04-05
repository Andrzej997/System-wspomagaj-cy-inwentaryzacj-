package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTAMENTS")
public class Departaments implements Serializable {

    @Id
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DEPRATAMENT_NAME", updatable = true, insertable = true, nullable = false, unique = true)
    private String depratamentName;

    @OneToMany(targetEntity = Room.class, mappedBy = "departamentId", cascade = CascadeType.PERSIST)
    private List<Room> roomCollection;

    @ManyToOne(optional = false, targetEntity = Institutes.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTITUTE_ID", insertable = true, nullable = false, unique = false, updatable = true)
    private Institutes instituteId;

    @OneToMany(targetEntity = Chiefs.class, mappedBy = "departamentId", cascade = CascadeType.ALL)
    private List<Chiefs> chiefsCollection;

    @OneToMany(targetEntity = Workers.class, mappedBy = "departamentId", cascade = CascadeType.ALL)
    private List<Workers> workersCollection;

    public Departaments() {

    }

    public String getDepratamentName() {
        return this.depratamentName;
    }

    public void setDepratamentName(String depratamentName) {
        this.depratamentName = depratamentName;
    }

    public List<Room> getRoomCollection() {
        return this.roomCollection;
    }

    public void setRoomCollection(List<Room> roomCollection) {
        this.roomCollection = roomCollection;
    }

    public Institutes getInstituteId() {
        return this.instituteId;
    }

    public void setInstituteId(Institutes instituteId) {
        this.instituteId = instituteId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Chiefs> getChiefsCollection() {
        return this.chiefsCollection;
    }

    public void setChiefsCollection(List<Chiefs> chiefsCollection) {
        this.chiefsCollection = chiefsCollection;
    }

    public List<Workers> getWorkersCollection() {
        return this.workersCollection;
    }

    public void setWorkersCollection(List<Workers> workersCollection) {
        this.workersCollection = workersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.depratamentName);
        hash = 89 * hash + Objects.hashCode(this.roomCollection);
        hash = 89 * hash + Objects.hashCode(this.instituteId);
        hash = 89 * hash + Objects.hashCode(this.chiefsCollection);
        hash = 89 * hash + Objects.hashCode(this.workersCollection);
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
        final Departaments other = (Departaments) obj;
        if (!Objects.equals(this.depratamentName, other.depratamentName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.roomCollection, other.roomCollection)) {
            return false;
        }
        if (!Objects.equals(this.instituteId, other.instituteId)) {
            return false;
        }
        if (!Objects.equals(this.chiefsCollection, other.chiefsCollection)) {
            return false;
        }
        return Objects.equals(this.workersCollection, other.workersCollection);
    }

    @Override
    public String toString() {
        return "Departaments{" + "id=" + id + ", depratamentName=" + depratamentName + ", roomCollection=" + roomCollection + ", instituteId=" + instituteId + ", chiefsCollection=" + chiefsCollection + ", workersCollection=" + workersCollection + '}';
    }

}
