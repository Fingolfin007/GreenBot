package com.iot.ece.greenbot;

/**
 * Created by xunda on 4/27/2017.
 */

import java.lang.Float;
import java.util.*;

public class StrToArray {

//    public static void main(String args[])
//    {
//        String s = "2  12  3   4   1 1";
//        System.out.println(s);
//        float[] result = StrToArray.convert(s);
//        for (int i = 0; i < result.length; i++)
//            System.out.println(result[i]);
//
//    }

    public static float[] convert( String s) {

        String[] NumString = s.split(" ");
        for (int i = 0; i < NumString.length; i++)
            NumString[i] = NumString[i].trim();

        ArrayList<Float> nums = new ArrayList<>();

        for (int i = 0; i < NumString.length; i++)
            try {
                nums.add(Float.valueOf(NumString[i]));
            } catch (Exception e) {
            };

        Float[] Result = new Float[nums.size()];
        Result = nums.toArray(Result);

        float result[] = new float[Result.length];
        for (int i = 0; i < Result.length; i++)
            result[i] = Result[i].floatValue();
        return result;

    }
}