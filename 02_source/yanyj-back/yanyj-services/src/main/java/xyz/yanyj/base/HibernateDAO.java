package xyz.yanyj.base;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;
import xyz.yanyj.util.StringUtil.StringUtil;
import xyz.yanyj.util.CollectionUtil.ResultToMap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 *
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 *
 * @param <T> DAO操作的对象类型
 * @param <Id> 主键类型
 *
 * @author liupj
 */
@SuppressWarnings("all")
public class HibernateDAO<T, Id extends Serializable> extends SimpleHibernateDAO<T, Id> implements BaseDAO<T, Id> {



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
    @Override
    public Page<T> findPage(QueryParameter queryParameter, String hql, Object... values) {
        return findPageInterval(queryParameter, hql, false, values);
    }

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
    @Override
    public Page<T> findPageInCache(QueryParameter queryParameter, String hql, Object... values) {
        return findPageInterval(queryParameter, hql, true, values);
    }

    private Page<T> findPageInterval(QueryParameter queryParameter, String hql, boolean isCacheable, Object... values) {

        int pageNo = queryParameter.getPageNo();
        int pageSize = queryParameter.getPageSize();
        Page<T> page = new Page<T>(pageSize, true);
        if (pageNo <= 0) {
            pageNo = 1;
        }
        page.setPageNo(pageNo);
        page.setSortList(queryParameter.getSortList());

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalRows((int)totalCount);
            page.setTotalCount((int)totalCount);
        }

        if(queryParameter.getSortList().size() > 0) {
            hql = setOrderParameterToHql(hql, queryParameter);
        }

        Query q = createQuery(hql, values);

        if (isCacheable) {
            q.setCacheable(true);
        }

        setPageParameterToQuery(q, page);

        List result = q.list();
        page.setResult(result);

