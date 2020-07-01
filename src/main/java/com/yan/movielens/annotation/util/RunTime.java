package com.yan.movielens.annotation.util;



import com.yan.movielens.annotation.Time;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTime {
    public static void runTime(Object object) throws InvocationTargetException, IllegalAccessException {


        Method[] methods = object.getClass().getMethods();

        for(Method method:methods){
            if(method.isAnnotationPresent((Time.class))){
                long startTime = System.currentTimeMillis();

                method.invoke(object);

                long endTime = System.currentTimeMillis();
                long runTime=endTime-startTime;
                System.out.println(method.getName()+" 运行时间为："+runTime+"ms");
            }
        }

    }
}
