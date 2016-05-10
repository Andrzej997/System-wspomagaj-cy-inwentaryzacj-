package pl.polsl.reservationsdatabasebeanremote.database;

import java.io.Serializable;

public class UsersPK implements Serializable {

    private static final long serialVersionUID = -7467824673158319231L;

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

}
