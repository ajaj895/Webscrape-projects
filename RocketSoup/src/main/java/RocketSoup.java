/*
 * This is a webscraping application that prints a rocket
 * launch calendar to a terminal. This calender may not
 * be accurate but is from spaceflightnow.com
 *
 * Author: Evan Colwell
 * Date Created: 10/21/2020
 * Last Updated: 10/27/2020
 *
 */

//Jsoup imports (handled through maven)
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;

public class RocketSoup {

    private static Document sfn;

    public static void main(String[] args){
        try{
            sfn = getSfn();
            LinkedList<String> schedule = getSchedule(sfn);
            printSchedule(schedule);
        } catch(IOException e){
            System.err.println("Error: Cannot connect to the Spaceflightnow website. Please try again later...");
        }
    }

    /**
     * Gets the mission of the rocket, as a String
     * @param web A document that contains the website
     * @return A String that contains the mission
     */
    public static String getMission(Element web){

        String launchDate = web.select("span.launchdate").text();
        String mission = web.select("span.mission").text();
        String schedule = launchDate+"\n"+mission;
        return schedule;

    }

    /**
     * Gets the data of the mission from the rocket
     * @param web A document that contains the website
     * @return A String that contains the data of the mission
     */
    public static String getMissionData(Element web){

        String missionData = web.select("div.missiondata").text() + "\n";
        return missionData;

    }

    /**
     * Gets the mission description of the rocket
     * @param web A document that contains the website
     * @return A String that contains the description of the mission
     */
    public static String getDescription(Element web){

        String missionDescription = web.select("div.missdescrip").text() + "\n";
        return missionDescription;

    }

    /**
     * Gets a list of the current schedule
     * @param web A document that contains the website
     * @return A LinkedList that contains Strings with the schedule
     */
    public static LinkedList<String> getSchedule(Document web){

        LinkedList<String> schedule = new LinkedList<String>();
        int i = 1;
        for(Element launch : web.select("div.entry-content.clearfix div.datename, div.missiondata, div.missdescrip")){
            if(schedule.isEmpty() || schedule.size() % 3 == 0){
                schedule.addLast(getMission(launch));
            } else if(schedule.size() % 3 == 1){
                schedule.addLast(getMissionData(launch));
            } else {
                schedule.addLast(getDescription(launch));
            }
        }
        return schedule;
    }

    /**
     * Prints the schedule
     * @param schedule LinkedList of Strings
     */
    public static void printSchedule(LinkedList<String> schedule){
        for(int i = 0; i < schedule.size(); i++){
            System.out.println(schedule.get(i));
        }
    }

    /**
     * getSfn Returns the SpaceFlightNow website as a JSoup document
     * @return A JSoup document with the SpaceFLightNow website in html and css code
     * @throws IOException Throws error if website can not be reached
     */
    public static Document getSfn() throws IOException {
        return getWebsite();
    }

    // Gets the website
    private static Document getWebsite() throws IOException{
        return Jsoup.connect("https://spaceflightnow.com/launch-schedule/").get();
    }
}
