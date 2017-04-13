package xyz.yanyj.util.BigDecimalUtils;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 * Created by yanyj on 2017/4/13.
 */
public class BigDecimalUtil {

    /**
     * 进位四舍五入，返回指定位数的值
     *
     * @author yanyj
     * @date 2017/4/13
     * @param value 要转换的数值
     * @param scale 返回位数
     * @return
     * @test  测试通过
     */
    public static BigDecimal setScale(BigDecimal value, int scale) {
        return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确加法计算的add方法
     *
     * @author yanyj
     * @date 2017/4/13
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal value1,BigDecimal value2){
        return value1.add(value2);
    }

    /**
     * 提供精确减法运算的sub方法
     * 
     * @author yanyj
     * @date 2017/4/13
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal value1,BigDecimal value2){
        return value1.subtract(value2);
    }
    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal value1,BigDecimal value2){
        return value1.multiply(value2);
    }
    /**
     * 提供精确的除法运算方法div
     *
     * @author yanyj
     * @date 2017/4/13
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static BigDecimal div(BigDecimal value1,BigDecimal value2,int scale) throws IllegalAccessException{
        //如果精确范围小于0，抛出异常信息
        if(scale<0){
            throw new IllegalAccessException("精确度不能小于0");
        }
        return value1.divide(value1, scale);
    }
}
