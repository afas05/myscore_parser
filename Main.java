import com.gargoylesoftware.htmlunit.html.HtmlPage;
import db.DBserv;
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
                //добавляем в БД
                dBserv.insert(name, count[0][i], count[1][i], time.get(i), coef.get(0), coef.get(1), coef.get(2), url.get(i));
                exsist.add(dBserv.get(url.get(i)).getId());
            } else {
                //update
                long id = dBserv.get(url.get(i)).getId();
                dBserv.update(time.get(i), count[0] [i], count[1] [i], coef.get(0), coef.get(1), coef.get(2), id);
                exsist.add(id);
            }

        }
        //get all match from DB LIVE + ENDED
        ArrayList<Long> matchsFromDB = dBserv.getIds();
        //get all ENDED matchs
        for(int i = 0; i < matchsFromDB.size(); i++) {
            for(int o = 0; o < exsist.size(); o++) {
                if(matchsFromDB.get(i) == exsist.get(o)) {
                    matchsFromDB.remove(i);
                }
            }
        }
        //delet ENDED matches
        if(!matchsFromDB.isEmpty()) {
            for(int i = 0; i < matchsFromDB.size(); i++) {
                dBserv.delete(matchsFromDB.get(i));
            }
        }

    }
}
