package xyz.yanyj.base;



import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * DataAccess层接口，所有自定义DAO层接口应当继承此接口
 * @author liupj
 * @date 20170115
 * @param <T>
 * @param <Id>
 */
@SuppressWarnings("all")
public interface BaseDAO<T, Id extends Serializable> {
    /**
     * 取得当前Session.
     */
    Session getSession();


    /****************************** 增 ***********************************/
    /**
     * 保存新增或修改的对象.
     */
    void save(T entity);

    /**
     * 保存新增或修改的对象.
     */
    void save(List<T> entityList);

    /****************************** 删 ***********************************/
    /**
     * 删除对象.
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    void delete(T entity);

    /**
     * 批量删除实体集合
     * @param collection
     */
    void delete(Collection<T> collection);

    /**
     * 按id删除对象.
     */
    void delete(Id id);

    /**
     * 批量删除
     */
    void batchDelete(Id[] ids);
    /**
     * 批量删除
     * @param ids
     * @param clazz 需要删除的指定实体的Class对象
     */
    void batchDelete(Id[] ids, Class clazz);


    /****************************** 查找 **********************************/
    /**
     * 按id获取对象.
     */
    T get(Id id);

    /**
     * 按id列表获取对象列表.
     */
    List<T> get(Collection<Id> ids);

    /**
     * 根据hql和命名参数获取实体列表
     * @param charSequence
     * @param namedParameters
     * @return
     */
    List<T> find(CharSequence hql, Map<String, Object> namedParameters);


    /**
     * 根据hql和命名参数获取实体列表(二级缓存)
     * @param charSequence
     * @param namedParameters
     * @return
     */
    List<T> findInCache(CharSequence hql, Map<String, Object> namedParameters);

    /**
     * 根据hql和命名参数获取唯一实体
     * @param charSequence
     * @param namedParameters
     * @return
     */
    T findUnique(CharSequence hql, Map<String, Object> namedParameters);

    /**
     * 根据hql和命名参数获取唯一实体(二级缓存)
     * @param charSequence
     * @param namedParameters
     * @return
     */
    T findUniqueInCache(CharSequence hql, Map<String, Object> namedParameters);

    /**
     * 根据属性组和排序组查询列表（该方法常用）
     * 匹配方式为相等
     * @param properties
     * @param sorts
     * @return
     */
    List<T> find(Map<String, Object> properties, QueryParameter.Sort... sorts);

    /**
     * 根据属性组和排序组查询列表（优先缓存中查找，该方法常用）
     * 匹配方式为相等
     * @param properties
     * @param sorts
     * @return
     */
    List<T> findInCache(Map<String, Object> properties, QueryParameter.Sort... sorts);


    /**
     * 按属性查找唯一对象, 匹配方式为相等.（该方法常用）
     */
    T findUnique(Map<String, Object> properties, QueryParameter.Sort... sorts);

    /**
     * 按属性查找唯一对象, 匹配方式为相等.（优先缓存中查找，该方法常用）
     */
    T findUniqueInCache(Map<String, Object> properties, QueryParameter.Sort... sorts);

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    int execute(String hql, Object... values);

    /**
     * 执行HQL进行批量修改/删除操作. (推荐)
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    int execute(String hql, Map<String, ?> values);

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    List<T> find(Criterion... criterions);

    /**
     * 按Criteria查询对象列表.(开启二级缓存)
     *
     * @param criterions 数量可变的Criterion.
     */
    List<T> findInCache(Criterion... criterions);

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    T findUnique(Criterion... criterions);

    /**
     * 按Criteria查询唯一对象.(使用二级缓存)
     *
     * @param criterions 数量可变的Criterion.
     */
    T findUniqueInCache(Criterion... criterions);

    /**
     * 根据Criterion条件创建Criteria.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    Criteria createCriteria(Criterion... criterions);

    /**
     * 初始化对象.
     * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
     * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
     * 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    void initProxyObject(Object proxy);
    /**
     * Flush当前Session.
     */
    void flush();

    /**
     * 为Query添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    Query distinct(Query query);

    /**
     * 为Criteria添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    Criteria distinct(Criteria criteria);

    /**
     * 取得对象的主键名.
     */
    String getIdName();

    /**
     * 取得指定Class实体的主键名.
     */
    String getIdName(Class clazz);

