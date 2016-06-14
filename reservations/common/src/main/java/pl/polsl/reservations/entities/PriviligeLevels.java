package pl.polsl.reservations.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;
@NamedQueries({
        @NamedQuery(name = "getPrivligeLevelsEntityByLevelValue", query = "select pl from PriviligeLevels pl where pl.priviligeLevel = :levelValue")
})


@Entity
@Table(name = "PRIVILIGE_LEVELS")
@EntityListeners(LoggerImpl.class)
public class PriviligeLevels implements Serializable {

    private static final long serialVersionUID = 7642276419680143633L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVILIGE_LEVEL", updatable = true, insertable = true, nullable = false)
    private Long priviligeLevel;

    @ManyToMany(targetEntity = Priviliges.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PRIVILIGE_MAP", joinColumns = {
            @JoinColumn(name = "PRIVILIGE_LEVEL", insertable = true, nullable = false, updatable = true)}, inverseJoinColumns = {
            @JoinColumn(name = "PRIVILIGE_ID", insertable = true, nullable = false, updatable = true)})
    private List<Priviliges> priviligesCollection;

    @Column(name = "DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String description;

    @OneToMany(targetEntity = Users.class, mappedBy = "priviligeLevel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

}
