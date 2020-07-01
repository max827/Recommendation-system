package com.yan.movielens.recommender.similarity.impl;

import com.yan.movielens.recommender.similarity.AbstractRecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.recommender.structure.impl.GeneralMatrix;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 扩展的同现相似度计算，加入了IUF和归一化的优化
 * IUF主要是对过于活跃的用户进行处罚
 * 归一化可以提高物品的多样性，防止只推荐某一种类型的产品
 */
@Slf4j
public class ExCo_occurrenceSimilarity extends AbstractRecommenderSimilarity {
    public ExCo_occurrenceSimilarity(Matrix train) {
        super(train);
    }


    @Override
    public void simialarityInit() {
        super.simialarityInit();
        log.info("开始归一化");
        normalized();
    }

    @Override
    public void updateSimilarity(Integer userId,Integer itemId,Double perference){
        Vector items=train.row(userId);
        if(items.contains(itemId) || simMatrix.size()==0 || items.size()<=1){
            return;
        }
        super.updateSimilarity(userId,itemId,perference);
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

        Iterator<Integer> thisIterator=thisUsersClone.iterator();

        int userId=0;
        double punishment=0;
        while (thisIterator.hasNext()){
            userId=thisIterator.next();
            //对活跃度过高的用户进行惩罚
            punishment= punishment+1/Math.log(1+train.row(userId).size());
        }

        return (punishment + 0.0) / Math.sqrt( thisVector.size()*thatVector.size());
    }

    protected double getSimilarity(List<? extends Number> thisList, List<? extends Number> thatList) {
        return 0.0;
    }

    /**
     * 对相似度矩阵进行归一化
     * 当归一化之后……矩阵将不再是一个对称矩阵，这是个巨坑啊，我一直把这个当相似度矩阵来搞……坑了我好久……
     */
    private void normalized(){
        //由于归一化后的相似度矩阵不是对称矩阵，所以用普通矩阵来存储。
        Matrix newSimMatrix=new GeneralMatrix();
        Set<Integer> thisItems=simMatrix.rowIndex();
        for(Integer thisItemId:thisItems){
            double sum=0;
            Map<Integer,Double> thatItems=simMatrix.row(thisItemId).returnMap();
            for(Map.Entry<Integer,Double> entry:thatItems.entrySet()){
                sum +=entry.getValue();

            }
            if(sum>0){
                for(Map.Entry<Integer,Double> entry:thatItems.entrySet()){
                    int thatItemId=entry.getKey();
                    newSimMatrix.set(thisItemId,thatItemId,entry.getValue()/sum);
                }
            }
        }
        this.simMatrix=newSimMatrix;
    }

}
