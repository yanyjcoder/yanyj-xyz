package xyz.yanyj.services.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.yanyj.entity.ticket.Lottery;
import xyz.yanyj.util.PageUtil.Page;
import xyz.yanyj.util.PageUtil.QueryParameter;

import java.util.Map;

/**
 * Created by yanyj on 2017/4/24.
 */
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
@Service
public interface LotteryService {

    Page<Lottery> getLotteryList(QueryParameter queryParameter, Map<String, Object> params, String... columns);
    boolean saveLottery(Lottery lottery);

    Lottery getLottery();
}
