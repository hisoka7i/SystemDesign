import java.util.PriorityQueue;
import java.util.Queue;

public class SlidingWindowLog {
    private final int windowSize;
    private final long timeWindow;
    private Queue<Long> timeStampLog;

    public SlidingWindowLog(int windowSize, long timeWindow){
        this.windowSize = windowSize;
        this.timeWindow = timeWindow;
        timeStampLog = new PriorityQueue<>();
    }

    public synchronized boolean allowRequest(){
        long current_time = System.currentTimeMillis();

        if(!timeStampLog.isEmpty() && (current_time - timeStampLog.peek()) > windowSize){
            timeStampLog.clear();
        }

        if(timeStampLog.size() < windowSize){
            return true;
        }
        return false;
    }
}
