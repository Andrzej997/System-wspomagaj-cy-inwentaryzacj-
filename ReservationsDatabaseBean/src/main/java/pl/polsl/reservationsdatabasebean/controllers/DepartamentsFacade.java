package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.Workers;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.InstitutesFacadeRemote;
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
public class DepartamentsFacade extends AbstractFacade<Departaments> implements DepartamentsFacadeRemote {

    private static final long serialVersionUID = 1982444506455129579L;
    private RoomFacadeRemote roomFacadeRemote;
    private WorkersFacadeRemote workersFacadeRemote;
    private InstitutesFacadeRemote institutesFacadeRemote;

    public DepartamentsFacade() throws NamingException {
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
            room.setDepartamentId(null);
            roomFacadeRemote.merge(room);
        }

        List<Workers> workersCollection = departament.getWorkersCollection();
        for(Workers worker : workersCollection){
            workersFacadeRemote.remove(worker);
        }

        Institutes institute = departament.getInstituteId();
        List<Departaments> departamentsCollection = institute.getDepartamentsCollection();
        departamentsCollection.remove(departament);
        institute.setDepartamentsCollection(departamentsCollection);
        institutesFacadeRemote.merge(institute);

        super.remove(departament);
    }

    protected void getDependencies(){
        try {
            roomFacadeRemote = new RoomFacade();
            workersFacadeRemote = new WorkersFacade();
            institutesFacadeRemote = new InstitutesFacade();

        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        roomFacadeRemote.setPriviligeLevel(priviligeLevel);
        workersFacadeRemote.setPriviligeLevel(priviligeLevel);
        institutesFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
