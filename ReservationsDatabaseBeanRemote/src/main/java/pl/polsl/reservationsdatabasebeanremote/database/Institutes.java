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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "INSTITUTES")
public class Institutes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @OneToMany(targetEntity = Departaments.class, mappedBy = "instituteId", cascade = CascadeType.ALL)
    private List<Departaments> departamentsCollection;

    @OneToMany(targetEntity = Chiefs.class, mappedBy = "instituteId", cascade = CascadeType.ALL)
    private List<Chiefs> chiefsCollection;

    @Column(name = "INSTITUTE_NAME", updatable = true, insertable = true, nullable = false)
    private String instituteName;

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

    public List<Chiefs> getChiefsCollection() {
        return this.chiefsCollection;
    }

    public void setChiefsCollection(List<Chiefs> chiefsCollection) {
        this.chiefsCollection = chiefsCollection;
    }

    public String getInstituteName() {
        return this.instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.departamentsCollection);
        hash = 17 * hash + Objects.hashCode(this.chiefsCollection);
        hash = 17 * hash + Objects.hashCode(this.instituteName);
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
        if (!Objects.equals(this.departamentsCollection, other.departamentsCollection)) {
            return false;
        }
        return Objects.equals(this.chiefsCollection, other.chiefsCollection);
    }

    @Override
    public String toString() {
        return "Institutes{" + "id=" + id + ", departamentsCollection=" + departamentsCollection + ", chiefsCollection=" + chiefsCollection + ", instituteName=" + instituteName + '}';
    }
    
}
