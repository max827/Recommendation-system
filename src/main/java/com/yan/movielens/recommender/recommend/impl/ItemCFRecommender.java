package com.yan.movielens.recommender.recommend.impl;



import com.yan.movielens.recommender.recommend.AbstractRecommender;
import com.yan.movielens.recommender.recommend.RecommenderContext;
import com.yan.movielens.recommender.recommend.factory.RecommendItemFactory;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.recommender.util.MapUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 基于物品的协同过滤算法
 */
public class ItemCFRecommender extends AbstractRecommender {

    public ItemCFRecommender(RecommenderContext context, RecommendItemFactory recommendItemFactory, int k) {
        super(context, recommendItemFactory,k);
    }

    @Override
    public void trainModel(Integer userId, Matrix trainData, RecommenderSimilarity similarity) {
        //获取这个用户过去的物品列表
        Vector userItems = trainData.row(userId);

        Map<Integer,Double> map=userItems.returnMap();
        for(Map.Entry<Integer,Double> entry1:map.entrySet()){
            System.out.print(" 物品:"+entry1.getKey()+" 打分："+entry1.getValue());
        }
        System.out.println();

        Map<Integer, RecommendedItem> recommendedItemMap=new HashMap<Integer, RecommendedItem>();

        for(Integer itemId:userItems.indexSet()){
            double preferences = userItems.get(itemId);

            Vector simItems=similarity.getSim().row(itemId);
            //获取前K个相似物品，个人认为主要作用在于减少计算量
            Map<Integer,Double> simItemKth= MapUtils.sortByValue(simItems.returnMap(),k);

            for(Map.Entry<Integer,Double> entry:simItemKth.entrySet()){
                int simItemId=entry.getKey();
                //如果这个物品本来就在用户的列表中，那就不用推荐了
                if(userItems.contains(simItemId)){continue;}

                double sim = entry.getValue();
                double weight=preferences*sim;

                if(recommendedItemMap.get(simItemId)==null){
                    recommendedItemMap.put(simItemId,recommendItemFactory.createRecommendItem(simItemId));
                }
                RecommendedItem recommendedItem = recommendedItemMap.get(simItemId);
                recommendedItem.setReason(itemId,weight);


                if(!recommendedItemList.contains(recommendedItem)) {
                    recommendedItemList.add(recommendedItem);
                }
            }
        }
    }

}
