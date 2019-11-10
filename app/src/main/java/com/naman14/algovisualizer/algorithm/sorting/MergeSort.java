package com.naman14.algovisualizer.algorithm.sorting;

import android.app.Activity;

import com.naman14.algovisualizer.LogFragment;
import com.naman14.algovisualizer.algorithm.Algorithm;
import com.naman14.algovisualizer.algorithm.DataHandler;
import com.naman14.algovisualizer.visualizer.SortingVisualizer;

public class MergeSort extends SortAlgorithm implements DataHandler {

    int[] array;

    public MergeSort(SortingVisualizer visualizer, Activity activity, LogFragment logFragment) {
        this.visualizer = visualizer;
        this.activity = activity;
        this.logFragment = logFragment;
    }

    
    @Override
    public void run() {
        super.run();
    }

    //    Sorting algorithm (merge sort)
    private void sort() {
        logArray("Original array - ", array);
        if (array == null || array.length == 0) return;

        mergesort(array, new int[array.length], 0, array.length - 1);
        addLog("Array has been sorted");
        completed();
    }

    private void mergesort(int[] array, int[] temp, int leftStart, int rightEnd){
        if(leftStart >= rightEnd) return;
        int middle = (leftStart + rightEnd) / 2;
        mergesort(array, temp, leftStart, middle);
        mergesort(array, temp, middle + 1, rightEnd);
        mergeHalves(array, temp, leftStart, rightEnd);
    }

    private void mergeHalves(int[] array, int[] temp, int leftStart, int rightEnd) {
        int leftEnd = (leftStart + rightEnd) / 2;
        int rightStart = leftEnd + 1;
        int size = rightEnd - leftStart + 1;

        int left = leftStart;
        int right = rightStart;
        int index = leftStart;

        while(left <= leftEnd && right <= rightEnd) {
            highlightSwap(left, right);
            addLog("Swapping " + array[left] + " and " + array[right]);
            if(array[left] <= array[right]){
                temp[index] = array[left];
                left++;
            } else{
                temp[index] = array[right];
                right++;
            }
            sleep();
            index++;
        }
//        given two arrays, copies one to the other
        System.arraycopy(array, left, temp, index, leftEnd - left + 1);
        System.arraycopy(array, right, temp, index, rightEnd - right + 1);
        System.arraycopy(temp, leftStart, array, leftStart, size);

    }

    @Override
    public void onDataRecieved(Object data) {
        super.onDataRecieved(data);
        this.array = (int[]) data;
    }

    @Override
    public void onMessageReceived(String message) {
        super.onMessageReceived(message);
        if (message.equals(Algorithm.COMMAND_START_ALGORITHM)) {
            startExecution();
            sort();
        }
    }

}
