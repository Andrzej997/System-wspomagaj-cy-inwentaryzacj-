package pl.polsl.reservationsdatabasebeanremote.database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "INSTITUTES")
public class Institutes implements Serializable {

    private static final long serialVersionUID = -7904312398532927244L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @OneToMany(targetEntity = Departaments.class, mappedBy = "instituteId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Departaments> departamentsCollection;

    @Column(name = "INSTITUTE_NAME", updatable = true, insertable = true, nullable = false)
    private String instituteName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Workers.class)
    @PrimaryKeyJoinColumn
    private Workers chiefId;

    public Institutes() {

    }

    public List<Departaments> getDepartamentsCollection() {
        return this.departamentsCollection;
    }

    public void setDepartamentsCollection(List<Departaments> departamentsCollection) {
        this.departamentsCollection = departamentsCollection;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituteName() {
        return this.instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Workers getChiefId() {
        return this.chiefId;
    }

    public void setChiefId(Workers chiefId) {
        this.chiefId = chiefId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.departamentsCollection);
        hash = 17 * hash + Objects.hashCode(this.instituteName);
        hash = 17 * hash + Objects.hashCode(this.chiefId);
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
        final Institutes other = (Institutes) obj;
        if (!Objects.equals(this.instituteName, other.instituteName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.chiefId, other.chiefId)) {
            return false;
        }
        return Objects.equals(this.departamentsCollection, other.departamentsCollection);
    }

    @Override
    public String toString() {
        return "Institutes{" + "id=" + id + ", departamentsCollection=" + departamentsCollection + ", chiefId=" + chiefId + ", instituteName=" + instituteName + '}';
    }

}
