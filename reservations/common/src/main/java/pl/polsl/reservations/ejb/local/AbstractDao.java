package pl.polsl.reservations.ejb.local;

import java.util.List;
import javax.transaction.UserTransaction;

/**
 * @author Mateusz Sojka
 * @version 1.0 
 * 
 * Abstract interface to all dao classes
 */
public interface AbstractDao<T> {

    /**
     * Method to obtain user context by user certifate
     *
     * @param userCertificate String with certificate
     */
    void setUserContext(String userCertificate);

    /**
     * UserContext setter
     *
     * @param userContext UserContext
     */
    void setUserContext(UserContext userContext);

    /**
     * Method to obtain UserCertificatesPool objects
     *
     * @return UsersCertifcatesPool
     */
    UsersCertifcatesPool getUsersCertifcatesPool();

    /**
     * Method creates UserTransaction
     *
     * @return UserTransaction
     */
    UserTransaction getUserTransaction();

    /**
     * Method creates entity
     *
     * @param entity T type of entity class
     */
    void create(T entity);

    /**
     * Method to edit entity
     *
     * @param entity T type of entity class
     */
    void edit(T entity);

    /**
     * Method to remove entity
     *
     * @param entity T type of entity class
     */
    void remove(T entity);

    /**
     * Method to merge entity
     *
     * @param entity T type of entity class
     */
    void merge(T entity);

    /**
     * Method to find entity by id
     *
     * @param id value of Numeric type
     * @return T found entity or null
     */
    T find(Object id);

    /**
     * Method to get reference of entity by id
     *
     * @param id value of Numeric type
     * @return T found entity reference or null
     */
    T getReference(Object id);

    /**
     * Method to get all entities
     *
     * @return List of entites
     */
    List<T> findAll();

    /**
     * Method to find all enitities in given range
     *
     * @param range int array
     * @return List of entities
     */
    List<T> findRange(int[] range);

    /**
     * Method to get count of entities in database
     *
     * @return int count of entities
     */
    int count();

    /**
     * Method to find entity by column names and thier values
     *
     * @param columnNames List of column names
     * @param values List of column values
     * @return List of found entities
     */
    List<T> findEntity(List<String> columnNames, List<Object> values);

    /**
     * Method to close entity manager
     */
    void closeEntityManager();
}
