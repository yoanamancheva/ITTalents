package myScanner;

import javafx.beans.value.WritableObjectValue;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class WordsThread extends Thread{
    private static  String[] arrayOfWords;
    private final int startIndex;
    private final int endIndex;
    private int countWar;
    private int countPeace;

    public static ConcurrentHashMap<String, Integer> map;

    public WordsThread(String[] arrayOfWords, int startIndex, int endIndex) {
        WordsThread.arrayOfWords = arrayOfWords;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= endIndex; i++) {
                String word = arrayOfWords[i];
                if(word.contains("war")) {
                    this.countWar++;
                }
                if (word.contains("peace")) {
                    this.countPeace++;
                }

                if(map.containsKey(word)) {
                    int prevNumber = map.get(word);
                    map.put(word, prevNumber+1);
                } else {
                    map.put(word, 1);
                }
        }

    }

    public int getCountPeace() {
        return countPeace;
    }

    public int getCountWar() {
        return countWar;
    }
}