        return page;
    }


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
    @Override
    public Page<T> findPage(QueryParameter queryParameter, String hql, Map<String, ?> values) {
        return findPageInterval(queryParameter, hql, false, values);
    }


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
    @Override
    public Page<T> findPageInCache(QueryParameter queryParameter, String hql, Map<String, ?> values) {
        return findPageInterval(queryParameter, hql, true, values);
    }

    private Page<T> findPageInterval(QueryParameter queryParameter, String hql, boolean isCacheable, Map<String, ?> values) {

        int pageNo = queryParameter.getPageNo();
        int pageSize = queryParameter.getPageSize();
        Page<T> page = new Page<T>(pageSize, true);
        if (pageNo <= 0) {
            pageNo = 1;
        }
        page.setPageNo(pageNo);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalRows((int)totalCount);
            page.setTotalCount((int)totalCount);
        }

        if(queryParameter.getSortList().size() > 0) {
            hql = setOrderParameterToHql(hql, queryParameter);
        }

        Query q = createQuery(hql, values);
        setPageParameterToQuery(q, page);

        List result;
        if (isCacheable) {
            result = q.setCacheable(true).list();
        } else {
            result = q.list();
        }

        page.setResult(result);
        return page;
    }

    /**
     * sql查询列表（?号占位符）
     * @param sql
     * @param values
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySql(String sql, Object... values) {
        try {
            SQLQuery q = createSqlQuery(sql, values);

            List<Map<String, Object>> list = q.setResultTransformer(
                    new ResultToMap()).list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    /**
     * sql查询列表（命名占位符, 推荐）
     * @param sql
     * @param values
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySql(String sql, Map<String, Object> values) {

        SQLQuery q = createSqlQuery(sql, values);
        return q.setResultTransformer(new ResultToMap()).list();
    }


    /**
     * sql语句分页查询
     * @param queryParameter 查询参数
     * @param sql
     * @param values 可变数组，与sql中？占位符的顺序一一对应
     * @return
     */
    @Override
    public Page<T> findPageBySql(QueryParameter queryParameter, String sql, Object... values) {
        return findBySqlInternel(queryParameter, sql, values);
    }

    /**
     * sql语句分页查询
     * @param queryParameter 查询参数
     * @param sql
     * @param values java.util.Map
     * @return
     */
    @Override
    public  Page<T> findPageBySql(QueryParameter queryParameter, String sql, Map<String, ?> values) {
        return findBySqlInternel(queryParameter, sql, values);
    }

    private Page<T> findBySqlInternel(QueryParameter queryParameter, String sql, Object obj) {

        boolean isMap = false;
        if (obj instanceof Map) {
            isMap = true;
        }


        Page<T> page = new Page<T>();
        page.setPageNo(queryParameter.getPageNo());
        page.setPageSize(queryParameter.getPageSize());
        page.setAutoCount(queryParameter.isAutoCount());
        page.setSortList(queryParameter.getSortList());

        if (page.isAutoCount()) {

            String totalCountSql = prepareCountHql(sql);

            SQLQuery cq;
            if(isMap) {
                cq = createSqlQuery(totalCountSql, (Map)obj);
            } else {
                cq = createSqlQuery(totalCountSql, (Object[])obj);
            }


            Object o = cq.uniqueResult();
            Long count = 0L;
            if(o instanceof Long){
                count = (Long) o;
            }else if(o instanceof BigDecimal){
                BigDecimal b = (BigDecimal) o;
                count = b.longValue();
            }else if(o instanceof BigInteger){
                BigInteger b = (BigInteger) o;
                count = b.longValue();
            }
            page.setTotalCount(count.intValue());

        }
        if (page.getPageNo() > page.getTotalPages()) {
            page.setPageNo(1);
        }

        SQLQuery q;
        if(isMap) {
            q = createSqlQuery(sql, (Map)obj);
        } else {
            q = createSqlQuery(sql, (Object[])obj);
        }

        q.addEntity(entityClass);

        if (page.isFirstSetted()) {
            q.setFirstResult(page.getFirst());
        }

        if (page.isPageSizeSetted()) {
            q.setMaxResults(page.getPageSize());
        }

        page.setResult(q.list());
        return page;

    }


    /**
     * sql查询列表（？号占位符）
     * @param clazz
     * @param sql
     * @param values
     * @param <T>
     * @return 实体列表
     */
    @Override
    public <T> List<T> findBySql(Class<T> clazz, String sql, Object... values) {

        return createSqlQuery(sql, values).addEntity(clazz).list();

    }

    /**
     * sql查询列表(命名占位符，推荐)
     * @param clazz
     * @param sql
     * @param values
     * @param <T>
     * @return 实体列表
     */
    @Override
    public <T> List<T> findBySql(Class<T> clazz, String sql, Map<String, ?> values) {

        return createSqlQuery(sql, values).addEntity(clazz).list();

    }


    /**
     * 执行SQL进行修改/删除操作.
     *
     * @param values
     *            数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    @Override
    public int executeSql(String sql, Object... values) {
        return createSqlQuery(sql, values).executeUpdate();
    }

    /**
     * 执行SQL进行修改/删除操作.(推荐)
     *
     * @param values
     *            命名参数,按名称绑定.
     * @return 更新记录数.
     */
    @Override
    public int executeSql(String sql, Map<String, ?> values) {
        return createSqlQuery(sql, values).executeUpdate();
    }




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
    @Override
    public int executeBatchSql(String sql, List<Map<String, Object>> dataList, String... columns) {

        PreparedStatement pstmt = null;
        Map<String, Object> map = null;
        try {

            pstmt = SessionFactoryUtils.getDataSource(sessionFactory).getConnection().prepareStatement(sql);

            for (Map<String, Object> aDataList: dataList) {
                map = aDataList;

                int j = 1;
                for (String key : columns) {
                    pstmt.setObject(j, map.get(key));
                    j++;
                }

                pstmt.addBatch();
            }

            int[] counts = pstmt.executeBatch();
            logger.info("====================当前sql = [" + sql +  "] execute counts : " + counts.length + " ===================");

            return counts.length;
        } catch (Exception e) {
            logger.error("sql 执行失败， 当前sql = [" + sql +  "], map = [" + map + "]", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);

            } finally {
                if (pstmt != null) {
                    pstmt = null;
                }
            }
        }
    }

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
    @Override
    public int executeBatchSql(String sql, List<List<String>> dataList) {
        PreparedStatement pstmt = null;
        List<String> data = null;
        try {
            pstmt = SessionFactoryUtils.getDataSource(sessionFactory).getConnection().prepareStatement(sql);

            for(int index = 0; index < dataList.size(); index ++) {
                data = dataList.get(index);
                int j = 1;
                for (String value : data) {
                    pstmt.setObject(j, value);
                    j++;
                }

                pstmt.addBatch();
            }

            int[] counts = pstmt.executeBatch();


            return counts.length;
        } catch (Exception e) {
            logger.error("sql 执行失败， 当前sql = [" + sql +  "], data = [" + data + "]", e);

        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return 0;

            } finally {
                if (pstmt != null) {
                    pstmt = null;
                }
            }
        }

        return 0;
    }

    /**
     * 批量执行sql(insert/update/delete)
     * @param sqlList 拼接好的待执行sql语句
     * @return
     */
    @Override
    public int executeBatchSql(List<String> sqlList) {
        Statement stmt = null;
        String currentSql = null;

        try {
            stmt = SessionFactoryUtils.getDataSource(sessionFactory).getConnection().createStatement();
            for(int index = 0; index < sqlList.size(); index ++) {
                currentSql = sqlList.get(index);
                stmt.addBatch(currentSql);
            }

            int[] counts = stmt.executeBatch();

            return counts.length;
        } catch (Exception e) {
            logger.error("sql 执行失败， 当前sql = [" + currentSql +  "]", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);

            } finally {
                if (stmt != null) {
                    stmt = null;
                }
            }
        }
    }

    /*
     * 在HQL的后面添加分页参数定义的orderBy, 辅助函数.
     */
    private String setOrderParameterToHql(String hql, QueryParameter queryParameter) {
        StringBuilder builder = new StringBuilder(hql);
        builder.append(" ORDER BY ");

        for (QueryParameter.Sort orderBy : queryParameter.getSortList()) {
            builder.append(orderBy.toString() + ", ");
        }

        return builder.substring(0, builder.length() - 2);
    }

    /*
     * 设置分页参数到Query对象,辅助函数.
     */
    private Query setPageParameterToQuery(Query q, Page page) {
        if (page.isFirstSetted()) {
            q.setFirstResult(page.getFirst());
        }

        q.setMaxResults(page.getPageSize());

        return q;
    }

    /*
     * 设置分页参数到Criteria对象,辅助函数.
     */
    private Criteria setPageRequestToCriteria(Criteria c, Page page) {

        c.setFirstResult(page.getFirst());
        c.setMaxResults(page.getPageSize());

        if (page.isOrderBySetted()) {
            for (Page.Sort sort : page.getSortList()) {
                if (sort.isAsc()) {
                    c.addOrder(Order.asc(sort.getFieldName()));
                } else {
                    c.addOrder(Order.desc(sort.getFieldName()));
                }
            }
        }
        return c;
    }

    /*
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    private long countHqlResult(String hql, Object... values) {
        String countHql = null;
        
            countHql = prepareCountHql(hql);
            return (Long) createQuery(countHql, values).uniqueResult();

    }

    /*
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    private long countHqlResult(String hql, Map<String, ?> values) {
        String countHql = null;

    
            countHql = prepareCountHql(hql);
            return (Long) createQuery(countHql, values).uniqueResult();
      
    }

    private String prepareCountHql(String orgHql) {
        return "select count (*) " + StringUtil.removeSelect(StringUtil.removeOrders(orgHql));
    }

    /**
     * 创建SqlQuery
     * @param sql
     * @param values
     * @return
     */
    protected SQLQuery createSqlQuery(String sql, Object... values) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    sqlQuery.setParameter(i, values[i]);
                }
            }
        }
        return sqlQuery;
    }

    /**
     * 创建sqlQuery
     * @param sql
     * @param values
     * @return
     */
    protected SQLQuery createSqlQuery(String sql, Map<String, ?> values) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        if (values != null) {
            sqlQuery.setProperties(values);
        }
        return sqlQuery;
    }


}
