package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.WorkersFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class WorkersFacade extends AbstractFacade<Workers> implements WorkersFacadeRemote {

    private static final long serialVersionUID = -509559572309358716L;

    private RoomFacadeRemote roomFacadeRemote;

    private DepartamentsFacadeRemote departamentsFacadeRemote;

    public WorkersFacade() throws NamingException {
        super(Workers.class);
    }

    @Override
    public List<Workers> getWorkersByName(String workerName){
        Query query = this.getEntityManager().createNamedQuery("getWorkersByName", Workers.class);
        query.setParameter("workerName", workerName);
        return query.getResultList();
    }

    @Override
    public List<Workers> getWorkersBySurname(String surname){
        Query query = this.getEntityManager().createNamedQuery("getWorkersBySurname", Workers.class);
        query.setParameter("surname", surname);
        return query.getResultList();
    }

    @Override
    public List<Workers> getWorkersByGrade(String grade){
        Query query = this.getEntityManager().createNamedQuery("getWorkersByGrade", Workers.class);
        query.setParameter("grade", grade);
        return query.getResultList();
    }

    @Override
    public List<Workers> getWorkersByAdress(String adress){
        Query query = this.getEntityManager().createNamedQuery("getWorkersByAdress", Workers.class);
        query.setParameter("adress", adress);
        return query.getResultList();
    }

    @Override
    public List<Workers> getWorkerByPesel(String pesel){
        Query query = this.getEntityManager().createNamedQuery("getWorkerByPesel", Workers.class);
        query.setParameter("pesel", pesel);
        return query.getResultList();
    }

    @Override
    public List<Room> getRoomsCollectionByKeeperId(Long id){
        Query query = this.getEntityManager().createNamedQuery("getRoomsCollectionByKeeperId", Room.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Departaments getDepartamentByWorkerId(Long id){
        Query query = this.getEntityManager().createNamedQuery("getDepartamentByWorkerId", Departaments.class);
        query.setParameter("id", id);
        return (Departaments) query.getSingleResult();
    }

    @Override
    public List<Workers> getAllChiefs(){
        Query query = this.getEntityManager().createNamedQuery("getAllChiefs", Workers.class);
        return query.getResultList();
    }

    @Override
    public List<Workers> getWorkersWhichHaveChief(){
        Query query = this.getEntityManager().createNamedQuery("getWorkersWhichHaveChief", Workers.class);
        return query.getResultList();
    }

    @Override
    public List<Room> getRoomCollectionById(Number id){
        Workers workers = this.find(id);
        return workers.getRoomCollection();
    }

    @Override
    public void remove(Workers entity) {
        getDependencies();

        Workers worker = this.find(entity.getId());
        worker.setChiefId(null);
        List<Room> roomCollection = worker.getRoomCollection();
        for(Room room : roomCollection){
            room.setKeeperId(null);
            roomFacadeRemote.merge(room);
        }

        Room room = worker.getRoom();
        List<Workers> workers = room.getWorkerses();
        workers.remove(worker);
        room.setWorkerses(workers);
        roomFacadeRemote.merge(room);

        Departaments departament = worker.getDepartamentId();
        List<Workers> workersCollection = departament.getWorkersCollection();
        workersCollection.remove(worker);
        departament.setWorkersCollection(workersCollection);

        super.remove(worker);
    }

    protected void getDependencies(){
        try {
            roomFacadeRemote = new RoomFacade();
            departamentsFacadeRemote = new DepartamentsFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        roomFacadeRemote.setPriviligeLevel(priviligeLevel);
        departamentsFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
