package com.yan.movielens.recommender.data;

import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.recommender.structure.impl.GeneralMatrix;


import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class AbstractDataModel implements DataModel{

    protected Matrix data = null;
    protected Matrix trainData = null;
    protected Matrix testData = null;

    public abstract void dataConversion();

    @Override
    public void splitData(DataModel dataModel,int blocknum){
        data=dataModel.getData();
        if(data == null){
            return;
        }

        trainData = new GeneralMatrix();
        testData = new GeneralMatrix();

        Random r=new Random();

        Set<Integer> userIds=data.rowIndex();

        Iterator<Integer> userIdsIterator=userIds.iterator();
        int userId=0;
        while (userIdsIterator.hasNext()){
            userId=userIdsIterator.next();

            Vector items=data.row(userId);
            Map<Integer,Double> itemsMap=items.returnMap();
            int itemId=0;
            double preferences=0.0;
            int a=0;
            for(Map.Entry<Integer,Double> entry:itemsMap.entrySet()){
                itemId=entry.getKey();
                preferences=entry.getValue();
                a=r.nextInt(blocknum)+1;
                if(a==1){
                    testData.set(userId,itemId,preferences);
                }else {
                    trainData.set(userId,itemId,preferences);
                }
            }
        }
    }

    @Override
    public void cuttingData(int num) {
        data=getData();
        Matrix newData=new GeneralMatrix();


        Set<Integer> users=data.rowIndex();
        if(num>users.size()){
            return;
        }

        int i=0;
        for(Integer userId:users){
            if(i == num){
                this.data=newData;
                break;
            }

            Vector userItem=data.row(userId);
            Set<Integer> items=userItem.indexSet();
            for(Integer itemId:items){
                double preference = userItem.get(itemId);
                newData.set(userId,itemId,preference);

            }
            i++;
        }

    }


    @Override
    public Matrix getData() {
        return data;
    }

    @Override
    public Matrix getTestData() {
        return testData;
    }

    @Override
    public Matrix getTrainData() {
        return trainData;
    }

    @Override
    public void setData(Matrix data){
        this.data=data;
    }
}
