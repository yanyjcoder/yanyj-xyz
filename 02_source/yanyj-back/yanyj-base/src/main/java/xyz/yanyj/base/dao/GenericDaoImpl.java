package xyz.yanyj.base.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanyj on 2017/4/18.
 */
public class GenericDaoImpl<T, pk extends Serializable>{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }


    public Class<T> entryClass;

    public T load(pk id) {
        return (T) this.getCurrentSession().load(entryClass, id);
    }



    public T get(pk id) {
        return (T) this.getCurrentSession().get(entryClass, id);
    }


    public void persist(T entity) {
        this.getCurrentSession().persist(entity);

    }

    public String save(T entity) {
        return (String) this.getCurrentSession().save(entity);
    }

    public void saveOrUpdate(T entity) {
        this.getCurrentSession().saveOrUpdate(entity);
    }

    public void delete(pk id) {
        T entity = this.load(id);
        this.getCurrentSession().delete(entity);
    }

    public void flush() {
        this.getCurrentSession().flush();

    }
}
