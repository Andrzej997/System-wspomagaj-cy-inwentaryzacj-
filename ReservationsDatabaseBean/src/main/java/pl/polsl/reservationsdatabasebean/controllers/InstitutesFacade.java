package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.InstitutesFacadeRemote;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class InstitutesFacade extends AbstractFacade<Institutes> implements InstitutesFacadeRemote {

    private static final long serialVersionUID = 6300433953924621009L;

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

}
