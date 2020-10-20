/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nws.webscraper;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

/**
 *
 * @author Evan
 */
public class NWSWebscraper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Document document = Jsoup.connect("https://forecast.weather.gov/MapClick.php?x=68&y=83&site=ilx&zmx=&zmy=&map_x=67&map_y=83#").get();
        //Open the console in the web broser and hover over the elements you want an put what it says in the select("___")
        Scanner sc = new Scanner(System.in);
        int runTime = 1;//an hour
        boolean datalog = true;//this isn't really needed anymore
        
        //System.out.print("Would you like a datalog of the current weather? (yes/no): ");
        /*if (tester.equalsIgnoreCase("yes") || tester.equalsIgnoreCase("y")) {
            datalog = true;
        }Depreciated
        */
        System.out.print("Enter a number of hours to be run (If nothing is entered, the program will only collect 1 entry): ");
        int hoursIn = sc.nextInt();
        if(hoursIn > runTime) runTime = hoursIn;
        File weatherLog = new File("weatherLog.txt");

        FileWriter writer = null;
        try {
            if (weatherLog.exists()) {
                writer = new FileWriter(weatherLog, true);
            } else {
                writer = new FileWriter(weatherLog);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error could not find or open datalog. The program will now exit.");
            System.exit(1);
        }
        
        for (int i = 1; i <= runTime; i++) {
            document = Jsoup.connect("https://forecast.weather.gov/MapClick.php?x=68&y=83&site=ilx&zmx=&zmy=&map_x=67&map_y=83#.XFOy5KpKhPY").get();
            String currentCond = document.select("p.myforecast-current").text();
            String temp = document.select("p.myforecast-current-lrg").text();//.text gets the value from the HTML
            String tempC = document.select("p.myforecast-current-sm").text();//temp in celcius
            String detail = document.select("div#current_conditions_detail.pull-left").text();

            writer.append("Current Conditions: " + currentCond + " | ");
            writer.append("\n");
            writer.append("Temperature " + temp + ", " + tempC + " | ");
            writer.flush();
            System.out.println(currentCond);
            System.out.println("Temperature " + temp + ", " + tempC);
            //this for loop goes through each object in the table on the detailed information about the current conditions
            /*
            This for-loop checks each element in "div#current_conditions_details.pull-left"
            for an item called "tr" and then takes the text inside "tr"
            using .text().

            Element is part of jsoup.nodes

            the thing your searching for in the for loop has to be inside the
            " " marks when you do your document.select()
            using this for loop as an example,
            div#current_conditions_detail.pull-left  is the area being searched in
            between the <div> and </div> in the html code.
            The "tr" after that is the thing the for loop is going to each of,
            each tr has .text() called on it and returns only the text of what's in
            between <tr> and </tr>.   
             */
            for (Element row : document.select("div#current_conditions_detail.pull-left tr")) {//prints current conditions.
                System.out.println(row.text());//.text() gets rid of the html code
                writer.append(row.text() + " | ");

            }
            writer.flush();
            System.out.println();
            System.out.println("7 Day Forecast:\n");
            //for each "li.forecast-tombstone" in "div#seven-day-forecast-container
            for (Element tombstone : document.select("div#seven-day-forecast-container li.forecast-tombstone")) {
                System.out.println(tombstone.select("p.period-name").text() + ": " + tombstone.select("p.short-desc").text() + " | " + tombstone.select("p.temp.temp-low").text() + tombstone.select("p.temp.temp-high").text());

            }
            if(i == runTime){
                writer.close();//closes the writer for good
                break;
            }//this is so the program doesn't run an extra hour than needed.
            Thread.sleep((1000*60)*60);//throws a thing if interrupted.
        }
        // TODO code application logic here
    }

}
