import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.yanyj.base.User;
import xyz.yanyj.ticket.service.UserService;
import xyz.yanyj.util.StringUtil.JsonMapper;
import xyz.yanyj.util.StringUtil.JsonUtil;

import java.util.logging.Logger;

/**
 * Created by yanyj on 2017/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
        "classpath:spring-hibernate.xml" })
public class TestUserService {

    private static final Logger LOGGER = Logger
            .getLogger(String.valueOf(TestUserService.class));

    @Autowired
    private UserService userService;

    @Test
    public void save() {

        LOGGER.info(userService.getUser("40287d815b7eef9b015b7eef9ebd0000").getUsername());
    }

    @Test
    public void getUserList() {

        LOGGER.info(JsonUtil.convertToJson(userService.getUserList()));
    }
}
