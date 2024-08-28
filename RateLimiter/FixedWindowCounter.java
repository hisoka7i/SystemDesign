import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class FixedWindowCounter{
    private final int maxRequest;
    private long windowStart;
    private final long windowDurationMillis;
    private ConcurrentHashMap<String,Integer> requestCounter = new ConcurrentHashMap<>();

    public FixedWindowCounter(int maxRequest, long windowDuration, TimeUnit unit){
        this.maxRequest = maxRequest;
        this.windowDurationMillis = unit.toMillis(windowDuration);
        this.windowStart = System.currentTimeMillis();
    }

    public boolean isAllowed(String clientId){
        long current_time = System.currentTimeMillis();

        //if time duration between current and starting is greater then window time,
        //then clear, request counter
        if((current_time - windowStart)>windowDurationMillis){
            requestCounter.clear();
            windowStart = current_time;
        }

        if(!requestCounter.contains(clientId)){
            requestCounter.put(clientId, 0);
        }

        int requestCount = requestCounter.get(clientId);

        if(requestCount > maxRequest){
            return false;
        }
        requestCounter.put(clientId, requestCount+1);
        return true;
    }
}