package com.adi3000.code.synch.redminetrello.data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.adi3000.code.synch.redminetrello.common.AbstractLogger;

public class AbstractDAO<T> extends AbstractLogger{
    @SuppressWarnings("unchecked")
    private final Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    @Inject
    private transient SessionFactory sessionFactory;
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    public Serializable save(T data) {
        getLogger().debug("Save object of class {}", clazz.getName());
        return save(Collections.singleton(data)).iterator().next();
    }

    public Collection<Serializable> save(Collection<T> collection)  {
        getLogger().debug("Save objects of class {}", clazz.getName());
        List<Serializable> collectionsId = new ArrayList<>();
        for (T dtObject : collection) {
            collectionsId.add(getSession().save(dtObject));
        }
        return collectionsId;
    }

    public T find(Serializable id) {
        T data = (T) getSession().get(clazz, id);
        return data;
    }

    public Criteria createCriteria(){
    	return getSession().createCriteria(clazz);
    }


}
