package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.context.PriviligeContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author matis
 */
public abstract class AbstractFacade<T> implements Serializable {

    private static final long serialVersionUID = -13071878948980250L;
    private final Class<T> entityClass;
    private final PriviligeContext priviligeContext;
    private EntityManager em;
    private final UserTransaction userTransaction;

    public AbstractFacade() throws NamingException {
        entityClass = null;
        priviligeContext = new PriviligeContext();
        priviligeContext.setPriviligeLevel(1);
        em = priviligeContext.getEntityManager();
        userTransaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
    }

    protected AbstractFacade(Class<T> entityClass) throws NamingException {
        this.entityClass = entityClass;
        priviligeContext = new PriviligeContext();
        priviligeContext.setPriviligeLevel(1);
        em = priviligeContext.getEntityManager();
        userTransaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
    }

    protected PriviligeContext getPriviligeContext() {
        return priviligeContext;
    }

    protected UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    public void setPriviligeLevel(Integer level) {
        priviligeContext.setPriviligeLevel(level);
        em = priviligeContext.getEntityManager();
        em.setFlushMode(FlushModeType.COMMIT);
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void create(T entity) {
        try {
            getUserTransaction().begin();
            em.joinTransaction();
            em.persist(entity);
            em.flush();
            getUserTransaction().commit();
        } catch (Exception e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }

    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void edit(T entity) {
        try {
            getUserTransaction().begin();
            em.joinTransaction();
            em.merge(entity);
            em.flush();
            getUserTransaction().commit();
        } catch (Exception e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void remove(T entity) {
        try {
            getUserTransaction().begin();
            em.joinTransaction();
            em.remove(entity);
            getUserTransaction().commit();
        } catch (Exception e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void merge(T entity) {
        try {
            getUserTransaction().begin();
            em.joinTransaction();
            em.merge(entity);
            getUserTransaction().commit();
        } catch (Exception e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
    }

    public T find(Object id) {
        id = getAppropriateIdValue(id);
        return em.find(entityClass, id);
    }

    public T getReference(Object id) {
        id = getAppropriateIdValue(id);
        return em.getReference(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findEntity(List<String> columnNames, List<Object> values) {
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

}
