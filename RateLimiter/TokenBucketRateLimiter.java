import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiter {
    private final int maxToken;
    private final int refillRate;
    //This is token, thread friendly and avoid concurrency issues
    private AtomicInteger currentTokens;
    private long lastRefillTimeStamp;
    private Lock lock;
    // private long now;

    public TokenBucketRateLimiter(int maxToken, int refillRate){
        this.maxToken = maxToken;
        this.refillRate = refillRate;
        this.currentTokens = new AtomicInteger(maxToken);
        this.lock = new ReentrantLock();
        this.lastRefillTimeStamp = System.nanoTime();
    }

    //here we will refill the bucket
    private void refill(){
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTimeStamp;
        int tokensToAdd = (int)(elapsedTime/1000_000_000L * refillRate);

        if(tokensToAdd>0){
            int newToken = Math.min(maxToken, currentTokens.get() + tokensToAdd);
            currentTokens.set(newToken);
            lastRefillTimeStamp = now;
        }
    }

    public boolean allowRequest(){
        lock.lock();
        try{
            refill();
            if(currentTokens.get() > 0){
                currentTokens.decrementAndGet();
                return true;
            }else{
                return false;
            }
        }finally{
            lock.unlock();
        }
    }
}
