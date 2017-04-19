import org.junit.Test;
import xyz.yanyj.util.BigDecimalUtils.BigDecimalUtil;
import xyz.yanyj.util.StringUtil.StringUtil;

import java.math.BigDecimal;

/**
 * Created by yanyj on 2017/4/13.
 */

public class BigDecimalUtilTest {

    @Test
    public void testSetScale() {
        System.err.println(StringUtil.isNullOrEmpty("1"));
    }

    @Test
    public void testAdd() {
        BigDecimal bigDecimal1 = new BigDecimal("1.55");
        BigDecimal bigDecimal2 = new BigDecimal("1.45");

        System.err.println(BigDecimalUtil.mul(bigDecimal1, bigDecimal2));
    }


}
