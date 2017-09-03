package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;

public class Main extends Application {

    float avg_wait_time = 0;
    BorderPane root;
    TextArea showProc;

    public String getSelectedValue(String a, String field){
        switch(a){
            case "Esimene": return "A,2;B,3;A,-;C,4;D,5;B,-;E,5";
            case "Teine": return "A,2;B,3;C,4;A,-;B,-;D,4;E,3;D,-;E,-";
            case "Kolmas": return "A,3;B,4;C,2;B,-;D,3;A,-;E,6;F,2;E,-";
            case "Enda oma": return field;
            default: return "0";
        }
    }





    public ArrayList<ArrayList<Integer>> sortByExecute(ArrayList<ArrayList<Integer>> list){

        ArrayList<ArrayList<Integer>> newList = new ArrayList<>();
        int process = 0;
        for(int i = 0; i < list.size(); i++){
            int come = list.get(i).get(0);
            int nr = list.get(i).get(1);
            list.get(i).add(i);

            if(newList.isEmpty() || (!newList.isEmpty() && newList.get(i-1).get(1) <= nr &&
                    process + nr < newList.get(i-1).get(0))){

                newList.add(list.get(i));




            }
            else{
                int j = i-1;
                int n = i;
                while(j >= 0){

                    if(newList.get(j).get(1) <= nr && process + nr < newList.get(i-1).get(0)){
                        break;
                    }
                    else{
                        ArrayList<Integer> change = newList.get(j);
                        newList.remove(j);
                        if(j == i - 1) {
                            newList.add(j, list.get(i));
                        }
                        else{
                            newList.add(j+1, newList.get(n-1));
                            newList.remove(n-1);
                        }
                        newList.add(n, change);
                        j--;
                        n--;
                    }
                }

            }
            process += nr;
        }
        return newList;
    }

    public void currentProc(ArrayList<Process> currentArray, TextArea process){
        process.setText("");
        for(Process elem : currentArray){
            process.setText(process.getText() + "[" + elem.getName()
                    + "," + (elem.isDelete() ? "-" : elem.getSize()) + "]\n");
        }

    }

    public ArrayList<Process> createArray(String text){
        try {
            ArrayList<Process> out = new ArrayList<>();
            String[] part = text.split(";");
            for (String a : part) {
                String[] elem = a.split(",");
                Process proc = new Process(elem[0], elem[1]);
                out.add(proc);
            }
            return out;
        } catch (ArrayIndexOutOfBoundsException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Viga!");
            alert.setHeaderText(null);
            alert.setContentText("Sisend on valesti kirjutatud! Kontolli üle!");
            alert.showAndWait();
            return null;
        }
    }

    public void start(Stage primaryStage) throws Exception{

        root = new BorderPane();

        GridPane user_section = new GridPane();
        root.setLeft(user_section);
        user_section.setPadding(new Insets(5));

        Label inf1 = new Label();
        inf1.setText("Vali või sisesta järjend (kujul A,3;B,5;A,-;C,2)");
        user_section.setColumnSpan(inf1, 5);
        user_section.add(inf1, 1, 1);
        inf1.setPadding(new Insets(10));

        final ToggleGroup group = new ToggleGroup();
        RadioButton op1 = new RadioButton("Esimene");
        op1.setSelected(true);
        user_section.add(op1, 1, 2);
        op1.setToggleGroup(group);
        op1.setPadding(new Insets(10));

        RadioButton op2 = new RadioButton("Teine");
        user_section.add(op2, 1, 3);
        op2.setToggleGroup(group);
        op2.setPadding(new Insets(10));

        RadioButton op3 = new RadioButton("Kolmas");
        user_section.add(op3, 1, 4);
        op3.setToggleGroup(group);
        op3.setPadding(new Insets(10));

        RadioButton op4 = new RadioButton("Enda oma");
        user_section.add(op4, 1, 5);
        op4.setToggleGroup(group);
        op4.setPadding(new Insets(10));

        Label opt1 = new Label("A,2;B,3;A,-;C,4;D,5;B,-;E,5");
        user_section.add(opt1, 2, 2);

        Label opt2 = new Label("A,2;B,3;C,4;A,-;B,-;D,4;E,3;D,-;E,-");
        user_section.add(opt2, 2, 3);

        Label opt3 = new Label("A,3;B,4;C,2;B,-;D,3;A,-;E,6;F,2;E,-");
        user_section.add(opt3, 2, 4);

        TextField opt4 = new TextField();
        user_section.add(opt4, 2, 5);

        Label inf2 = new Label();
        inf2.setText("Algoritmi käivitamiseks vajuta nupule");
        user_section.setColumnSpan(inf2, 5);
        user_section.add(inf2, 1, 6);

        Insets but = new Insets(10);

        GridPane buttons = new GridPane();
        user_section.setColumnSpan(buttons, 5);
        user_section.add(buttons, 1, 7);
        buttons.setPadding(but);

        Button btn_first = new Button("Näita");
        buttons.add(btn_first, 0, 0);
        btn_first.setPadding(but);

        Label inf4 = new Label("Roheline näitab protsessi ja V (punane) näitab vaba kohta");
        user_section.setColumnSpan(inf4, 2);
        user_section.add(inf4, 1, 8);

        GridPane outRight = new GridPane();
        outRight.setPadding(new Insets(10));
        root.setRight(outRight);

        Label inf3 = new Label("Käimas olevad protsessid: ");
        outRight.add(inf3, 0, 1);

        showProc = new TextArea();
        showProc.setMaxWidth(300);
        outRight.add(showProc, 0, 2);






        btn_first.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                RadioButton select = (RadioButton) group.getSelectedToggle();

                ArrayList<Process> workingArray;
                workingArray = createArray(getSelectedValue(select.getText(), opt4.getText()));
                if(workingArray != null) {
                    currentProc(workingArray, showProc);
                    Fixed fixed = new Fixed(workingArray, root);
                    fixed.newDraw();
                    int i = 0;
                    while (!fixed.isFinished()) {
                        fixed.nextStep();
                        fixed.draw();
                        i++;
                    }
                }
            }
        });

        primaryStage.setTitle("Failisüsteem");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

