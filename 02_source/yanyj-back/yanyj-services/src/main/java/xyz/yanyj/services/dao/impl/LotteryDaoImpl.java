package xyz.yanyj.services.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import xyz.yanyj.base.HibernateDAO;
import xyz.yanyj.services.dao.LotteryDao;
import xyz.yanyj.entity.ticket.Lottery;
import xyz.yanyj.util.DateUtil.DateUtil;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;
import xyz.yanyj.util.StringUtil.StringUtil;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by yanyj on 2017/4/24.
 */
@Repository("lotteryDao")
public class LotteryDaoImpl extends HibernateDAO<Lottery, String> implements LotteryDao {

    @Override
    public Page<Lottery> getLotteryList(QueryParameter queryParameter, Map<String, Object> params, String... columns) {

        String status = (String)params.get("status");
        String lotteryType = (String)params.get("lotteryType");
        String startDate = (String)params.get("startDate");
        String endDate = (String)params.get("endDate");
        String red = (String)params.get("red");
        String black = (String)params.get("black");
        String draw = (String)params.get("draw");

//        StringBuilder hql = StringUtil.getSelectHeader(columns);
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM Lottery lottery WHERE 1 = 1");

        //彩单状态
        if(!StringUtil.isNullOrEmpty(status)) {
            hql.append(" AND lottery.status IN (:status)");
            params.put("status", Arrays.asList(status.split(",")));
        }

        //彩单类型
        if(!StringUtil.isNullOrEmpty(lotteryType)) {
            hql.append(" AND lottery.lotteryType IN (:lotteryType)");
            params.put("lotteryType", Arrays.asList(lotteryType.split(",")));
        }

        //彩单开始时间
        if(!StringUtil.isNullOrEmpty(startDate)) {
            hql.append(" AND lottery.betDate > :startDate");
            params.put("startDate", DateUtil.parseSimple(startDate));
        }

        //彩单结束时间
        if(!StringUtil.isNullOrEmpty(endDate)) {
            hql.append(" AND lottery.betDate < :endDate");
            params.put("endDate", DateUtil.parseSimple(endDate));
        }

        //红单
        if(!StringUtil.isNullOrEmpty(red)) {
            hql.append(" AND lottery.profit > 0");
        }

        //黑单
        if(!StringUtil.isNullOrEmpty(black)) {
            hql.append(" AND lottery.profit < 0");
        }

        //走水
        if(!StringUtil.isNullOrEmpty(draw)) {
            hql.append(" AND lottery.profit = 0");
        }


        hql.append(" ORDER BY lottery.betDate DESC");


        return findPage(queryParameter, hql.toString(), params);

    }


    @Override
    public void saveLottery(Lottery lottery) {
        save(lottery);
    }

    @Override
    public Lottery getLottery() {
        return get("2c9081c95b9e15d6015b9e15da270000");
    }
}
