import org.junit.Test;
import xyz.yanyj.util.BigDecimalUtils.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * Created by yanyj on 2017/4/13.
 */

public class BigDecimalUtilTest {

    @Test
    public void testSetScale() {
        BigDecimal bigDecimal = new BigDecimal("1.23456");
        System.err.println(BigDecimalUtil.setScale(bigDecimal, 0));
    }

    @Test
    public void testAdd() {
        BigDecimal bigDecimal1 = new BigDecimal("1.55");
        BigDecimal bigDecimal2 = new BigDecimal("1.45");

        System.err.println(BigDecimalUtil.mul(bigDecimal1, bigDecimal2));
    }
}
