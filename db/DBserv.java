package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Игорь on 23.11.2016.
 */
public class DBserv {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final SessionFactory sessionFactory;

    public DBserv() {
        Configuration configuration = getMysqlConf();
        sessionFactory = createSessionfactory(configuration);
    }

    private Configuration getMysqlConf() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(DataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        configuration.setProperty("hibernate.connection.url", "jdbc:sqlserver://localhost:1433;databaseName=GoldStandartDB;");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "1111");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionfactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public DataSet get(String url) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Dao dao = new Dao(session);
        DataSet dataSet = dao.get(url);
        transaction.commit();
        session.close();
        return dataSet;
    }

    public void insert(String name, int count1, int count2, int time,
                       float coef1, float coef2, float coef3, String url) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Dao dao = new Dao(session);
            long id = dao.insert(name, count1, count2, time, coef1, coef2, coef3, url);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            System.out.println("Hib exep insert");
        }
    }

    public void update(int time, int g1, int g2, float c1, float c2, float c3, long id) {
        Session session = sessionFactory.openSession();
        Dao dao = new Dao(session);
        dao.update(time, g1, g2, c1, c2, c3, id);
    }

    public ArrayList<Long> getIds() throws SQLException {
        Session session = sessionFactory.openSession();
        Dao dao = new Dao(session);
        ArrayList<Long> longs = new ArrayList<>();
        return longs = dao.getIds();
    }

    public void delete(long id)  throws SQLException {
        Session session = sessionFactory.openSession();
        Dao dao = new Dao(session);
        dao.delete(id);
    }

    public void closeFactory() {
        sessionFactory.close();
    }
}
