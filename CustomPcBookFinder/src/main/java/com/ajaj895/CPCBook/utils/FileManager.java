/**
 * CustomPC File Manager
 * Written by: Evan C.
 * August 2022
 *
 * FileManager is an object class that manages the file needed to run CustomPcBookFinder. This file also creates, writes,
 * and compares the file's contents when necessary. This object will create a file bookfile.txt in the current directory
 * of the .jar file and this object will make the file if it does not find it.
 */

package com.ajaj895.CPCBook.utils;

import java.io.*;
import java.util.Scanner;

public class FileManager {

    private File file;
    private FileWriter fw;
    private FileReader fr;
    private int currentBooks = 0;

    public FileManager(int books) {
        build(books);
    }

    public int getCurrentBooks(){
        return currentBooks;
    }

    private void build(int books) {

        file = new File("bookfile.txt");

        // File Creation
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error: Unable to create file. Closing... " + e);
                System.exit(1);
            }
        }

        // File Comparison
        try {

            if(file.length() != 0) { // Gets the number of books from the previous time this program was run.
                Scanner sc = new Scanner(file);
                while(sc.hasNextLine()){
                    currentBooks = Integer.parseInt(sc.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Unable to read file. Closing... " + e);
            System.exit(1);
        }

        // New book checker
        if(currentBooks < books && currentBooks != 0) System.out.println("\nNo new CustomPC books found!"); // Will say 33 new books on first run
        else System.out.println("\n" + (books - currentBooks) + " new CustomPC books found!");

        // File Writing
        try {
            fw = new FileWriter(file);
            fw.write(String.valueOf(books));
            //System.out.println("Wrote " + books + " to file!");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Unable to write to the file. Closing... " + e);
            System.exit(1);
        }

    }

}
