import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser
{

    private static Document getPage() throws IOException
    {
        String url = "https://www.pogoda.spb.ru/";
        Document document = Jsoup.parse(new URL(url),3000);
        return document;
    }
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if(matcher.find())
        {
            return matcher.group();
        }
        throw new Exception("Ne mogli naiti");
    }
    private static void printFourValues(Elements values, int index)
    {
        for(int i = 0; i<4; i ++)
        {
            Element valueLine = values.get(index);
            for(Element td: valueLine.select("td"))
            {
                System.out.println(td.text() + "                    ");
            }
        }
    }



    public static void main(String[] args) throws Exception
    {
        Document page = getPage();
        Element tableWeather = page.select("table[class=wt]").first();
        Elements names = tableWeather.select("tr[class=wth]");
        Elements values = tableWeather.select("tr[valign=top]");
        int index = 0;
        for(Element name: names)
        {
            String dateString = name.select("th[id=dt]").text();
            String date = getDateFromString(dateString);
            System.out.println( date + "     Явления        Температура           Давление            Влажность          Ветер");
            printFourValues(values, index);
        }
    }
}
