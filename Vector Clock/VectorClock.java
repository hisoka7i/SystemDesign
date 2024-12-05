
import java.util.Arrays;

public class VectorClock {
    private int[] clock;

    //we need a constructor which will get the size of processes involved
    public VectorClock(int processes){
        clock = new int[processes];
        Arrays.fill(clock, 0);
    }

    //now we need a function to increment
    public boolean increment(int processId){
        try {
            clock[processId]++;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //a function to merge 2 clocks
    public boolean mergeClocks(int[] otherclock){
        try {
            for(int i=0; i<clock.length;i++){
                //whichever is more latest will update the clock
                clock[i] = Math.max(clock[i], otherclock[i]);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //we need a function to get the clock
    public int[] getClock(){
        return clock;
    }

    //we need a function to print clock 
    public void printClock() {
        System.out.println("Vector Clock: " + Arrays.toString(clock));
    }
}
