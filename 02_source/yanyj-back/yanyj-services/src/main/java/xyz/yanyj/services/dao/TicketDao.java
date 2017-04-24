package xyz.yanyj.services.dao;

import xyz.yanyj.entity.ticket.Lottery;

import java.util.List;
import java.util.Map;

/**
 * Created by yanyj on 2017/4/19.
 */
public interface TicketDao {

    boolean saveTicket(Lottery lottery);

    List<Lottery> getTicketList(Map<String, Object> params, String... columns);

    boolean updateTicket(Lottery lottery);

    boolean deleteTicket(String id);

    boolean deleteTickets(String[] ids);

}
