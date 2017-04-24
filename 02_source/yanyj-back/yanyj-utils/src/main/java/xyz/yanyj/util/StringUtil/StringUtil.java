package xyz.yanyj.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
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


    public static StringBuilder getSelectHeader(String... columns) {

        StringBuilder sb = new StringBuilder("SELECT");
        if(columns.length > 0) {
            sb.append(" ");
            sb.append(StringUtils.join(columns, ","));
        } else {
            sb.append( "*");
        }

        return sb;
    }
}
