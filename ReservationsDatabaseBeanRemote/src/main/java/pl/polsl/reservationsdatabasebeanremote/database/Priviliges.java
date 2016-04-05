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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRIVILIGES")
public class Priviliges implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVILIGE_ID", updatable = true, insertable = true, nullable = false)
    private Long priviligeId;

    @Column(name = "DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String description;

    @ManyToMany(targetEntity = PriviligeLevels.class, cascade = CascadeType.ALL)
    @JoinTable(name = "PRIVILIGE_MAP", joinColumns = {
        @JoinColumn(name = "PRIVILIGE_ID", insertable = true, nullable = false, updatable = true)}, inverseJoinColumns = {
        @JoinColumn(name = "PRIVILIGE_LEVEL", insertable = true, nullable = false, updatable = true)})
    private List<PriviligeLevels> priviligeLevelsCollection;

    @Column(name = "PRIVILEGE_NAME", updatable = true, insertable = true, nullable = false)
    private String privilegeName;

    public Priviliges() {

    }

    public Long getPriviligeId() {
        return this.priviligeId;
    }

    public void setPriviligeId(Long priviligeId) {
        this.priviligeId = priviligeId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PriviligeLevels> getPriviligeLevelsCollection() {
        return this.priviligeLevelsCollection;
    }

    public void setPriviligeLevelsCollection(List<PriviligeLevels> priviligeLevelsCollection) {
        this.priviligeLevelsCollection = priviligeLevelsCollection;
    }

    public String getPrivilegeName() {
        return this.privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.priviligeId);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.priviligeLevelsCollection);
        hash = 59 * hash + Objects.hashCode(this.privilegeName);
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
        final Priviliges other = (Priviliges) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.privilegeName, other.privilegeName)) {
            return false;
        }
        if (!Objects.equals(this.priviligeId, other.priviligeId)) {
            return false;
        }
        return Objects.equals(this.priviligeLevelsCollection, other.priviligeLevelsCollection);
    }

    @Override
    public String toString() {
        return "Priviliges{" + "priviligeId=" + priviligeId + ", description=" + description + ", priviligeLevelsCollection=" + priviligeLevelsCollection + ", privilegeName=" + privilegeName + '}';
    }
    
}
