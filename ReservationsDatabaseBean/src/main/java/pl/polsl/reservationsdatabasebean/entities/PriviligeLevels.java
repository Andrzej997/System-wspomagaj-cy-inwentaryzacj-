package pl.polsl.reservationsdatabasebean.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRIVILIGE_LEVELS")
public class PriviligeLevels implements Serializable {

    private static final long serialVersionUID = 7642276419680143633L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVILIGE_LEVEL", updatable = true, insertable = true, nullable = false)
    private Long priviligeLevel;

    @ManyToMany(targetEntity = Priviliges.class, fetch = FetchType.EAGER)
    @JoinTable(name = "PRIVILIGE_MAP", joinColumns = {
            @JoinColumn(name = "PRIVILIGE_LEVEL", insertable = true, nullable = false, updatable = true)}, inverseJoinColumns = {
            @JoinColumn(name = "PRIVILIGE_ID", insertable = true, nullable = false, updatable = true)})
    private List<Priviliges> priviligesCollection;

    @Column(name = "DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String description;

    @OneToMany(targetEntity = Users.class, mappedBy = "priviligeLevel", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Users> usersCollection;

    public PriviligeLevels() {

    }

    public List<Priviliges> getPriviligesCollection() {
        return this.priviligesCollection;
    }

    public void setPriviligesCollection(List<Priviliges> priviligesCollection) {
        this.priviligesCollection = priviligesCollection;
    }

    public Long getPriviligeLevel() {
        return this.priviligeLevel;
    }

    public void setPriviligeLevel(Long priviligeLevel) {
        this.priviligeLevel = priviligeLevel;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Users> getUsersCollection() {
        return this.usersCollection;
    }

    public void setUsersCollection(List<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.priviligeLevel);
        hash = 79 * hash + Objects.hashCode(this.priviligesCollection);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.usersCollection);
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
        final PriviligeLevels other = (PriviligeLevels) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.priviligeLevel, other.priviligeLevel)) {
            return false;
        }
        if (!Objects.equals(this.priviligesCollection, other.priviligesCollection)) {
            return false;
        }
        return Objects.equals(this.usersCollection, other.usersCollection);
    }

}
