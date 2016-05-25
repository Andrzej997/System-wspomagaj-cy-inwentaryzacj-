package pl.polsl.reservations.ejb.local;

import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by matis on 14.05.2016.
 */
public interface AbstractDao<T> {
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