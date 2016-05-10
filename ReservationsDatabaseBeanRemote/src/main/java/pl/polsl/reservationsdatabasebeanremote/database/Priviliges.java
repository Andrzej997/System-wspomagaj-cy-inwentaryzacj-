package pl.polsl.reservationsdatabasebeanremote.database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRIVILIGES")
public class Priviliges implements Serializable {

    private static final long serialVersionUID = -280370735684039513L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVILIGE_ID", updatable = true, insertable = true, nullable = false)
    private Long priviligeId;

    @Column(name = "DESCRIPTION", updatable = true, insertable = true, nullable = true)
    @Lob
    private String description;

    @ManyToMany(targetEntity = PriviligeLevels.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

}
