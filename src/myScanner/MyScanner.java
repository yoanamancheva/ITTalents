package myScanner;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class MyScanner {

    private Collection<CounterThread> threadsSet = new HashSet<>();
    private String path;
    private int threads;
    private String text;
    private String[] wordsArray;

    public MyScanner(String path, int threads) {
        this.path = path;
        this.threads = threads;

        File file = new File(this.path);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Problem with reading the file, " + e.getMessage());
        }
        StringBuilder builder = new StringBuilder();

        while (sc.hasNextLine()) {
            builder.append(sc.nextLine());
            builder.append("\n");
        }
        this.text = builder.toString();

        // \W -> a non-word character
        // \s -> any whitespace
        this.wordsArray = text.split("[\\W\\s]+");
        sc.close();
    }


    public void countCommas(){

        long start = System.currentTimeMillis();
        for (int i = 1; i <= threads; i++) {
            CounterThread counter = new CounterThread(
                    text,
                    (i - 1) * (text.length() / threads),
                    i == threads ? text.length() :  i * text.length() / threads);

            counter.start();
            threadsSet.add(counter);
        }

        for (CounterThread t : threadsSet) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Problem with joining the threads, " + e.getMessage());
            }
        }

        int numberOfCommas = 0;
        for (CounterThread t : threadsSet) {
            numberOfCommas += t.getCount();
        }
        System.out.println("Number of commas: " + numberOfCommas);
        System.out.println("time: " + (System.currentTimeMillis()-start));

    }

    public void showWordsFrequency() {
        Collection<WordsThread> threads2 = new HashSet<>();
        int startIndex = 0;
        int endIndex = wordsArray.length/threads-1;

        for (int i = 0; i < threads; i++) {

            if(i > 0) {
                startIndex += wordsArray.length/threads;
                endIndex = startIndex + wordsArray.length/threads-1;
            }

            WordsThread wordsThread = new WordsThread(wordsArray, startIndex,
                    i == threads-1 ? wordsArray.length-1 : endIndex);


            threads2.add(wordsThread);
            wordsThread.start();
        }
        for (WordsThread t : threads2) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Problem with joining the threads, " + e.getMessage());
            }
        }
        System.out.println(WordsThread.map);


        int numberWar = 0;
        int numberPeace = 0;
        for (WordsThread t : threads2) {
            numberWar += t.getCountWar();
            numberPeace += t.getCountPeace();
        }

        System.out.println("peace: " + numberPeace);
        System.out.println("war: " + numberWar);
        if(numberPeace > numberWar) {
            System.out.println("The word \"peace\" is more common");
        } else if(numberPeace < numberWar) {
            System.out.println("The word \"war\" is more common");
        } else {
            System.out.println("Both words are equally common");
        }
    }
}
