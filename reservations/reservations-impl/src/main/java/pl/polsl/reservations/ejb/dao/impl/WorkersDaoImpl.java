package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.DepartamentsDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.WorkersDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.Workers;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class WorkersDaoImpl extends AbstractDaoImpl<Workers> implements WorkersDao {

    private static final long serialVersionUID = -509559572309358716L;

    private RoomDao roomFacadeRemote;

    private DepartamentsDao departamentsFacadeRemote;

    public WorkersDaoImpl() throws NamingException {
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
        worker.setChief(null);
        List<Room> roomCollection = worker.getRoomCollection();
        for(Room room : roomCollection){
            room.setKeeper(null);
            roomFacadeRemote.merge(room);
        }

        Room room = worker.getRoom();
        List<Workers> workers = room.getWorkerses();
        workers.remove(worker);
        room.setWorkerses(workers);
        roomFacadeRemote.merge(room);

        Departaments departament = worker.getDepartament();
        List<Workers> workersCollection = departament.getWorkersCollection();
        workersCollection.remove(worker);
        departament.setWorkersCollection(workersCollection);

        super.remove(worker);
    }

    @Override
    protected void getDependencies(){
        try {
            roomFacadeRemote = new RoomDaoImpl();
            departamentsFacadeRemote = new DepartamentsDaoImpl();
            roomFacadeRemote.setUserContext(userContext);
            departamentsFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
