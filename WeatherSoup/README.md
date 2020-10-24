# WeatherSoup
A Java JSoup program that prints out the local weather for Macomb, IL. This program uses JSoup as a dependency but is handled by Maven.

## AUTHOR
Evan Colwell
ec-colwell@wiu.edu
eccolwell99@gmail.com

## USAGE

To run the program, you need Maven and you need to run the following:

``` mvn exec:java -Dexec.mainClass=com.ajaj895.weathersoup.WeatherSoup ```

## DEPENDENCIES

This project uses JSoup, but is handled by the Maven package manager. The latest version of Maven is needed.

Run the following in the directory with the POM.xml:

``` mvn clean package ``` 

## ABOUT

This is a webscraping application that takes the weather from the national weather service and prints it to the console/terminal. This will also catch errors if it can not connect to the website.
