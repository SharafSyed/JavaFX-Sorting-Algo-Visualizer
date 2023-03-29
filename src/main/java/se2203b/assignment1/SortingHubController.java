package se2203b.assignment1;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class SortingHubController implements Initializable {

    //establishing all private fields of GUI
    @FXML
    private Label arrSize;
    @FXML
    private ComboBox<String> cBox;
    @FXML
    private Slider sliderChange;
    @FXML
    private Pane display;

    //establishing arraylist of rectangles
    private ArrayList<Rectangle> rectangles;

    //making a thread accessible throughout class by making it in an instance field
    private Thread t;

    //base array for all sorting to occur
    private int[] intArray;

    //chosen sorting method
    private SortingStrategy sortingMethod;


    //constants for the GUI pane height and width
    private double WIN_HEIGHT;
    private double WIN_WIDTH;


    //listener for Sort button, will execute sorting
    public void startSort(ActionEvent event){
        String method;
        //will establish the combo box selected value. catching a NullPointer Exception in case there is nothing selected
        try{
            method = cBox.getValue();
        }
        catch(Exception e){
            return;
        }


        if(method.equals("Selection Sort")) {
            //passing the array of random integers as well as the current instance of the class to the
            //new sorting method object, allowing it to update the current GUI instance
            setSortStrategy(new SelectionSort(intArray, this));
        }
        else if(method.equals("Merge Sort")){
            //passing the array of random integers as well as the current instance of the class to the
            //new sorting method object, allowing it to update the current GUI instance
            setSortStrategy(new MergeSort(intArray, this));
        }

        //create new thread for the sorting method that implements its runnable interface
        //run thread using start()
        t = new Thread(sortingMethod);
        t.start();

    }
    //sets sortingStrategy
    public void setSortStrategy(SortingStrategy sortingMethod) { this.sortingMethod = sortingMethod;  }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //assigns values to width and height of pane
        WIN_WIDTH = display.getPrefWidth();
        WIN_HEIGHT = display.getPrefHeight();


        //initializes choice values of the combo box
        cBox.getItems().addAll("Merge Sort", "Selection Sort");
        cBox.getSelectionModel().selectFirst();//makes the combo box automatically select the first option (merge sort)
        //creates array of random integers
        createArray();


    }

    //called during initialization as well as a listener for any drags or changes on the slider
    public void createArray(){

        //sets the size of the array to the nearest integer of the slider value through casting
        int size = (int)sliderChange.getValue();
        //sets label to the size given by the slider
        arrSize.setText(String.format("%d", size));

        //initializes array size
        intArray = new int[size];
        //initializes rectangles arraylist size
        rectangles = new ArrayList<Rectangle>();

        //creates HashSet to detect any repeated numbers
        HashSet<Integer> unique = new HashSet<>();
        int num = 0;
        //iterates using the hash sets contains() method to make sure intArray contains no repeated values
        for(int i = 0; i < intArray.length; i++) {
            num = (int) (Math.random() * (size)) + 1;
            while (unique.contains(num)){
                num = (int) (Math.random() * size) + 1;
            }
            //if the hash set doesnt contain the number, add it to the set and intArray
            unique.add(num);
            intArray[i] = num;
        }
        //once intArray is generated, update the graph with the new value
        updateGraph(intArray);
    }

    public void updateGraph(int[] data){
        //clears all values from the pane if any existed
        display.getChildren().clear();
        //intializes size of the array based on the argument passed
        int size = data.length;

        //iterates through each value of the array
        for (int i = 0; i < data.length; i++) {
            //creates new rectangle with the following attributes
            //creates rectangle width with gap by dividing pane width and subtracting 1
            //creates rectangle height by scaling it: each height is a proportion of the array value to its maximum multiplied by the pane height
            Rectangle rect = new Rectangle((WIN_WIDTH / size) * i + 2, 0, WIN_WIDTH / size - 2, (int) (((double)data[i]/size) * WIN_HEIGHT));
            //rectangles are initialized at the top of the pane, therefore translate them downwards respective to their height
            rect.setTranslateY(WIN_HEIGHT - ((int)(((double) data[i] / size) * WIN_HEIGHT))-1);
            rect.setFill(javafx.scene.paint.Color.RED); //set color to red
            rectangles.add(rect); //add rectangle to the arrayList
            display.getChildren().add(rect); //add rectangle to the pane
        }
    }


    //listener for the reset button
    public void reset(ActionEvent event){
        //set slider to 64
        sliderChange.setValue(64);
        cBox.setValue("Merge Sort");
        //create a new randomized array
        createArray();
    }
}