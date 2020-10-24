package main;

import java.util.ArrayList;

import arraySort.*;
import reversePolishNotation.ReversePolishNotation;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        array.add(5);
        array.add(4);
        array.add(1);
        array.add(2);
        array.add(7);
        array.add(0);
        array.add(3);
        array.add(6);
        array.add(9);
        array.add(8);

        ArraySort arraySort = new BucketSort(array, false);
        ArrayList<Integer> res = arraySort.sort();

        for (Integer i : res) {
            System.out.println(i);
        }

        String str = "a+b*(b+c/d)/g";
        System.out.println(ReversePolishNotation.toPolishNotation(str));
    }

}
