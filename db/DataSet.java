package db;

/**
 * Created by Игорь on 22.11.2016.
 */
public class DataSet {
    private long id;
    private String name;
    private int count1;
    private int count2;
    private int time;
    private float coef1;
    private float coef2;
    private float coef3;
    private String url;

    public DataSet(long id, String name, int count1, int count2, int time, float coef1, float coef2, float coef3, String url) {
        this.id = id;
        this.name = name;
        this.count1 = count1;
        this.count2 = count2;
        this.time = time;
        this.coef1 = coef1;
        this.coef2 = coef2;
        this.coef3 = coef3;
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
        return coef1;
    }

    public float getCoef2() {
        return coef2;
    }

    public float getCoef3() {
        return coef3;
    }

    public String getUrl() {
        return url;
    }
}
