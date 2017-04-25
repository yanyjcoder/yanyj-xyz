package xyz.yanyj.controller.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.yanyj.services.service.LotteryService;
import xyz.yanyj.util.PageUtil.QueryParameter;
import xyz.yanyj.util.StringUtil.JsonUtil;

import java.util.Map;

/**
 * Created by yanyj on 2017/4/25.
 */
@Controller
@RequestMapping(value = "/ticket")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @RequestMapping(value = "/lotteries", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getLotteryList( @RequestBody Map<String, Object> params) {

        String result = JsonUtil.convertToJson(lotteryService.getLotteryList(new QueryParameter(), params, new String[] {}));

        return result;
    }

}
