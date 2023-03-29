package se2203b.assignment1;

import javafx.application.Platform;

public class MergeSort extends SortingHubController implements SortingStrategy{

    private int[] list;

    private SortingHubController controller;

    //constructor for the MergeSort object
    public MergeSort(int[] nums, SortingHubController controller){
        this.list = nums;
        this.controller = controller;
    }


    @Override
    public void sort(int[] numbers) {
        //sort has to call second method as in-place merge sort takes in 3 arguments instead of 1
        mergeSort(list, 0, list.length-1);

    }

    @Override
    public void run() {
        //makes the sorting method runnable in a separate thread, allowing the GUI to remain workable
        sort(list);

    }


    public void mergeSort(int arr[], int leftIndex, int rightIndex)
    {
        //check if array is trivially sorted (1 data value)
        if (leftIndex < rightIndex) {

            // Same as (l + r) / 2, but avoids overflow
            // for large l and r
            int median = leftIndex + (rightIndex - leftIndex) / 2;

            //recursively call mergeSort on each divided half of the data set
            mergeSort(arr, leftIndex, median);
            mergeSort(arr, median + 1, rightIndex);

            //once no more recursive calls are able to be made, run merge to sort subarrays
            merge(arr, leftIndex, median, rightIndex);
        }
    }


    public void merge(int[] arr, int start, int mid, int end ){
        //create
        int secondStart = mid + 1;

        // If the merge is already sorted
        if (arr[mid] <= arr[secondStart]) {
            return;
        }


        //maintain pointers of each subarray to check the value of each
        while (start <= mid && secondStart <= end) {

            // If the element is in increasing order, check next index of start
            if (arr[start] <= arr[secondStart]) {
                start++;
            }
            else { //executes if secondStart is less than start
                //else maintain pointer of secondStart index and shift everything forward
                int value = arr[secondStart];
                int index = secondStart;

                // Shift all the elements between start and secondStart up by 1
                while (index != start) {
                    arr[index] = arr[index - 1]; //similar to how insertion sort moves values back an index
                    index--;

                    //as indexes of array values are changing we update the graph here with delay
                    try {
                        Thread.sleep(5);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Platform.runLater(()-> controller.updateGraph(list));

                }
                //finally once everythign is shifted up, replace start index with secondStart value
                arr[start] = value;
                try {
                    Thread.sleep(5);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Platform.runLater(()-> controller.updateGraph(list));


                // Update all the pointers
                start++;
                mid++;
                secondStart++;
            }
        }
    }


}
