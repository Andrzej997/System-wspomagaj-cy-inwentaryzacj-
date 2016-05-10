package pl.polsl.reservationsdatabasebeanremote.database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "getDepartamentByName", query = "select d from Departaments d where d.depratamentName like :name"),
        @NamedQuery(name = "findDepartametsHavingWorkers", query = "select d from Departaments d where d.workersCollection is not empty"),
        @NamedQuery(name = "getDepartamentByChiefId", query = "select d from Departaments d where d.chiefId.id = :id")
})

@Entity
@Table(name = "DEPARTAMENTS")
public class Departaments implements Serializable {

    private static final long serialVersionUID = 61654414912214227L;

    @Id
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DEPRATAMENT_NAME", updatable = true, insertable = true, nullable = false, unique = true)
    private String depratamentName;

    @OneToMany(targetEntity = Room.class, mappedBy = "departamentId", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Room> roomCollection;

    @ManyToOne(optional = true, targetEntity = Institutes.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTITUTE_ID", insertable = true, nullable = true, unique = false, updatable = true)
    private Institutes instituteId;

    @OneToMany(targetEntity = Workers.class, mappedBy = "departamentId", cascade = CascadeType.ALL)
    private List<Workers> workersCollection;

    @OneToOne(targetEntity = Workers.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Workers chiefId;

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

    public List<Workers> getWorkersCollection() {
        return this.workersCollection;
    }

    public void setWorkersCollection(List<Workers> workersCollection) {
        this.workersCollection = workersCollection;
    }

    public Workers getChiefId() {
        return this.chiefId;
    }

    public void setChiefId(Workers chiefId) {
        this.chiefId = chiefId;
    }

}
