
package Palindrome;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.Properties;

import Palindrome.Store;

public class Main {
    private Store store;
    public static void main(String[] args)
    {
        Main program = new Main();
        program.run();
    }

    public Main(){
        store = new Store();
    }

    public void run(){
        Properties properties = this.store.loadConfig();
        String input = properties.getProperty("inputXml");
        String output = properties.getProperty("outputXml");
        if(!this.store.existFile(input)){
            store.writeLog("No pudimos encontrar el archivo: " + input);
            return;
        }
        ArrayList<String> tests = this.store.readFromXML(input);
        ArrayList<Boolean> results = this.testPalindromes(tests);
        store.saveToXML(output,results);
    }

    public ArrayList<Boolean> testPalindromes(ArrayList<String> tests){
        ArrayList<Boolean> results = new ArrayList<>();
        tests.forEach((test) -> {
            String cleaned = test.toLowerCase().replaceAll("[^a-z0-9]","");
            String[] letters = cleaned.split("");
            letters = invert(letters);
            String revertedWord = String.join("", letters);
            Boolean result = cleaned.equals(revertedWord);
            results.add(result);
        });
        return results;
    }

    private String[] invert(String[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            String temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    } 
}
