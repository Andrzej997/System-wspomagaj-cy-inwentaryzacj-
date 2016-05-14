package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.DepartamentsFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.InstitutesFacadeRemote;

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
public class InstitutesFacade extends AbstractFacade<Institutes> implements InstitutesFacadeRemote {

    private static final long serialVersionUID = 6300433953924621009L;

    private DepartamentsFacadeRemote departamentsFacadeRemote;

    public InstitutesFacade() throws NamingException {
        super(Institutes.class);
    }

    @Override
    public Institutes getInstituteByName(String name){
        Query query = getEntityManager().createNamedQuery("getInstituteByName", Institutes.class);
        query.setParameter("name", name);
        return (Institutes) query.getSingleResult();
    }

    @Override
    public Institutes getInstituteByChiefId(Long chiefId){
        Query query = getEntityManager().createNamedQuery("getInstituteByChiefId", Institutes.class);
        query.setParameter("id", chiefId);
        return (Institutes) query.getSingleResult();
    }

    @Override
    public List<Departaments> getDepartamentsCollectionById(Number id){
        Institutes institutes = this.find(id);
        return institutes.getDepartamentsCollection();
    }

    @Override
    public void remove(Institutes entity) {
        getDependencies();

        Institutes institute = this.find(entity.getId());
        List<Departaments> departamentsCollection = institute.getDepartamentsCollection();
        for(Departaments departament : departamentsCollection){
            departamentsFacadeRemote.remove(departament);
        }

        super.remove(institute);
    }

    protected void getDependencies(){
        try {
            departamentsFacadeRemote = new DepartamentsFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        departamentsFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
