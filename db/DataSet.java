package db;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Игорь on 22.11.2016.
 */
@Entity
@Table(name = "matches")
public class DataSet implements Serializable{
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "idmatches")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "count1")
    private int count1;

    @Column(name = "count2")
    private int count2;

    @Column(name = "time")
    private int time;

    @Column(name = "coef1")
    private BigDecimal coef1;

    @Column(name = "coef2")
    private BigDecimal coef2;

    @Column(name = "coef3")
    private BigDecimal coef3;

    @Column(name = "url")
    private String url;

    public DataSet(){}

    public DataSet(long id, String name, int count1, int count2, int time, float coef1, float coef2, float coef3, String url) {
        this.setId(id);
        this.setName(name);
        this.setCount1(count1);
        this.setCount2(count2);
        this.setTime(time);
        this.setCoef1(coef1);
        this.setCoef2(coef2);
        this.setCoef3(coef3);
        this.setUrl(url);
    }

    public DataSet(String name, int count1, int count2, int time, float coef1, float coef2, float coef3, String url) {
        this.setId(-1);
        this.setName(name);
        this.setCount1(count1);
        this.setCount2(count2);
        this.setTime(time);
        this.setCoef1(coef1);
        this.setCoef2(coef2);
        this.setCoef3(coef3);
        this.setUrl(url);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setCoef1(float coef1) {
        this.coef1 = BigDecimal.valueOf(coef1);
    }

    public void setCoef2(float coef2) {
        this.coef2 = BigDecimal.valueOf(coef2);
    }

    public void setCoef3(float coef3) {
        this.coef3 = BigDecimal.valueOf(coef3);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount1() {
        return count1;
    }

    public int getCount2() {
        return count2;
    }

    public int getTime() {
        return time;
    }

    public float getCoef1() {
        return coef1.floatValue();
    }

    public float getCoef2() {
        return coef2.floatValue();
    }

    public float getCoef3() {
        return coef3.floatValue();
    }

    public String getUrl() {
        return url;
    }
}
