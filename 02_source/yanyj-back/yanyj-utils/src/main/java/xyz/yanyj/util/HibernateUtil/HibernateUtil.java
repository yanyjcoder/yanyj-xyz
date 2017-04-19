package xyz.yanyj.util.HibernateUtil;

import org.hibernate.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyj on 2017/4/19.
 */
public class HibernateUtil {

    public static Query setPamater(Query query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {

                if(params.get(key) instanceof List) {
                    query.setParameterList(key, (Collection) params.get(key));
                } else {
                    query.setParameter(key, params.get(key));
                }
            }
        }

        return query;
    }
}
