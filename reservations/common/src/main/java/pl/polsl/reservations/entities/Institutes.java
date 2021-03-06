package pl.polsl.reservations.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pl.polsl.reservations.logger.LoggerImpl;

@NamedQueries({
    @NamedQuery(name = "getInstituteByName", query = "select i from Institutes i where i.instituteName = :name"),
    @NamedQuery(name = "getInstituteByChiefId", query = "select i from Institutes i where i.chief.id = :id")
})

@Entity
@Table(name = "INSTITUTES")
@EntityListeners(LoggerImpl.class)
public class Institutes implements Serializable {

    private static final long serialVersionUID = -7904312398532927244L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = true, insertable = true, nullable = false)
    private Long id;

    @OneToMany(targetEntity = Departaments.class, mappedBy = "institute", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Departaments> departamentsCollection;

    @Column(name = "INSTITUTE_NAME", updatable = true, insertable = true, nullable = false)
    private String instituteName;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Workers.class)
    @PrimaryKeyJoinColumn
    private Workers chief;

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

    public Workers getChief() {
        return this.chief;
    }

    public void setChief(Workers chief) {
        this.chief = chief;
    }

}
