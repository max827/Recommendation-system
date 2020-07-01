package com.yan.movielens.recommender.util;

import java.util.*;

public class MapUtils {
    public static Map<Integer, Double> sortByValue(Map<Integer,Double> map, int k){
        ArrayList<Map.Entry<Integer, Double>> l = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
        Map<Integer,Double> result=new HashMap<Integer,Double>();

        Collections.sort(l, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                Double v1 = o1.getValue();
                Double v2 = o2.getValue();
                return v2.compareTo(v1);
            }
        });

        if(k>l.size())
            k=l.size();

        for(int i=0;i<k;i++){
            Map.Entry<Integer, Double> e=l.get(i);
            result.put(e.getKey(),e.getValue());
        }

        return result;
    }

    /**
     * 和上面那个一样的，就是返回值是list而已
     */
    public static <K> List<K> sortByValueList(Map<K,? extends Number> map, int k,int flag){

        ArrayList<Map.Entry<K,? extends Number>> l = new ArrayList<Map.Entry<K,? extends Number>>(map.entrySet());
        List<K> result=new ArrayList<>();

        Collections.sort(l, new Comparator<Map.Entry<K,? extends Number>>() {
            public int compare(Map.Entry<K,? extends Number> o1, Map.Entry<K,? extends Number> o2) {
                Double v1 = o1.getValue().doubleValue();
                Double v2 = o2.getValue().doubleValue();

                if(flag==0){
                    return v2.compareTo(v1);
                }else {
                    return v1.compareTo(v2);
                }
            }
        });

        if(k>l.size())
            k=l.size();

        for(int i=0;i<k;i++){
            Map.Entry<K,? extends Number> e=l.get(i);
            result.add(e.getKey());
        }

        return result;

    }

    public static Map<Integer,Double> combineMap(Map map1,Map map2){
        Map<Integer, Double> combineResultMap = new HashMap<Integer, Double>();
        combineResultMap.putAll(map1);
        combineResultMap.putAll(map2);
        return combineResultMap;
    }
}
