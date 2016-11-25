package db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Игорь on 23.11.2016.
 */
public class DBserv {
    private final Connection connection;

    public DBserv() {
        this.connection = getMySqlCon();
    }

    public static Connection getMySqlCon() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("parserdb?").          //db name
                    append("user=root&").          //login
                    append("password=1111");       //password


            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("return nulllll-------------------");
        return null;
    }

    public DataSet get(String url) {
        try {
            return (new Dao(connection).get(url));
        } catch (SQLException e) {
            return null;
        }
    }

    public void insert(String name, int count1, int count2, int time,
                       float coef1, float coef2, float coef3, String url) {
        try {
            connection.setAutoCommit(false);
            Dao dao = new Dao(connection);
            dao.insert(name, count1, count2, time, coef1, coef2, coef3, url);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("SQLEXEPTION-------------------");
            } catch (SQLException ignore) {
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException w) {}
        }
    }

    public void update(int time, int g1, int g2, float c1, float c2, float c3, long id) {
        try {
            connection.setAutoCommit(false);
            Dao dao = new Dao(connection);
            dao.updateTime(time, id);
            dao.updateCount(g1, g2, id);
            dao.updateCoef(c1, c2, c3, id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("SQLEXEPTION-------------------UPDATE");
            } catch (SQLException ignore) {
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException w) {}
        }
    }

    public ArrayList<Long> getIds() {
        try {
            return new Dao(connection).getIds();
        } catch (SQLException e) {
            System.out.println("Pacan k uspehu wel");
            return null;
        }
    }

    public void delete(long id) {
        try {
            connection.setAutoCommit(false);
            Dao dao = new Dao(connection);
            dao.delete(id);
            connection.commit();
        } catch (SQLException e) {
            System.out.print("delete failure");
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
