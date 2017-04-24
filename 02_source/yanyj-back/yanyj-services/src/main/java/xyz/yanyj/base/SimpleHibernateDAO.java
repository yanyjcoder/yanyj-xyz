package xyz.yanyj.base;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import xyz.yanyj.util.PageUtil.QueryParameter;
import xyz.yanyj.util.ReflectUtil.ReflectUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * 封装Hibernate原生API的DAO泛型基类
 *
 *  不包括原生sql语句的操作，也不包括分页查询
 * @param <T> DAO操作的对象类型
 * @param <Id> 主键类型
 *
 * @author Liupj
 *
 * @see HibernateDAO
 *       HibernateDAO 是SimpleHibernateDAO的子类，在它的基础上增强了sql语句curd，以及分页查询
 *
 */
@SuppressWarnings("all")
public class SimpleHibernateDAO<T, Id extends Serializable> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;

    /**
     * 通过子类的泛型定义取得对象类型Class.
     */
    public SimpleHibernateDAO() {
        this.entityClass = ReflectUtil.getClassGenricType(getClass());
    }

    /**
     * 取得当前Session.
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    /****************************** 增 ***********************************/
    /**
     * 保存新增或修改的对象.
     */
    public void save(final T entity) {

        try {
            getSession().saveOrUpdate(entity);
            flush();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    /**
     * 保存新增或修改的对象.
     */
    public void save(List<T> entityList) {

        for (T entity: entityList) {
            save(entity);
        }
    }

    /****************************** 删 ***********************************/
    /**
     * 删除对象.
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    public void delete(T entity) {
        try {
            getSession().delete(entity);
            flush();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    /**
     * 批量删除实体集合
     * @param collection
     */
    public void delete(Collection<T> collection) {
        for (T item : collection) {
            delete(item);
        }
    }

    /**
     * 按id删除对象.
     */
    public void delete(Id id) {
        delete(get(id));
    }

    /**
     * 批量删除
     */
    public void batchDelete(Id[] ids) {
        batchDelete(ids, entityClass);
    }

    /**
     * 批量删除
     * @param ids
     * @param clazz 需要删除的指定实体的Class对象
     */
    public void batchDelete(Id[] ids, Class clazz) {
        if (ids == null || ids.length == 0) {
        }

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Id id : ids) {
            sb.append("'").append(id).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        execute("delete from " + clazz.getSimpleName() + " where " + getIdName(clazz) + " in " + sb);
    }


    /****************************** 查找 **********************************/
    /**
     * 按id获取对象.
     */
    public T get(Id id) {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 按id列表获取对象列表.
     */
    public List<T> get(Collection<Id> ids) {
        return find(Restrictions.in(getIdName(), ids));
    }


    /**
     * 根据hql和命名参数获取实体列表
     * @param charSequence
     * @param namedParameters
     * @return
     */
    public List<T> find(CharSequence hql, Map<String, Object> namedParameters) {
        return createQuery(hql.toString(), namedParameters).list();
    }

    /**
     * 根据hql和命名参数获取实体列表(二级缓存)
     * @param charSequence
     * @param namedParameters
     * @return
     */
    public List<T> findInCache(CharSequence hql, Map<String, Object> namedParameters) {
        return createQuery(hql.toString(), namedParameters).setCacheable(true).list();
    }

    /**
     * 根据hql和命名参数获取唯一实体
     * @param charSequence
     * @param namedParameters
     * @return
     */
    public T findUnique(CharSequence hql, Map<String, Object> namedParameters) {
        return (T) createQuery(hql.toString(), namedParameters).uniqueResult();
    }

    /**
     * 根据hql和命名参数获取唯一实体(二级缓存)
     * @param charSequence
     * @param namedParameters
     * @return
     */
    public T findUniqueInCache(CharSequence hql, Map<String, Object> namedParameters) {
        return (T) createQuery(hql.toString(), namedParameters).setCacheable(true).uniqueResult();
    }

    /**
     * 根据属性组和排序组查询列表（该方法常用）
     * 匹配方式为相等
     * @param properties
     * @param sorts
     * @return
     */
    public List<T> find(Map<String, Object> properties, QueryParameter.Sort... sorts) {
        Criteria c = multiPropertiesAndSortsCriteria(properties, sorts);
        return c.list();
    }

    /**
     * 根据属性组和排序组查询列表（优先缓存中查找，该方法常用）
     * 匹配方式为相等
     * @param properties
     * @param sorts
     * @return
     */
    public List<T> findInCache(Map<String, Object> properties, QueryParameter.Sort... sorts) {
        Criteria c = multiPropertiesAndSortsCriteria(properties, sorts);
        return c.setCacheable(true).list();
    }

    private Criteria multiPropertiesAndSortsCriteria(Map<String, Object> properties, QueryParameter.Sort... sorts) {
        Criteria c = null;

        if (properties == null) {
            c = createCriteria();
        } else {
            List<Criterion> criterions = new ArrayList<Criterion>();
            for (String key: properties.keySet()) {
                criterions.add(Restrictions.eq(key, properties.get(key)));
            }

            c = createCriteria(criterions.toArray(new Criterion[] {}));
        }

        for (QueryParameter.Sort item: sorts) {
            if (item.isAsc()) {
                c.addOrder(Order.asc(item.getFieldName()));
            } else {
                c.addOrder(Order.desc(item.getFieldName()));
            }
        }

        return c;
    }




    /**
     * 按属性查找唯一对象, 匹配方式为相等.（该方法常用）
     */
    public T findUnique(Map<String, Object> properties, QueryParameter.Sort... sorts) {
        Criteria c = multiPropertiesAndSortsCriteria(properties, sorts);
        return (T) c.uniqueResult();
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.（优先缓存中查找，该方法常用）
     */
    public T findUniqueInCache(Map<String, Object> properties, QueryParameter.Sort... sorts) {
        Criteria c = multiPropertiesAndSortsCriteria(properties, sorts);
        return (T) c.setCacheable(true).uniqueResult();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int execute(String hql, Object... values) {
        int result = 0;
        try {
            result = createQuery(hql, values).executeUpdate();
        } catch (Exception e) {
          
        }
        return result;
    }

    /**
     * 执行HQL进行批量修改/删除操作. (推荐)
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int execute(String hql, Map<String, ?> values) {
        int result = 0;
        try {
            result = createQuery(hql, values).executeUpdate();
        } catch (Exception e) {
          
        }

        return result;
    }



    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(Criterion... criterions) {
        return createCriteria(criterions).list();


    }

    /**
     * 按Criteria查询对象列表.(开启二级缓存)
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> findInCache(Criterion... criterions) {

        return createCriteria(criterions).setCacheable(true).list();


    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUnique(Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 按Criteria查询唯一对象.(使用二级缓存)
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUniqueInCache(Criterion... criterions) {

        return (T) createCriteria(criterions).setCacheable(true).uniqueResult();
    }

    /**
     * 根据Criterion条件创建Criteria.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 初始化对象.
     * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
     * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
     * 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    /**
     * Flush当前Session.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 为Query添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为Criteria添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 取得对象的主键名.
     */
    public String getIdName() {
        ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 取得指定Class实体的主键名.
     */
    public String getIdName(Class clazz) {
        ClassMetadata meta = sessionFactory.getClassMetadata(clazz);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     *
     * 此方法暂时未开放给BaseDAO，所以只能在DAO实现中调用
     *
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    protected boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue) {
        if (newValue == null || newValue.equals(oldValue)) {
            return true;
        }

        Map<String, Object> mapParams = new HashMap<String, Object>(1);
        mapParams.put(propertyName, newValue);
        List<T> result = find(mapParams);

        return (result.size() == 0);
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    protected Query createQuery(String hql, Object... values) {

        Query query = getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values 命名参数,按名称绑定.
     */
    protected Query createQuery(String hql, Map<String, ?> values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

}