package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.context.PriviligeContext;
import pl.polsl.reservationsdatabasebeanremote.database.Reservations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author matis
 */
public abstract class AbstractFacade<T> implements Serializable {

    private static final long serialVersionUID = -13071878948980250L;

    private final Class<T> entityClass;

    private final PriviligeContext priviligeContext;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        priviligeContext = new PriviligeContext();
        priviligeContext.setPriviligeLevel(1);

    }

    public void setPriviligeLevel(Integer level) {
        priviligeContext.setPriviligeLevel(level);
    }

    protected EntityManager getEntityManager() {
        return priviligeContext.getEntityManager();
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void merge(T entity) {
        getEntityManager().merge(entity);
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public T getReference(Object id) {
        return getEntityManager().getReference(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findEntity(List<String> columnNames, List<Object> values) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = cb.createQuery();
        Root<T> rt = criteriaQuery.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        int i = 0;
        for (String name : columnNames) {
            predicates.add(cb.equal(rt.get(name), values.get(i)));
            i++;
        }
        criteriaQuery.select(rt).where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        List<T> resultList = query.getResultList();
        return resultList;
    }

}
