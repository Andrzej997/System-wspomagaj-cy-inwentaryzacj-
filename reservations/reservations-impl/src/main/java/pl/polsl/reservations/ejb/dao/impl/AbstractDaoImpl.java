package pl.polsl.reservations.ejb.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import pl.polsl.reservations.ejb.local.AbstractDao;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.ejb.local.UsersCertifcatesPool;
import pl.polsl.reservations.ejb.local.UsersCertifcatesPoolImpl;
import pl.polsl.reservations.privileges.PrivilegeLevelEnum;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Base abstract class to all data access object classes
 */
public abstract class AbstractDaoImpl<T> implements Serializable, AbstractDao<T> {

    private static final long serialVersionUID = -13071878948980250L;

    /**
     * DaoEntity field
     */
    private final Class<T> entityClass;

    /**
     * UserTransaction field
     */
    private final UserTransaction userTransaction;

    /**
     * UserContext field
     */
    protected UserContext userContext;

    /**
     * UserCertificatesPool field
     */
    private final UsersCertifcatesPool usersCertifcatesPool;

    protected AbstractDaoImpl(Class<T> entityClass) throws NamingException {
        this.entityClass = entityClass;
        userTransaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        usersCertifcatesPool = UsersCertifcatesPoolImpl.getInstance();
    }

    /**
     * Method to obtain UserCertificatesPool objects
     *
     * @return UsersCertifcatesPool
     */
    @Override
    public UsersCertifcatesPool getUsersCertifcatesPool() {
        return this.usersCertifcatesPool;
    }

    /**
     * Abstract method to obtain crossing dependencies
     */
    protected abstract void getDependencies();

    /**
     * Method to obtain user context by user certifate
     *
     * @param userCertificate String with certificate
     */
    @Override
    public void setUserContext(String userCertificate) {
        if (usersCertifcatesPool.checkCertificate(userCertificate)) {
            userContext = usersCertifcatesPool.getUserContextByCertificate(userCertificate);
        }
    }

    /**
     * UserContext setter
     *
     * @param userContext UserContext
     */
    @Override
    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    /**
     * Method creates UserTransaction
     *
     * @return UserTransaction
     */
    @Override
    public UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    protected EntityManager getEntityManager() {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        return em;
    }

    /**
     * Method creates entity
     *
     * @param entity T type of entity class
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void create(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        em.persist(entity);
    }

    /**
     * Method to edit entity
     *
     * @param entity T type of entity class
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void edit(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        em.merge(entity);
    }

    /**
     * Method to remove entity
     *
     * @param entity T type of entity class
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void remove(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        T merge = em.merge(entity);
        em.remove(merge);
        em.flush();
    }

    /**
     * Method to merge entity
     *
     * @param entity T type of entity class
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void merge(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        em.merge(entity);
    }

    /**
     * Method to find entity by id
     *
     * @param id value of Numeric type
     * @return T found entity or null
     */
    @Override
    public T find(Object id) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        id = getAppropriateIdValue(id);
        return em.find(entityClass, id);
    }

    /**
     * Method to get reference of entity by id
     *
     * @param id value of Numeric type
     * @return T found entity reference or null
     */
    @Override
    public T getReference(Object id) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        id = getAppropriateIdValue(id);
        return em.getReference(entityClass, id);
    }

    /**
     * Method to get all entities
     *
     * @return List of entites
     */
    @Override
    public List<T> findAll() {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    /**
     * Method to find all enitities in given range
     *
     * @param range int array
     * @return List of entities
     */
    @Override
    public List<T> findRange(int[] range) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Method to get count of entities in database
     *
     * @return int count of entities
     */
    @Override
    public int count() {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Method to find entity by column names and thier values
     *
     * @param columnNames List of column names
     * @param values List of column values
     * @return List of found entities
     */
    @Override
    public List<T> findEntity(List<String> columnNames, List<Object> values) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = cb.createQuery();
        Root<T> rt = criteriaQuery.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : columnNames) {
            predicates.add(cb.equal(rt.get(name), values.get(i)));
            i++;
        }
        criteriaQuery.select(rt).where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<T> query = em.createQuery(criteriaQuery);
        List<T> resultList = query.getResultList();
        return resultList;
    }

    /**
     * Method to get long value from object
     *
     * @param o object data
     * @return Long value
     */
    @Contract("null -> null")
    private Long getLongValue(Object o) {
        if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof Integer) {
            Integer value = (Integer) o;
            return value.longValue();
        } else if (o instanceof Short) {
            Short value = (Short) o;
            return value.longValue();
        } else if (o instanceof Byte) {
            Byte value = (Byte) o;
            return value.longValue();
        } else {
            return null;
        }
    }

    /**
     * Method return appropriate id value and type from any other type value
     *
     * @param value Object value
     * @return Object of id value
     */
    @Nullable
    private Object getAppropriateIdValue(Object value) {
        Long idValue = getLongValue(value);
        if (idValue == null) {
            return null;
        }
        Field[] fields = this.entityClass.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Id) {
                    Class<?> type = field.getType();
                    if (Byte.class == type) {
                        value = idValue.byteValue();
                    } else if (Integer.class == type) {
                        value = idValue.intValue();
                    } else if (Short.class == type) {
                        value = idValue.shortValue();
                    } else if (Long.class == type) {
                        value = idValue;
                    } else {
                        return null;
                    }
                }
            }
        }
        return value;
    }

    /**
     * Method to close entity manager
     */
    @Override
    public void closeEntityManager() {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
