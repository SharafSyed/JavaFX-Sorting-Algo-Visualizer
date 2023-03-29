package se2203b.assignment1;

//interface implements runnable so that it may use run() method for multithreading
public interface SortingStrategy extends Runnable{
    //adds sort method to it
    public void sort(int[] numbers);

}
