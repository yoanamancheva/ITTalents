package myScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Demo  {

    public static void main(String[] args) throws Exception {

        String path = "war-peace.txt";
        MyScanner myScanner = new MyScanner(path, 5);
        myScanner.countCommas();

        WordsThread.map = new ConcurrentHashMap<>();
        myScanner.showWordsFrequency();

    }
}
