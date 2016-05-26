package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.DepartamentsDao;
import pl.polsl.reservations.ejb.dao.InstitutesDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.ejb.dao.WorkersDao;
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Institutes;
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
public class DepartamentsDaoImpl extends AbstractDaoImpl<Departaments> implements DepartamentsDao {

    private static final long serialVersionUID = 1982444506455129579L;
    private RoomDao roomFacadeRemote;
    private WorkersDao workersFacadeRemote;
    private InstitutesDao institutesFacadeRemote;

    public DepartamentsDaoImpl() throws NamingException {
        super(Departaments.class);
    }

    @Override
    public Departaments getDepartamentByName(String name){
        Query query = getEntityManager().createNamedQuery("getDepartamentByName", Departaments.class);
        query.setParameter("name", name);
        return (Departaments) query.getSingleResult();
    }

    @Override
    public List<Departaments> findDepartametsHavingWorkers(){
        Query query = getEntityManager().createNamedQuery("findDepartametsHavingWorkers", Departaments.class);
        return query.getResultList();
    }

    @Override
    public Departaments getDepartamentByChiefId(Long chiefId){
        Query query = getEntityManager().createNamedQuery("getDepartamentByChiefId", Departaments.class);
        query.setParameter("id", chiefId);
        return (Departaments) query.getSingleResult();
    }

    @Override
    public List<Room> getRoomCollectionById(Long id){
        Departaments departament = this.find(id);
        return departament.getRoomCollection();
    }

    @Override
    public List<Workers> getWorkersCollectionById(Long id){
        Departaments departament = this.find(id);
        return departament.getWorkersCollection();
    }

    @Override
    public void remove(Departaments entity) {
        getDependencies();

        Departaments departament = this.find(entity.getId());
        List<Room> roomCollection = departament.getRoomCollection();
        for(Room room : roomCollection){
            room.setDepartament(null);
            roomFacadeRemote.merge(room);
        }

        List<Workers> workersCollection = departament.getWorkersCollection();
        for(Workers worker : workersCollection){
            workersFacadeRemote.remove(worker);
        }

        Institutes institute = departament.getInstitute();
        List<Departaments> departamentsCollection = institute.getDepartamentsCollection();
        departamentsCollection.remove(departament);
        institute.setDepartamentsCollection(departamentsCollection);
        institutesFacadeRemote.merge(institute);

        super.remove(departament);
    }

    @Override
    protected void getDependencies(){
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
