package com.yan.movielens.recommender.util;

import java.util.Collection;

public class MathUtils {
    public static double mean(Collection<? extends Number> data){
        double sum = 0.0;
        int count = 0;
        for (Number d : data) {
            if (!Double.isNaN(d.doubleValue())) {
                sum += d.doubleValue();
                count++;
            }
        }
        return sum / count;
    }
}
