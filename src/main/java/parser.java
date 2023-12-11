import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser {

    private static Document getPage() throws IOException {
        String url = "https://pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception{
        Matcher matcher = pattern.matcher(stringDate);
        if(matcher.find()){
            return matcher.group();
        }
        throw new Exception("Can't exctract date from string");
    }

    public static void main(String[] args) throws IOException {
        Document page = getPage();
        Element tableWth = page.select("table[class=wt]").first();
        Elements names = tableWth.select("tr[class=wth]");
        Elements values = tableWth.select("tr[valign=top]");

        for (Element name: names) {
            String dateString=name.select("th[id=dt]").text();
            String date=getDateFromString(dateString);
            System.out.println( date + "     Явления Температура Давление Влажность Ветер");
        }
        System.out.println(names);

    }
}
