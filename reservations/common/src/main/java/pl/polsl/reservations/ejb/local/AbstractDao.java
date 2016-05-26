package pl.polsl.reservations.ejb.local;

import java.util.List;
import javax.transaction.UserTransaction;

/**
 * Created by matis on 14.05.2016.
 */
public interface AbstractDao<T> {

    void setUserContext(String userCertificate);
    
    void setUserContext(UserContext userContext);
    
    UsersCertifcatesPool getUsersCertifcatesPool();

    UserTransaction getUserTransaction();

    void create(T entity);

    void edit(T entity);

    void remove(T entity);

    void merge(T entity);

    T find(Object id);

    T getReference(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

    int count();

    List<T> findEntity(List<String> columnNames, List<Object> values);

    void closeEntityManager();
}
