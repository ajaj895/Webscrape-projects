/*
 * Author: Evan Colwell
 * Date: 10/24/2020
 * Description: WeatherSoup is a very simple webscraper that returns
 * the weather data for Macomb, IL, directly to the terminal using
 * JSoup and Maven.
 */
package com.ajaj895.weathersoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WeatherSoup {

    private static Document nws;

    public static void main(String[] args){
        try{
            nws = getWebsite();
            System.out.println(getWeather(nws));
        } catch (IOException e) { //If the website can't be reached.
            System.err.println("Error: Could not connect to the National Weather Service website. Please try again later.");
        }
    }

    /**
     * Gets the local weather conditions
     * @param web A document containing the NWS website
     * @return A string of the local conditions
     */
    public static String getCond(Document web){
        return web.select("p.myforecast-current").text();
    }

    /**
     * Gets the local temperature in Fahrenheit
     * @param web A document containing the NWS website
     * @return A string with the temperature in Fahrenheit
     */
    public static String getTemp(Document web){
        return web.select("p.myforecast-current-lrg").text();
    }

    /**
     * Gets the local temperature in Celsius
     * @param web A document containing the NWS website
     * @return A string with the temperature in Celsius
     */
    public static String getTempC(Document web){
        return web.select("p.myforecast-current-sm").text();
    }

    /**
     * Gets the detailed report of what's going on weather wise
     * @param web A document containing the NWS website
     * @return A long string with a detailed report of what the weather is doing
     */
    public static String getDetails(Document web){
        return web.select("div#current_conditions_detail.pull-left").text();
    }

    /**
     * Gets a formatted string of the weather in Macomb, IL
     * @param web A document containing the NWS website
     * @return A long formatted String with the weather report
     */
    public static String getWeather(Document web){
        String weather = "| Current Conditions: " + getCond(web) + " | ";
        weather = weather + "\n" + "| Temperature: " + getTemp(web) + ", " + getTempC(web) + " | ";
        weather = weather + "\n" + "| " + getDetails(web) + " | ";
        return weather;
    }

    //Gets the website
    private static Document getWebsite() throws IOException {
        return Jsoup.connect("https://forecast.weather.gov/MapClick.php?x=68&y=83&site=ilx&zmx=&zmy=&map_x=67&map_y=83#").get();
    }
}
