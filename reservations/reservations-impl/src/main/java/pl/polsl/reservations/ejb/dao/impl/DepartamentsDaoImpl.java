package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.DepartamentsDao;
import pl.polsl.reservations.ejb.dao.InstitutesDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.WorkersDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.entities.Workers;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author Mateusz Sojka
 * @version
 *
 * Departaments data access object implementation
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DepartamentsDaoImpl extends AbstractDaoImpl<Departaments> implements DepartamentsDao {

    private static final long serialVersionUID = 1982444506455129579L;
    private RoomDao roomFacadeRemote;
    private WorkersDao workersFacadeRemote;
    private InstitutesDao institutesFacadeRemote;

    public DepartamentsDaoImpl() throws NamingException {
        super(Departaments.class);
    }

    /**
     * Method to get departament by name
     *
     * @param name String with departament name
     * @return Departaments entity
     */
    @Override
    public Departaments getDepartamentByName(String name) {
        Query query = getEntityManager().createNamedQuery("getDepartamentByName", Departaments.class);
        query.setParameter("name", name);
        try {
            return (Departaments) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * Method to find departaments which have workers assigned
     *
     * @return List of departaments
     */
    @Override
    public List<Departaments> findDepartametsHavingWorkers() {
        Query query = getEntityManager().createNamedQuery("findDepartametsHavingWorkers", Departaments.class);
        return query.getResultList();
    }

    /**
     * Method to get departament by his chief
     *
     * @param chiefId Long with chief id
     * @return Departaments entity
     */
    @Override
    public Departaments getDepartamentByChiefId(Long chiefId) {
        Query query = getEntityManager().createNamedQuery("getDepartamentByChiefId", Departaments.class);
        query.setParameter("id", chiefId);
        try {
            return (Departaments) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * Method to deparaments room collection by departament id
     *
     * @param id departament id
     * @return list of rooms
     */
    @Override
    public List<Room> getRoomCollectionById(Long id) {
        Departaments departament = this.find(id);
        return departament.getRoomCollection();
    }

    /**
     * Method to get departaments' workers by departament id
     *
     * @param id departament id
     * @return list of workers
     */
    @Override
    public List<Workers> getWorkersCollectionById(Long id) {
        Departaments departament = this.find(id);
        return departament.getWorkersCollection();
    }

    /**
     * Method used to remove entity from database
     *
     * @param entity
     */
    @Override
    public void remove(Departaments entity) {
        getDependencies();

        Departaments departament = this.find(entity.getId());
        /* List<Room> roomCollection = departament.getRoomCollection();
        for (Room room : roomCollection) {
            room.setDepartament(null);
            roomFacadeRemote.merge(room);
        }
        departament.setRoomCollection(null);

        List<Workers> workersCollection = departament.getWorkersCollection();
        for (Workers worker : workersCollection) {
            workersFacadeRemote.remove(worker);
        }
        departament.setRoomCollection(null);

        Institutes institute = departament.getInstitute();
        List<Departaments> departamentsCollection = institute.getDepartamentsCollection();
        departamentsCollection.remove(departament);
        institute.setDepartamentsCollection(departamentsCollection);
        institutesFacadeRemote.merge(institute);
        departament.setInstitute(null);*/

        super.remove(departament);
    }

    @Override
    protected void getDependencies() {
        try {
            roomFacadeRemote = new RoomDaoImpl();
            workersFacadeRemote = new WorkersDaoImpl();
            institutesFacadeRemote = new InstitutesDaoImpl();
            roomFacadeRemote.setUserContext(userContext);
            workersFacadeRemote.setUserContext(userContext);
            institutesFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
