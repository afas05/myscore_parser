import com.gargoylesoftware.htmlunit.html.DomElement;
import db.DBserv;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Игорь on 22.11.2016.
 */
public class Main {
    public static void main(String[] args) {

        Parser parser = new Parser();
        DBserv dBserv = new DBserv();

        ArrayList<Integer> time = parser.parseTime(parser.getPage("http://m.myscore.com.ua/?s=2"));
        int[] [] count = parser.parseCount(parser.getPage("http://m.myscore.com.ua/?s=2"));
        ArrayList<String> url = parser.parseUrl(parser.getPage("http://m.myscore.com.ua/?s=2"));

        for (int i = 0; i < url.size(); i++) {
            //достаем данные для первого матча
            String name = parser.getName(parser.getPage(url.get(i)));
            System.out.println(name);
            ArrayList<Float> coef = parser.getKoef(parser.getPage(url.get(i)));

            System.out.println(coef);
            // добавляем в БД

        }

    }
}
