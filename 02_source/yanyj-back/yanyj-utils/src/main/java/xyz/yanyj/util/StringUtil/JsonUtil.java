package xyz.yanyj.util.StringUtil;

/**
 * Created by yanyj on 2017/4/19.
 */
public class JsonUtil {

    public static String convertToJson(Object object, String... columns) {
        return new JsonMapper().convertToJson(object, columns);
    };
}
