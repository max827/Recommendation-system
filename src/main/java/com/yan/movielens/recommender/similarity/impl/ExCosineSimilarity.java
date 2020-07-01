package com.yan.movielens.recommender.similarity.impl;

import com.yan.movielens.recommender.similarity.AbstractRecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.recommender.util.MathUtils;

import java.util.*;

/**
 * 扩展的余弦相似度
 * 主要解决用户之间不同的评分尺度导致的推荐结果不准确
 */
public class ExCosineSimilarity extends AbstractRecommenderSimilarity {
    public ExCosineSimilarity(Matrix train) {
        super(train);
    }


    @Override
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
        double innerProduct = 0.0, thisPower2 = 0.0, thatPower2 = 0.0;
        while (thisIterator.hasNext()){
            userId=thisIterator.next();

            //计算该用户的平均评分
            double sum=0.0;
            for (Map.Entry<Integer,Double> entry1:train.row(userId).returnMap().entrySet()){
                sum += entry1.getValue();
            }
            double userMean= sum/train.row(userId).size();

            //开始计算余弦相似度
            double thisMean=thisVector.get(userId)-userMean;
            double thatMean=thatVector.get(userId)-userMean;
            innerProduct += thisMean * thatMean;
            thisPower2 += thisMean * thisMean;
            thatPower2 += thatMean * thatMean;

        }

        return innerProduct / Math.sqrt(thisPower2 * thatPower2);
    }

    @Override
    protected double getSimilarity(List<? extends Number> thisList, List<? extends Number> thatList) {
      return 0.0;
    }
}
