package se2203b.assignment1;

import javafx.application.Platform;

public class SelectionSort extends SortingHubController implements SortingStrategy{

    private int[] list;

    private SortingHubController controller;


    @Override
    public void sort(int[] numbers) {
        int temp;
        int size = numbers.length;
        list = numbers;

        for (int i = 0; i < size -1 ; i++){
            int largestIndex = i; //initializes index of the largest term at the first term AFTER the first term
            for (int j = i + 1; j < size; j++){
                if (numbers[j] < (numbers[largestIndex])){
                    largestIndex = j; //continuously finds smaller term until finding the next minimum
                }
            }
            //swaps current term of outer index with next smallest term
            temp = numbers[i];
            numbers[i] = numbers[largestIndex];
            numbers[largestIndex] = temp;

            //update GUI with a delay each time index is changed for a value with new list
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            Platform.runLater(() -> controller.updateGraph(list));

        }
    }

    @Override
    public void run() {
        sort(this.list);
    }

    //constructor to establish properties of the SelectionSort object
    public SelectionSort(int[] nums, SortingHubController controller) {
        this.list = nums;
        this.controller = controller;

    }
}
