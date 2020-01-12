import java.util.Scanner;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 *
 * @author Evan
 */
public class weatherSoup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        //MAP SIZE X=354 Y=274
        System.out.print("Enter an x value less than 355: ");
        int x = sc.nextInt();
        System.out.print("Enter a y value less than 275: ");
        int y = sc.nextInt();
        Document nws = Jsoup.connect("https://forecast.weather.gov/MapClick.php?x="+x+"&y="+y+"&site=ilx&zmx=&zmy=&map_x="+x+"&map_y="+y+"#").get();//https://forecast.weather.gov/MapClick.php?x=167&y=151&site=lsx&zmx=&zmy=&map_x=167&map_y=151# Macomb IL
        //the x and y are pixel counts are on the clickable map. while the site = lsx is the forcasting office.
        System.out.println(nws.title());
        System.out.println(getWeather(nws));
        
    }
    private static String weather(Document d) throws IOException{//gets the weather
        //final Document document = Jsoup.connect("https://forecast.weather.gov/MapClick.php?x=68&y=83&site=ilx&zmx=&zmy=&map_x=67&map_y=83#.XFOy5KpKhPY").get();
        String currentCond = d.select("p.myforecast-current").text();
        String temp = d.select("p.myforecast-current-lrg").text();//.text gets the value from the HTML
        String tempC = d.select("p.myforecast-current-sm").text();//temp in celcius
        String storeString = "Current Conditions "+currentCond+"\n"+"Temperature "+temp+" ("+tempC+")";
        for(Element row : d.select("div#current_conditions_detail.pull-left tr")){//collect current conditions, and last time updated.
            storeString=storeString+"\n"+row.text();
        }
        storeString = "Here are the current weather conditions near "+d.selectFirst("h2.panel-title").text()+"\n"+storeString;//change this string for localization.
        return storeString;
    }
    /**
     * This method gets data from the National Weather Service and returns information, in a String, for local weather data for Macomb, IL.
     * @return String - Returns formatted weather data in a String.
     * @throws IOException Throws an IOException if connection to the NWS website cannot be made.
     */
    public static String getWeather(Document d) throws IOException{//gets the weather() which gets the weather, totally not confusing
        return weather(d);
    }
    
}
