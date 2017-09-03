package sample;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by Herko on 28.10.2015.
 */
public class Fixed {
    private int block = 50;
    private ArrayList<Process> processes;
    private ArrayList<FreeSpace> freeSpace = new ArrayList<>();
    private ArrayList<Process> working = new ArrayList<>();
    private ArrayList<Rectangle> rectangles = new ArrayList<>();
    private int step = 0;
    private int xCurrent = 0;
    private BorderPane root;
    private Group graphF;
    private int elems = 0;
    private int frees = 0;


    public Fixed(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public Fixed(ArrayList<Process> processes, BorderPane root) {
        this.processes = processes;
        this.root = root;
    }

    public int getUsedBlock(){
        return 50 - block;
    }

    public int getFreeBlock(){
        return block;
    }

    public boolean isFinished(){

        if(processes.size() <= step )
            return true;
        return false;
    }

    public String nextStep(){
        String lopp = "";
        freeSpace.sort((f1, f2) -> f1.compareTo(f2));
        if(processes.size() > step) {
            Process newProt = processes.get(step);
            if(newProt.isDelete()){
                for(int i = 0; i < working.size(); i++){
                    if(working.get(i).getName().equals(newProt.getName())){
                        FreeSpace freeSpace1 = new FreeSpace(working.get(i).getxStart(), working.get(i).getSize());
                        freeSpace.add(freeSpace1);
                        block += working.get(i).getSize();
                        working.remove(working.get(i));
                        i--;

                    }
                }
            }
            else {
                newProt.setxStart(xCurrent);
                newProt.setId(elems);
                xCurrent += newProt.getSize();
                if (block - newProt.getSize() < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Pole ruumi");
                    alert.setHeaderText(null);
                    alert.setContentText("Pole piisavalt ruumi, et lisada!");

                    alert.showAndWait();
                } else {
                    block -= newProt.getSize();
                    elems++;

                    if (!freeSpace.isEmpty()) {
                        for (int i = 0; i < freeSpace.size(); i++) {
                            if (freeSpace.get(i).getFree() >= newProt.getSize()) {
                                Process prot1 = new Process();
                                prot1.setName(newProt.getName());
                                newProt.setxStart(freeSpace.get(i).getxStart());
                                prot1.setSize(newProt.getSize());
                                newProt.setSize(newProt.getSize()-freeSpace.get(i).getFree());
                                working.add(prot1);
                                if (freeSpace.get(i).getFree() != newProt.getSize()) {
                                    int nxStart = freeSpace.get(i).getxStart() + prot1.getSize();
                                    int nMemo = freeSpace.get(i).getFree() - prot1.getSize();
                                    freeSpace.add(new FreeSpace(nxStart, nMemo, frees));
                                    frees++;
                                }
                                freeSpace.remove(freeSpace.get(i));
                                i--;

                                break;

                            }
                            else {
                                Process prot = new Process();
                                prot.setName(newProt.getName());
                                prot.setxStart(freeSpace.get(i).getxStart());
                                prot.setSize(freeSpace.get(i).getFree());
                                newProt.setSize(newProt.getSize()-freeSpace.get(i).getFree());
                                working.add(prot);
                                freeSpace.remove(freeSpace.get(i));
                                i--;
                            }
                        }
                    }

                    if(freeSpace.isEmpty() && newProt.getSize() > 0) {
                        newProt.setxStart(xCurrent);
                        working.add(newProt);
                    }

                }
            }
        }

        step++;
        working.sort((p1, p2) -> p1.compareTo(p2));
        freeSpace.sort((f1, f2) -> f1.compareTo(f2));
        return "Step:" + (step - 1) + lopp;
    }

    public void newDraw(){
        graphF = new Group();
        root.setBottom(graphF);
        root.setMargin(graphF, new Insets(30, 40, 50, 40));
        graphF.minHeight(100);
        graphF.minWidth(100);
    }

    public void draw(){

        int x = 0;
        int y = 0;
        int work = 0;
        int free = 0;
        int mv = 0;
        while(working.size() != work || freeSpace.size() != free){
            Process pro = new Process();
            FreeSpace memo = new FreeSpace();
            int fast = 0;
            try {
                pro = working.get(work);
            }catch (NoSuchElementException | IndexOutOfBoundsException ex){
                fast += 1;
            }
            try {
                memo = freeSpace.get(free);
            }catch (NoSuchElementException | IndexOutOfBoundsException ex){
                fast += 2;
            }


            if(fast == 2 || pro.getxStart() <= memo.getxStart() && working.size() != 0
                    && ((pro.getxStart() == 0 && mv == 0) || (pro.getxStart() != 0 && mv > 0))){
                int nr = pro.getSize();
                Rectangle rect = new Rectangle(60 * step, y * 10, 50, nr * 10);
                rect.setFill(Color.GREEN);
                rect.setStroke(Color.BLACK);
                graphF.getChildren().add(rect);
                rectangles.add(rect);
                y += nr;
                work++;
                Text r0 = new Text("");
                r0.setText(pro.getName());
                r0.setX(60 * step + 25);
                r0.setY(y * 10 - 2);
                graphF.getChildren().add(r0);
            }else if(fast == 1 || pro.getxStart() > memo.getxStart() && freeSpace.size() != 0){
                int nr = memo.getFree();
                Rectangle rect = new Rectangle(60 * step, y * 10, 50, nr * 10);
                rect.setFill(Color.RED);
                rect.setStroke(Color.BLACK);
                graphF.getChildren().add(rect);
                rectangles.add(rect);
                y += nr;
                free++;
                Text r0 = new Text("");
                r0.setText("V");
                r0.setX(60*step + 25);
                r0.setY(y * 10 - 2);
                graphF.getChildren().add(r0);
            }
            mv++;
        }

    }

}
