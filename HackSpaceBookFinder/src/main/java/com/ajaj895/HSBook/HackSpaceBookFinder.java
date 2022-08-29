/**
 * HackSpace Book Finder
 * Written by Evan C.
 * August 2022
 *
 * HackSpaceBookFinder is a simple java program that looks at Raspberry Pi Press's HackSpace website to see what books are
 * available. The first time that this program is run, it will create a txt file that has the current number of books
 * available on their site. The next time that the program runs, it will compare the new number of books with the old
 * number of books to see if any new books have been released since the program was last run.
 */
package com.ajaj895.HSBook;

import com.ajaj895.HSBook.utils.FileManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;



public class HackSpaceBookFinder {

    private static Document hackspace;
    private static LinkedList<String> booklist;
    private static FileManager fm;

    public static void main(String[] args){
        try{
            hackspace = getHackspace();
            booklist = getBookList(hackspace);
            //printBooks(booklist);
            //System.out.println(booklist.size()); //for testing
            fm = new FileManager(booklist.size());
        } catch (IOException e) {
            System.err.println("Error: Cannot connect to HackSpace website. Please try later...");
        }
    }

    public static LinkedList<String> getBookList(Document web){
        LinkedList<String> books = new LinkedList<>();
        for(Element book : web.select("h2.o-type-display.rspec-book-card-heading")){
            books.addLast(book.text());
        }
        return books;
    }

    public static void printBooks(LinkedList<String> books){
        for(int i = 0; i < books.size(); i++){
            System.out.println(books.get(i));
        }
    }

    public static Document getHackspace() throws IOException {
        return getWebsite();
    }

    private static Document getWebsite() throws IOException {
        return Jsoup.connect("https://hackspace.raspberrypi.com/books").get();
    }

}
