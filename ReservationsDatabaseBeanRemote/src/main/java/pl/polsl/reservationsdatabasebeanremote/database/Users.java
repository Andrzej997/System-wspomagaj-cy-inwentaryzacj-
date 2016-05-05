package pl.polsl.reservationsdatabasebeanremote.database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NamedQueries({@NamedQuery(name = "validateUser", query = "select u from Users u where u.username = :username and u.password = :password"),
                @NamedQuery(name = "validateUserByEmail", query = "select u from Users u where u.email = :email and u.password = :password"),
                @NamedQuery(name = "getUserPrivligeLevelByUsername", query = "select u.priviligeLevel from Users u where u.username = :username"),
                @NamedQuery(name = "getWorkerByUsername", query = "select u.workers from Users u where u.username = :username")
                })

@Entity
@Table(name = "USERS")
public class Users implements Serializable {

    private static final long serialVersionUID = 6427096331927019081L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", updatable = true, insertable = true, nullable = false)
    private Long userId;

    @Column(name = "PASSWORD", updatable = true, insertable = true, nullable = false)
    private String password;

    @OneToMany(targetEntity = Reservations.class, cascade = CascadeType.ALL)
    private List<Reservations> reservationsCollection;

    @ManyToOne(optional = true, targetEntity = PriviligeLevels.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIVILIGE_LEVEL", insertable = true, nullable = true, updatable = true)
    private PriviligeLevels priviligeLevel;

    @Column(name = "USER_TYPE", updatable = true, insertable = true, nullable = false)
    private short userType;

    @OneToOne(optional = false, targetEntity = Workers.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Workers workers;

    @Column(name = "USERNAME", updatable = true, insertable = true, nullable = false)
    private String username;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PHONENUMBER", updatable = true, insertable = true, nullable = false)
    private Long phoneNumber;

    public Users() {

    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Reservations> getReservationsCollection() {
        return this.reservationsCollection;
    }

    public void setReservationsCollection(List<Reservations> reservationsCollection) {
        this.reservationsCollection = reservationsCollection;
    }

    public PriviligeLevels getPriviligeLevel() {
        return this.priviligeLevel;
    }

    public void setPriviligeLevel(PriviligeLevels priviligeLevel) {
        this.priviligeLevel = priviligeLevel;
    }

    public short getUserType() {
        return this.userType;
    }

    public void setUserType(short userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Workers getWorkers() {
        return this.workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
