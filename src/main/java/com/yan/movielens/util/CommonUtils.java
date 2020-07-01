package com.yan.movielens.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonUtils {


    /**
     * 将一个list分割成等长（len）的n份
     * @param list 需要被分割的list
     * @param len 每次分割的长度
     * @param <T> 数据类型
     * @return 分割好的数据用list嵌套的方式获得
     */
    public static <T> List<List<T>> splitList(List<T> list, int len) {

        if (list == null || list.isEmpty() || len < 1) {
            return Collections.emptyList();
        }

        List<List<T>> result = new ArrayList<>();

        int size = list.size();

        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }

        return result;
    }
}
