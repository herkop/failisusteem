package sample;

/**
 * Created by Herko on 28.10.2015.
 */
public class FreeSpace implements Comparable<FreeSpace>{
    private int xStart;
    private int free;
    private int id;

    public FreeSpace(int xStart, int free) {
        this.xStart = xStart;
        this.free = free;
    }

    public FreeSpace(int xStart, int free, int id) {
        this.xStart = xStart;
        this.free = free;
        this.id = id;
    }

    public FreeSpace() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int memory) {
        this.free = free;
    }

    @Override
    public int compareTo(FreeSpace o) {
        if(xStart > o.getxStart()){
            return 1;
        }
        else if(xStart < o.getxStart()){
            return -1;
        }
        return 0;
    }
}
