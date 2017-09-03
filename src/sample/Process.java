package sample;

/**
 * Created by Herko on 28.10.2015.
 */
public class Process implements Comparable<Process>{
    private int step = 0;
    private int workTime;
    private int xStart;
    private int id;
    private String name;
    private String size;

    public Process(String name, String size) {
        this.name = name;
        this.size = size;
        this.workTime = step;

    }

    public Process() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = String.valueOf(size);
    }

    public int getSize() {

        if(isDelete()){
            return -1;
        }
        else {
            return Integer.valueOf(size);
        }
    }

    public boolean isDelete(){

        return size.equals("-");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    @Override
    public int compareTo(Process o) {
        if(xStart > o.getxStart()){
            return 1;
        }
        else if(xStart < o.getxStart()){
            return -1;
        }
        return 0;
    }
}
