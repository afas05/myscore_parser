import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Игорь on 22.11.2016.
 */
public class Parser {

    public HtmlPage getPage(String url) {

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
        int i;

        do {
            i = 0;

            try {
                HtmlPage page = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(1000);
                return page;
            } catch (Exception c) {
                i = 1;
                return null;
            }

        } while (i > 0);

    }

    public ArrayList<Integer> parseTime(HtmlPage page) {

        List<DomElement> time = (List<DomElement>) page.getByXPath(".//*[@id='score-data']/span");
        ArrayList<Integer> time1 = new ArrayList<>();
        for(DomElement el: time) {
           String s = el.getTextContent();
           try {
               if (s.length() < 3) {
                   time1.add(Integer.parseInt(s.substring(0, 1)));
               } else {
                   time1.add(Integer.parseInt(s.substring(0, 2)));
               }
           } catch (NumberFormatException e) {
               time1.add(100);
           } catch (NullPointerException n) {
               time1.add(-1);
           }
        }
        return time1;
    }

    public int[] [] parseCount(HtmlPage page) {
        List<DomElement> link = (List<DomElement>) page.getByXPath(".//*[@id='score-data']/a");

        int[] [] count = new int[2][link.size()];

        for(int i = 0; i < link.size(); i++) {

            String[] s1 = link.get(i).getTextContent().split(":");
            try {
                count[0][i] = Integer.parseInt(s1[0]);
                count[1][i] = Integer.parseInt(s1[1]);
            } catch (Exception e) {
                count[0][i] = 99;
                count[1][i] = 99;
            }

        }
        return count;
    }

    public ArrayList<String> parseUrl(HtmlPage page) {
        List<DomElement> link = (List<DomElement>) page.getByXPath(".//*[@id='score-data']/a");
        ArrayList<String> links = new ArrayList<>();
        for(DomElement el: link) {
            links.add("http://m.myscore.com.ua" + el.getAttribute("href").toString());

        }
        return links;
    }

    public ArrayList getKoef(HtmlPage page1) {

        ArrayList<Float> coef = new ArrayList<>();
        try {
            List<DomElement> dom = (List<DomElement>) page1.getByXPath(".//*[@id='main']/p[3]/a");
            for (DomElement el : dom) {
                System.out.println(el.getTextContent());
                coef.add(Float.parseFloat(el.getTextContent()));
            }
            return coef;
        } catch (Exception e) {
            List<DomElement> dom = (List<DomElement>) page1.getByXPath(".//*[@id='main']/p[2]/a");
            for (DomElement el : dom) {
                System.out.println(el.getTextContent());
                coef.add(Float.parseFloat(el.getTextContent()));
            }
            return coef;
        }
    }

    public String getName(HtmlPage page1) {
        List<DomElement> dom = (List<DomElement>) page1.getByXPath(".//*[@id='main']/h3");
        String name = dom.get(0).getTextContent();

        return name;
    }
}
