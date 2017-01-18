package db;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Игорь on 22.11.2016.
 */
public class Dao {
    private Session session;

    public Dao(Session session) {
        this.session = session;
    }

    public Long insert(String name, int count1, int count2, int time,
                       float coef1, float coef2, float coef3, String url) throws HibernateException {
        return (Long) session.save(new DataSet(name, count1, count2, time, coef1, coef2, coef3, url));
    }

    public DataSet get(String url) throws HibernateException {
        Criteria criteria = session.createCriteria(DataSet.class);
        return ((DataSet) criteria.add(Restrictions.eq("url", url)).uniqueResult());
    }

    public void delete(long id) throws SQLException {
        Transaction transaction = null;
        transaction = session.beginTransaction();
        DataSet dataSet = (DataSet) session.get(DataSet.class, id);
        session.delete(dataSet);
        transaction.commit();
        session.close();
    }

    public void update(int time, int g1,  int g2, float c1, float c2, float c3, long id) throws HibernateException {
        Transaction transaction = null;
        transaction = session.beginTransaction();
        DataSet dataSet = (DataSet) session.get(DataSet.class, id);
        dataSet.setTime(time);
        dataSet.setCount1(g1);
        dataSet.setCount2(g2);
        dataSet.setCoef1(c1);
        dataSet.setCoef2(c2);
        dataSet.setCoef3(c3);
        session.update(dataSet);
        transaction.commit();
        session.close();
    }


    public ArrayList<Long> getIds() throws SQLException {

        ArrayList<Long> ids = new ArrayList<>();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        ArrayList<Long> list = (ArrayList<Long>) session.createQuery("from DataSet").list();

        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            DataSet dataSet = (DataSet) iterator.next();
            ids.add(dataSet.getId());
        }
        return ids;
    }
}
