import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LeakingBucketRateLimiter {
    private final int maxCapacity;
    private final long leakRate;
    private int currentLevel;
    private long lastLeakTimeStamp;
    private Lock lock;

    public LeakingBucketRateLimiter(int maxCapacity, long leakRate){
        this.maxCapacity = maxCapacity;
        this.leakRate = leakRate;
        currentLevel = 0;
        lastLeakTimeStamp = System.nanoTime();
        lock = new ReentrantLock();
    }

    public void leak(){
        long now = System.nanoTime();
        long elapsed_time = now - lastLeakTimeStamp;
        double seconds_elapsed = (int)elapsed_time/1_000_000_000.0;

        //This is number of units that should have been leaked in the given time.
        int unit_to_leak = (int)(seconds_elapsed * leakRate);

        if(unit_to_leak > 0){
            currentLevel = Math.max(currentLevel - unit_to_leak, 0);
            lastLeakTimeStamp = now;
        }
    }

    public boolean allowRequest(int units){
        lock.lock();
        try{
            leak();
            if(currentLevel + units > maxCapacity){
                return false;
            }else{
                return true;
            }
        }finally{
            lock.unlock();
        }
    }
}