    /**
     * 按HQL分页查询. (问号占位符形式，推荐使用命名占位符形式，即findPage(QueryParameter queryParameter, String hql, Map<String, ?> values)方法)
     *
     * @param queryParameter
     *            分页参数.
     * @param hql
     *            hql语句.
     * @param values
     *            数量可变的查询参数,按顺序绑定.
     *
     * @return 分页查询结果
     */
    public Page<T> findPage(QueryParameter queryParameter, String hql, Object... values);

    /**
     * 按HQL分页查询.(二级缓存)(问号占位符形式，推荐使用命名占位符形式，即findPage(QueryParameter queryParameter, String hql, Map<String, ?> values)方法)
     * @param queryParameter
     *          分页参数
     * @param hql
     *          hql语句
     * @param values
     *          数量可变的查询参数,按顺序绑定.
     * @return  分页查询结果
     */
    public Page<T> findPageInCache(QueryParameter queryParameter, String hql, Object... values);


    /**
     * 按HQL分页查询.(命名占位符形式)
     *
     * @param queryParameter
     *            分页参数.
     * @param hql
     *            hql语句.
     * @param values
     *            命名参数,按名称绑定.
     *
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public Page<T> findPage(QueryParameter queryParameter, String hql, Map<String, ?> values);


    /**
     * 按HQL分页查询.(命名占位符形式, 二级缓存)
     *
     * @param queryParameter
     *            分页参数.
     * @param hql
     *            hql语句.
     * @param values
     *            命名参数,按名称绑定.
     *
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    public Page<T> findPageInCache(QueryParameter queryParameter, String hql, Map<String, ?> values);


    /**
     * sql语句分页查询
     * @param queryParameter 查询参数
     * @param sql
     * @param values 可变数组，与sql中？占位符的顺序一一对应
     * @return
     */
    public Page<T> findPageBySql(QueryParameter queryParameter, String sql, Object... values);

    /**
     * sql语句分页查询
     * @param queryParameter 查询参数
     * @param sql
     * @param values java.util.Map
     * @return
     */
    public  Page<T> findPageBySql(QueryParameter queryParameter, String sql, Map<String, ?> values);

    /**
     * sql查询列表（?号占位符）
     * @param sql
     * @param values
     * @return
     */
    public List<Map<String, Object>> findBySql(String sql, Object... values);

    /**
     * sql查询列表（命名占位符, 推荐）
     * @param sql
     * @param values
     * @return
     */
    public List<Map<String, Object>> findBySql(String sql, Map<String, Object> values);

    /**
     * sql查询列表（？号占位符）
     * @param clazz
     * @param sql
     * @param values
     * @param <T>
     * @return 实体列表
     */
    public <T> List<T> findBySql(Class<T> clazz, String sql, Object... values);

    /**
     * sql查询列表(命名占位符，推荐)
     * @param clazz
     * @param sql
     * @param values
     * @param <T>
     * @return 实体列表
     */
    public <T> List<T> findBySql(Class<T> clazz, String sql, Map<String, ?> values);

    /**
     * 执行SQL进行修改/删除操作.
     *
     * @param values
     *            数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int executeSql(String sql, Object... values);

    /**
     * 执行SQL进行修改/删除操作.(推荐)
     *
     * @param values
     *            命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int executeSql(String sql, Map<String, ?> values);

    /**
     * 批量执行sql(insert/update/delete) (该方法常用)
     *
     * 注意：
     *
     *  1. sql 中只能采用“？”占位符的形式
     *  2. columns数组中字段与占位符一一对应，并且columns中字段必须在Map的key中存在
     *
     * @param sql 待执行sql
     * @param dataList 待入库数据列表
     * @param columns 与“？”占位符相匹配的字段名称
     */
    public int executeBatchSql(String sql, List<Map<String, Object>> dataList, String... columns);

    /**
     * 批量执行sql（insert/update/delete）
     * <p>
     * 注意：
     * <p>
     * 1. sql 中只能采用“？”占位符的形式
     * 2. List<String>值的顺序与sql中占位符的顺序完全一致
     *
     * @param sql      待执行sql
     * @param dataList 待入库数据列表
     */
    public int executeBatchSql(String sql, List<List<String>> dataList);

    /**
     * 批量执行sql(insert/update/delete)
     * @param sqlList 拼接好的待执行sql语句
     * @return
     */
    public int executeBatchSql(List<String> sqlList);

}
