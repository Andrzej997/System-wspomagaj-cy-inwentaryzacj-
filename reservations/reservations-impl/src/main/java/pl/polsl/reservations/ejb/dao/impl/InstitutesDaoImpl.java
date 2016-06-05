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
import pl.polsl.reservations.entities.Departaments;
import pl.polsl.reservations.entities.Institutes;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class InstitutesDaoImpl extends AbstractDaoImpl<Institutes> implements InstitutesDao {

    private static final long serialVersionUID = 6300433953924621009L;

    private DepartamentsDao departamentsFacadeRemote;

    public InstitutesDaoImpl() throws NamingException {
        super(Institutes.class);
    }

    @Override
    public Institutes getInstituteByName(String name) {
        Query query = getEntityManager().createNamedQuery("getInstituteByName", Institutes.class);
        query.setParameter("name", name);
        try {
            return (Institutes) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Institutes getInstituteByChiefId(Long chiefId) {
        Query query = getEntityManager().createNamedQuery("getInstituteByChiefId", Institutes.class);
        query.setParameter("id", chiefId);
        try {
            return (Institutes) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Departaments> getDepartamentsCollectionById(Number id) {
        Institutes institutes = this.find(id);
        return institutes.getDepartamentsCollection();
    }

    @Override
    public void remove(Institutes entity) {
        getDependencies();

        Institutes institute = this.find(entity.getId());
        List<Departaments> departamentsCollection = institute.getDepartamentsCollection();
        for (Departaments departament : departamentsCollection) {
            departamentsFacadeRemote.remove(departament);
        }

        super.remove(institute);
    }

    @Override
    protected void getDependencies() {
        try {
            departamentsFacadeRemote = new DepartamentsDaoImpl();
            departamentsFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
