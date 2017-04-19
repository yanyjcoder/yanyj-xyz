package xyz.yanyj.util.CollectionUtil;

import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.transform.ResultTransformer;

public class ResultToMap implements ResultTransformer {
    private static final long serialVersionUID = -6126968741247252813L;

    public ResultToMap() {
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        LinkedHashMap result = new LinkedHashMap(aliases.length);

        for(int i = 0; i < aliases.length; ++i) {
            result.put(aliases[i], tuple[i]);
        }

        return result;
    }

    public List transformList(List collection) {
        return collection;
    }
}
