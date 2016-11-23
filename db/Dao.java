package db;

import db.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Игорь on 22.11.2016.
 */
public class Dao {
    private Executor executor;

    public Dao(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void insert(String name, int count1, int count2, int time,
                       float coef1, float coef2, float coef3, String url) throws SQLException {
        executor.execUpdate("INSERT INTO `parserdb`.`matches` (`name`, `goal1st`, `goal2nd`, `time`, `coef1`, `coef2`, `coef3`, `url`) VALUES ('"+ name +"', '"+ count1 +"', '"+ count2+"', '"+ time + "', '"+coef1+"', '"+coef2+"', '"+coef3+"', '"+url+"');");
    }

    public DataSet get(String url) throws SQLException {
        return executor.execQuery("select * from parserdb.matches where url='" + url +"'", result -> {
            result.next();
            return new DataSet(result.getLong(1), result.getString(2), result.getInt(3), result.getInt(4), result.getInt(5), result.getFloat(6), result.getFloat(7), result.getFloat(8), result.getString(9));
        });
    }

    public void delete(long id) throws SQLException {
        executor.execUpdate("DELETE FROM `parserdb`.`matches` WHERE `idmatches`='"+ id +"';");
    }
}
