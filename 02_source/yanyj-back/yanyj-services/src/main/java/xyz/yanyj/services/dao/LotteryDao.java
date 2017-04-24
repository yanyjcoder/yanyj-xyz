package xyz.yanyj.services.dao;

import xyz.yanyj.entity.ticket.Lottery;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;

import java.util.Map;

/**
 * Created by yanyj on 2017/4/24.
 */
public interface LotteryDao {

    Page<Lottery> getLotteryList(QueryParameter queryParameter, Map<String, Object> params, String... columns);

    void saveLottery(Lottery lottery);

    Lottery getLottery();
}
