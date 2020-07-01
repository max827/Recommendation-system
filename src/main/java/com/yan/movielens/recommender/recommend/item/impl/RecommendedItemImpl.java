package com.yan.movielens.recommender.recommend.item.impl;



import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.util.MapUtils;


import java.util.Iterator;
import java.util.Map;

public class RecommendedItemImpl implements RecommendedItem {

    //物品ID
    private Integer itemId;
    //推荐原因，应该是用户曾经发生过行为的产品
    private Map<Integer,Double> reason;
    //相似度
    private double value = 0.0;

    public RecommendedItemImpl(Integer itemId,Map<Integer,Double> reason){
        this.itemId=itemId;
        this.reason=reason;
    }



    public double getReason(int id){
        return reason.get(id);
    }

    public Map<Integer,Double> getAllReason() {
        return reason;
    }

    private void refreshValue(){
        value=0;
        for(Map.Entry<Integer,Double> entry:reason.entrySet()){
            value += entry.getValue();
        }
    }

    @Override
    public void setReason(int id,double weight) {
        reason.put(id,weight);
    }

    @Override
    public Integer getItemId() {
        return itemId;
    }

    @Override
    public Integer getTrueReason() {
        //找到权重最大的相似物品，即推荐这个物品的原因
        Map<Integer,Double> truereason= MapUtils.sortByValue(reason,1);
        Iterator<Map.Entry<Integer, Double>> iterator =truereason.entrySet().iterator();
        return iterator.next().getKey();
    }

    @Override
    public double getValue() {
        refreshValue();
        return value;
    }
}
