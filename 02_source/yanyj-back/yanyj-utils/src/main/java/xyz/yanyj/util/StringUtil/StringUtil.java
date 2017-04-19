package xyz.yanyj.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanyj on 2017/4/19.
 */
public class StringUtil {

    public static String removeSelect(String sql) {
        int beginPos = sql.toLowerCase().indexOf("from");
        return sql.substring(beginPos);
    }

    public static String removeOrders(String sql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*?", 2);
        Matcher m = p.matcher(sql);

        String group;
        for(group = null; m.find(); group = m.group(m.groupCount())) {
            ;
        }

        return group == null?sql:sql.substring(0, sql.lastIndexOf(group));
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.equals("");
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
