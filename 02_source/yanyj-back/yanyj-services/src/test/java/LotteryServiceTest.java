import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.yanyj.entity.ticket.Lottery;
import xyz.yanyj.services.service.LotteryService;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;
import xyz.yanyj.util.StringUtil.JsonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyj on 2017/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
        "classpath:spring-hibernate.xml" })
public class LotteryServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void testSave() {
        Lottery lottery = new Lottery();
        lottery.setBetDate(new Date());
        lotteryService.saveLottery(lottery);

    }


    @Test
    public void test() {
//
//        System.err.println(lotteryService.getLottery());

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("endDate", "2017-5-2");
       Page<Lottery> lotteryPage = lotteryService.getLotteryList(new QueryParameter(), map, new String[] {});

       System.err.println(JsonUtil.convertToJson(lotteryPage));
    }
}
