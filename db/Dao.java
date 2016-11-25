package db;

import db.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public void updateTime(int time, long id) throws SQLException {
        executor.execUpdate("UPDATE `parserdb`.`matches` SET `time`='"+time +"' WHERE `idmatches`='"+id+"';");
    }

    public void updateCount(int g1, int g2, long id) throws SQLException {
        executor.execUpdate("UPDATE `parserdb`.`matches` SET `goal1st`='"+g1+"', `goal2nd`='"+g2+"' WHERE `idmatches`='"+id+"';");
    }

    public void updateCoef(float c1, float c2, float c3, long id) throws SQLException {
        executor.execUpdate("UPDATE `parserdb`.`matches` SET `coef1`='"+c1+"', `coef2`='"+c2+"', `coef3`='"+c3+"' WHERE `idmatches`='"+id+"';");
    }

    public ArrayList<Long> getIds() throws SQLException {
        ArrayList<Long> list = new ArrayList<>();
        return executor.execQuery("SELECT idmatches FROM parserdb.matches;", resultSet -> {
            while (resultSet.next()) {
                list.add(resultSet.getLong(1));
            }
            return list;
        });
    }
}
