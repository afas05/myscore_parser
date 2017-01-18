import com.gargoylesoftware.htmlunit.html.HtmlPage;
import db.DBserv;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Игорь on 22.11.2016.
 */
public class Main {
    public static void main(String[] args) {

        Parser parser = new Parser();
        DBserv dBserv = new DBserv();

        HtmlPage mainPage = parser.getPage("http://m.myscore.com.ua/?s=2");
        ArrayList<Integer> time = parser.parseTime(mainPage);
        int[] [] count = parser.parseCount(mainPage);
        ArrayList<String> url = parser.parseUrl(mainPage);

        //all LIVE matchs
        ArrayList<Long> exsist = new ArrayList<>();

        for (int i = 0; i < url.size(); i++) {
            //достаем данные для первого матча
            HtmlPage page = parser.getPage(url.get(i));
            String name = parser.getName(page);
            ArrayList<Float> coef = parser.getKoef(page);

            //cheking
            if(dBserv.get(url.get(i))==null) {
                try {
                    //добавляем в БД
                    dBserv.insert(name, count[0][i], count[1][i], time.get(i), coef.get(0), coef.get(1), coef.get(2), url.get(i));
                } catch (IndexOutOfBoundsException e) {
                    dBserv.insert(name, count[0][i], count[1][i], time.get(i), 0, 0, 0, url.get(i));
                }
                exsist.add(dBserv.get(url.get(i)).getId());
                System.out.println("add "+ name+" "+exsist.size());
            } else {
                //update
                long id = dBserv.get(url.get(i)).getId();
                try {
                    dBserv.update(time.get(i), count[0][i], count[1][i], coef.get(0), coef.get(1), coef.get(2), id);
                } catch (IndexOutOfBoundsException e) {
                    dBserv.update(time.get(i), count[0][i], count[1][i], 0, 0, 0, id);
                }
                exsist.add(id);
                System.out.println("upd "+ dBserv.get(url.get(i)).getName()+" "+exsist.size());
            }

        }
        try {
            //get all match from DB LIVE + ENDED
            ArrayList<Long> matchsFromDB = dBserv.getIds();
            //get all ENDED matchs
            ArrayList<Long> toDel = new ArrayList<>(matchsFromDB);
            for (int i = 0; i < matchsFromDB.size(); i++) {
                for (int o = 0; o < exsist.size(); o++) {
                    long l1 = matchsFromDB.get(i);
                    long l2 = exsist.get(o);
                    if (l1 == l2) {
                        toDel.remove(l1);
                        break;
                    }
                }
            }

            //delet ENDED matches
            if (!toDel.isEmpty()) {
                for (int i = 0; i < toDel.size(); i++) {
                    dBserv.delete(toDel.get(i));
                }
            }

        } catch (SQLException e) {
            System.out.println("SQl exep");
        }

        dBserv.closeFactory();
    }
}
