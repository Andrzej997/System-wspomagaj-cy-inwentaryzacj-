package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CHIEFS")
public class Chiefs implements Serializable {

    @Id
    @Column(name = "WORKER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workerId;

    @ManyToOne(optional = true, targetEntity = Institutes.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "INSTITUTE_ID", insertable = true, updatable = true)
    private Institutes instituteId;

    @OneToOne(optional = true, targetEntity = Workers.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "WORKER_ID", insertable = true, updatable = true)
    private Workers workers;

    @ManyToOne(optional = true, targetEntity = Departaments.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTAMENT_ID", insertable = true, updatable = true)
    private Departaments departamentId;

    public Chiefs() {

    }

    public Long getWorkerId() {
        return this.workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Institutes getInstituteId() {
        return this.instituteId;
    }

    public void setInstituteId(Institutes instituteId) {
        this.instituteId = instituteId;
    }

    public Workers getWorkers() {
        return this.workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
    }

    public Departaments getDepartamentId() {
        return this.departamentId;
    }

    public void setDepartamentId(Departaments departamentId) {
        this.departamentId = departamentId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.workerId);
        hash = 71 * hash + Objects.hashCode(this.instituteId);
        hash = 71 * hash + Objects.hashCode(this.workers);
        hash = 71 * hash + Objects.hashCode(this.departamentId);
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
        final Chiefs other = (Chiefs) obj;
        if (!Objects.equals(this.workerId, other.workerId)) {
            return false;
        }
        if (!Objects.equals(this.instituteId, other.instituteId)) {
            return false;
        }
        if (!Objects.equals(this.workers, other.workers)) {
            return false;
        }
        return Objects.equals(this.departamentId, other.departamentId);
    }

    @Override
    public String toString() {
        return "Chiefs{" + "workerId=" + workerId + ", instituteId=" + instituteId + ", workers=" + workers + ", departamentId=" + departamentId + '}';
    }
    
}
