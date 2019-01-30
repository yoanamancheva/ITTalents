package myScanner;


public class CounterThread extends Thread {
    private final String text;
    private final int startIndex;
    private final int endIndex;
    private int count;

    public CounterThread(String text, int startIndex, int endIndex) {
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int index = this.startIndex; index < this.endIndex; index++ ) {
            if(this.text.charAt(index) == ',' ){
                    count++;
            }
        }
    }

    public int getCount() {
        return count;
    }
}
