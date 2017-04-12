import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.sun.security.auth.SolarisNumericUserPrincipal;
import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

import test.User;

public class BaseTest {

    @Test
    public void test1() throws SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException{

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        /****上面是配置准备，下面开始我们的数据库操作******/
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        User user = new User();
        user.setUsername("yanyj1");
        session.save(user);
        transaction.commit();//提交事务
    }
}
