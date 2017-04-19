package xyz.yanyj.base.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanyj on 2017/4/17.
 */
public interface GenericDao<T, PK extends Serializable> {
    T load(PK id);

    T get(PK id);

    List<T> findAll();

    void persist(T entity);

    PK save(T entity);

    void saveOrUpdate(T entity);

    void delete(PK id);

    void flush();

}
