package main;

import java.util.ArrayList;

import arraySort.*;
import reversePolishNotation.ReversePolishNotation;
import tylorFormula.TylorFormula;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<>();
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

        ArrayList<Integer> res = BucketSort.sort(array, true);

        for (Integer i : res) {
            System.out.println(i);
        }

        String str = "a+b*(b+c/d)/g";
        System.out.println(ReversePolishNotation.toPolishNotation(str));

        System.out.println(TylorFormula.powE(1.5, 10));
        System.out.println(Math.pow(Math.E, 1.5));

        System.out.println(TylorFormula.lnX(1.5, 10));
        System.out.println(Math.log(1.5));
    }

}
