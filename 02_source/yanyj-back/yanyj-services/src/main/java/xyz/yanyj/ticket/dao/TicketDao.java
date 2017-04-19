package xyz.yanyj.ticket.dao;

import xyz.yanyj.ticket.Ticket;

import java.util.List;
import java.util.Map;

/**
 * Created by yanyj on 2017/4/19.
 */
public interface TicketDao {

    boolean saveTicket(Ticket ticket);

    List<Ticket> getTicketList(Map<String, Object> params);

    boolean updateTicket(Ticket ticket);

    boolean deleteTicket(String id);

    boolean deleteTickets(String[] ids);

}
