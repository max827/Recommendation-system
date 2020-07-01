package com.yan.movielens.util;

import com.yan.movielens.entity.Movie;

import java.util.*;

public class MapUtils {

    /**
     * 对一个Map根据value进行排序
     * @param map 要排序的map
     * @param k 决定返回多少个
     * @param flag 如果为0，降序，其他为升序
     * @param <K> 传进来的类型
     * @return 包含了排序好的对象的Map
     */
    public static <K> Map<K,? extends Number> sortByValue(Map<K,? extends Number> map, int k,int flag){

        ArrayList<Map.Entry<K,? extends Number>> l = new ArrayList<Map.Entry<K,? extends Number>>(map.entrySet());
        Map<K,Double> result=new HashMap<>();

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
            result.put(e.getKey(),e.getValue().doubleValue());
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
}
