package com.yan.movielens.recommender.filter;

import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.structure.Matrix;
import com.yan.movielens.recommender.structure.Vector;
import com.yan.movielens.recommender.util.MapUtils;


import java.util.*;

public class FilterImpl implements Filter{
    private List<RecommendedItem> recommendedItems;
    private Matrix train;
    public FilterImpl(List<RecommendedItem> recommendedItems,Matrix train){
        this.recommendedItems=recommendedItems;
        this.train=train;
    }

    /**
     * 过滤掉前num个热门物品
     * @param num 前num个热门物品
     */
    private void filterHotItems(int num){
        List<Integer> hotItemIdList=getHotItems(num);
        for(RecommendedItem recommendedItem:recommendedItems){
            int itemId=recommendedItem.getItemId();
            if(hotItemIdList.contains(itemId)){
                recommendedItems.remove(recommendedItem);
            }
        }
    }

    /**
     *获得前num个热门物品
     * @param num 前num个热门物品
     * @return 热门物品的Id
     */
    private List<Integer> getHotItems(int num){
        Set<Integer> userIds=train.rowIndex();

        Map<Integer,Integer> itemHits=new HashMap<Integer,Integer>();
        for(Integer userId:userIds){
            Set<Integer> itemIds=train.row(userId).indexSet();
            for(Integer itemId:itemIds){
                if(itemHits.get(itemId)==null){
                    itemHits.put(itemId,1);
                }
                int hit=itemHits.get(itemId);
                hit++;
                itemHits.put(itemId,hit);
            }
        }

        List<Integer> hotItemIdList= MapUtils.sortByValueList(itemHits,num,0);
        return hotItemIdList;
    }


    @Override
    public void filter() {
        filterHotItems(10);
    }

    @Override
    public List<RecommendedItem> getRecommendedItemList(int num) {
        return recommendedItems;
    }
}
