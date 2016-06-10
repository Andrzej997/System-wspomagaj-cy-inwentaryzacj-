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
 * @author matis
 */
public abstract class AbstractDaoImpl<T> implements Serializable, AbstractDao<T> {

    private static final long serialVersionUID = -13071878948980250L;
    private final Class<T> entityClass;
    private final UserTransaction userTransaction;

    protected UserContext userContext;

    private final UsersCertifcatesPool usersCertifcatesPool;

    protected AbstractDaoImpl(Class<T> entityClass) throws NamingException {
        this.entityClass = entityClass;
        userTransaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        usersCertifcatesPool = UsersCertifcatesPoolImpl.getInstance();
    }

    @Override
    public UsersCertifcatesPool getUsersCertifcatesPool() {
        return this.usersCertifcatesPool;
    }

    protected abstract void getDependencies();

    @Override
    public void setUserContext(String userCertificate) {
        if (usersCertifcatesPool.checkCertificate(userCertificate)) {
            userContext = usersCertifcatesPool.getUserContextByCertificate(userCertificate);
        }
    }

    @Override
    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    protected EntityManager getEntityManager() {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        return em;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void create(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        em.persist(entity);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void edit(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        em.merge(entity);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void remove(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        em.remove(em.merge(entity));
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void merge(T entity) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        em.joinTransaction();
        em.merge(entity);
    }

    @Override
    public T find(Object id) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        id = getAppropriateIdValue(id);
        return em.find(entityClass, id);
    }

    @Override
    public T getReference(Object id) {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        id = getAppropriateIdValue(id);
        return em.getReference(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

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

    @Nullable
    private Object getAppropriateIdValue(Object value) {
        Long idValue = getLongValue(value);
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

    @Override
    public void closeEntityManager() {
        PrivilegeLevelEnum level = userContext.getPrivilegeLevel();
        EntityManager em = level.getEntityManager();
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
