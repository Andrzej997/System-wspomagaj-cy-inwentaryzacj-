package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;
import java.util.Objects;

public class UsersPK implements Serializable {

    private Long userId;

    private Long workers;

    public UsersPK() {

    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWorkers() {
        return this.workers;
    }

    public void setWorkers(Long workers) {
        this.workers = workers;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.userId);
        hash = 59 * hash + Objects.hashCode(this.workers);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsersPK other = (UsersPK) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return Objects.equals(this.workers, other.workers);
    }

    @Override
    public String toString() {
        return "UsersPK{" + "userId=" + userId + ", workers=" + workers + '}';
    }

}
