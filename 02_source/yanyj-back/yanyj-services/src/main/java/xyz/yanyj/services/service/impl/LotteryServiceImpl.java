package xyz.yanyj.services.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.yanyj.base.HibernateDAO;
import xyz.yanyj.services.service.LotteryService;
import xyz.yanyj.entity.ticket.Lottery;
import xyz.yanyj.services.dao.LotteryDao;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;

import java.util.Map;

/**
 * Created by yanyj on 2017/4/24.
 */

@Service("lotteryService")
public class LotteryServiceImpl extends HibernateDAO<Lottery, String> implements LotteryService {

    @Autowired
    public LotteryDao lotteryDao;

    @Override
    public Page<Lottery> getLotteryList(QueryParameter queryParameter, Map<String, Object> params, String... columns) {

        return lotteryDao.getLotteryList(queryParameter, params, columns);
    }

    @Override
    public boolean saveLottery(Lottery lottery) {
        lotteryDao.saveLottery(lottery);

        return true;
    }

    @Override
    public Lottery getLottery() {
        return lotteryDao.getLottery();
    }
}
