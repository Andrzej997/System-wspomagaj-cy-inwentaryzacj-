package org.hibernate.jpa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

public class LazyHibernatePersistenceProvider extends HibernatePersistenceProvider {

    @SuppressWarnings("rawtypes")
    @Override
    public EntityManagerFactory createContainerEntityManagerFactory(final PersistenceUnitInfo info,
            final Map properties) {

        return (EntityManagerFactory) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{EntityManagerFactory.class}, new InvocationHandler() {
            private volatile EntityManagerFactory entityManagerFactory = null;

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(getEntityManagerFactory(info, properties), args);
            }

            protected EntityManagerFactory getEntityManagerFactory(PersistenceUnitInfo info, Map properties) {
                EntityManagerFactory entityManagerFactoryx = this.entityManagerFactory;
                if (entityManagerFactoryx == null) {
                    synchronized (LazyHibernatePersistenceProvider.this) {
                        entityManagerFactoryx = this.entityManagerFactory;
                        if (entityManagerFactoryx == null) {
                            this.entityManagerFactory = entityManagerFactoryx = LazyHibernatePersistenceProvider.this
                                    .createParentContainerEntityManagerFactory(info, properties);
                        }
                    }
                }
                return entityManagerFactoryx;
            }
        });
    }

    protected EntityManagerFactory createParentContainerEntityManagerFactory(PersistenceUnitInfo info, Map properties) {
        return super.createContainerEntityManagerFactory(info, properties);
    }
}
