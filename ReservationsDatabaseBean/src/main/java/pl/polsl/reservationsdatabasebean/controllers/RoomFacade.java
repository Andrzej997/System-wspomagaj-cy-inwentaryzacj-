package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;
import javax.persistence.Query;

/**
 * @author matis
 */
@Stateful(mappedName = "RoomFacade")
public class RoomFacade extends AbstractFacade<Room> implements RoomFacadeRemote {

    private static final long serialVersionUID = 3846358829048447657L;

    public RoomFacade() throws NamingException {
        super(Room.class);
    }

    @Override
    public Room getRoomByNumber(int number){
        Query query = getEntityManager().createNamedQuery("getRoomByNumber", Room.class);
        query.setParameter("roomNumber", number);
        return (Room)query.getSingleResult();
    }
}
