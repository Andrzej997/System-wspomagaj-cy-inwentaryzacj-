package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author matis
 */
@Stateful(mappedName = "DepartamentsFacade")
public class DepartamentsFacade extends AbstractFacade<Departaments> implements DepartamentsFacadeRemote {

    private static final long serialVersionUID = 1982444506455129579L;

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
}
