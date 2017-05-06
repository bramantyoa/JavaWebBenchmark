package ResponseTimeBenchmarking;

import java.util.ArrayList;

public class ResultArray {
    
    private ArrayList<Long> arr;
    
    ResultArray() {
        this.arr = new ArrayList<Long>();
    }
    
    public synchronized void add(long time) {
        this.arr.add(time);
    }
    
    public long calculateMean() {
        long temp = 0;
        long mean;
        
        for(Long value: this.arr) {
            temp += value;
        }
        
        return temp / this.arr.size();
    }
    
    public void printEach() {
        for(int i = 0; i < this.arr.size(); i++) {
            System.out.println("ArrayList index "+i+" contains: "+this.arr.get(i));
        }
    }
    
}
