package xyz.yanyj.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yanyj on 2017/4/24.
 */
public class DateUtil {

    public static Date parse(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;
    }

    public static Date parseSimpleAll(String dateStr) {
        return parse(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseSimple(String dateStr) {
        return parse(dateStr, "yyyy-MM-dd");
    }
}
