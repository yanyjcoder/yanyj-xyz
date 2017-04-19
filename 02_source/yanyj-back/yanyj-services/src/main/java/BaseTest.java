import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;
import xyz.yanyj.ticket.Account;
import xyz.yanyj.ticket.Profit;
import xyz.yanyj.ticket.Ticket;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import java.math.BigDecimal;
import java.util.Date;

public class BaseTest {

    @Test
    public void test1() throws SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException{

//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//
//        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//
//        /****上面是配置准备，下面开始我们的数据库操作******/
//        Session session = sessionFactory.openSession();
//
//        Transaction transaction = session.beginTransaction();
//
//        Ticket ticket = new Ticket();
//       ticket.setFinalScore(new BigDecimal("1.23"));
//       ticket.setBetDate(new Date());
//        session.save(ticket);
//        transaction.commit();//提交事务
    }


    @Test
    public void test2() throws SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException{
//
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//
//        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//
//        /****上面是配置准备，下面开始我们的数据库操作******/
//        Session session = sessionFactory.openSession();
//
//        Transaction transaction = session.beginTransaction();
//
//        Ticket ticket;
//        ticket = session.get(Ticket.class,"40287d815b662ea8015b662ebb540000");

    }
}
