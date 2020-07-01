package com.yan.movielens.recommender.similarity;





import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.recommender.structure.impl.SymmMatrix;

import java.util.*;


public abstract class AbstractRecommenderSimilarity implements RecommenderSimilarity {

    protected Matrix simMatrix;
    protected Matrix train;

    public AbstractRecommenderSimilarity(Matrix train){
        this.simMatrix=new SymmMatrix();
        this.train=train;
    }

    @Override
    public void simialarityInit() {

        //获取训练集中的所有物品ID
        Set<Integer> itemIds=train.columnIndex();

        List<Integer> itemList = new ArrayList<>(itemIds);
        Collections.sort(itemList);

        int thisItem=0;
        int thatItem=0;
        int num=itemList.size();
        for(int i=0;i<num;i++){
            thisItem = itemList.get(i);
            for(int j=i+1;j<num;j++){
                thatItem = itemList.get(j);
                double sim = getCorrelation(thisItem,thatItem);
                if (!Double.isNaN(sim) && sim != 0.0) {
                    simMatrix.set(thisItem, thatItem, sim);
                }
            }
        }

    }


    public double getCorrelation(Integer thisItem, Integer thatItem) {

        //用来记录两个物品的所有用户
        Vector thisVector = train.column(thisItem);
        Vector thatVector = train.column(thatItem);

        //thisList和thatList用来记录两个物品的共同用户对于这两个物品的偏好值
        List<Double> thisList = new ArrayList<Double>();
        List<Double> thatList = new ArrayList<Double>();

        Set<Integer> thisUsers=thisVector.indexSet();
        Set<Integer> thatUsers=thatVector.indexSet();

        //防止接下来取交集时改变原有的数据集
        Set<Integer> thisUsersClone=new HashSet<>(thisUsers);

        //获得两个用户列表之间的交集，即共同对两个物品产生过行为的用户
        thisUsersClone.retainAll(thatUsers);

        //如果两个物品之间没有共同用户，那么这两个物品之间相似度为0
        if(thisUsersClone.size()==0){
            return 0.0;
        }

        Iterator<Integer> thisIterator=thisUsersClone.iterator();

        int userId=0;
        while (thisIterator.hasNext()){
            userId=thisIterator.next();
            thisList.add(thisVector.get(userId));
            thatList.add(thatVector.get(userId));
        }

        double sim = getSimilarity(thisList, thatList);

        return sim;

    }

    protected abstract double getSimilarity(List<? extends Number> thisList, List<? extends Number> thatList);

    public void updateSimilarity(Integer userId,Integer itemId,Double perference){
        //获取原先的所有物品
        Set<Integer> allItem= train.columnIndex();

        //先更新训练集
        updateTrain(userId,itemId,perference);

        //若是从未出现的新物品
        if(!allItem.contains(itemId)){
            updateNewItem(userId,itemId,perference);
            return;
        }

        //老物品
        //更新之前与这个物品所有相关的物品
        Vector oldItems=simMatrix.row(itemId);
        for(Integer oldItemId:oldItems.indexSet()){
            double sim = getCorrelation(itemId,oldItemId);
            //这里我实在是觉得不用验证了，毕竟这些数据本来就是从相似度矩阵里面拿出来的
            simMatrix.set(itemId,oldItemId, sim);
        }

        //更新用户列表中从未与该物品发生关系的物品
        Vector userItems=train.row(userId);
        for(Integer userItem:userItems.indexSet()){
            if(!oldItems.contains(userItem)){
                double sim = getCorrelation(itemId,userItem);
                //虽然讲道理不可能算出来为0，以防万一还是验证一下好了
                if (!Double.isNaN(sim) && sim != 0.0) {
                    simMatrix.set(itemId,userItem, sim);
                }
            }
        }
    }

    protected void updateNewItem(Integer userId,Integer newItemId,Double perference){
        //获得这个用户过去的物品合集
        Vector items=train.row(userId);
        //和物品合集产生行为
        Set<Integer> itemIds=items.indexSet();
        for(Integer oldItemId:itemIds){
            double sim = getCorrelation(newItemId,oldItemId);
            //虽然讲道理不可能算出来为0，以防万一还是验证一下好了
            if (!Double.isNaN(sim) && sim != 0.0) {
                simMatrix.set(newItemId,oldItemId, sim);
            }
        }
    }

    protected void updateTrain(Integer userId,Integer itemId,Double perference){
        train.set(userId,itemId,perference);
    }

    @Override
    public Matrix getSim() {
        return simMatrix;
    }

    @Override
    public Matrix getTrain(){
        return train;
    }
}
