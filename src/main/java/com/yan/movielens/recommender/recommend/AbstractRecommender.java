package com.yan.movielens.recommender.recommend;




import com.yan.movielens.recommender.recommend.factory.RecommendItemFactory;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractRecommender implements Recommender {

    protected List<RecommendedItem> recommendedItemList;
    protected RecommendItemFactory recommendItemFactory;
    protected RecommenderContext context;
    protected int k;

    public AbstractRecommender(RecommenderContext context,RecommendItemFactory recommendItemFactory,int k){
        this.k=k;
        this.context = context;
        this.recommendItemFactory=recommendItemFactory;
        this.recommendedItemList=new ArrayList<RecommendedItem>();
    }

    @Override
    public void train() {
        //从上下文中获得必要的信息
        Integer userId = context.getUserId();
        Matrix trainData = context.getTrainData();
        RecommenderSimilarity similarity = context.getSimilarity();

        trainModel(userId,trainData,similarity);

    }

    public abstract void trainModel(Integer userId,Matrix trainData,RecommenderSimilarity similarity);

    @Override
    public List<RecommendedItem> recommendList() {
        return recommendedItemList;
    }

    @Override
    public List<RecommendedItem> recommendRank() {
        Collections.sort(recommendedItemList, new Comparator<RecommendedItem>() {
            public int compare(RecommendedItem o1, RecommendedItem o2) {
                Double v1 = o1.getValue();
                Double v2 = o2.getValue();
                return v2.compareTo(v1);
            }
        });
        return recommendedItemList;
    }

    @Override
    public List<RecommendedItem> recommendTopN(int n) {
        recommendedItemList = recommendRank();


        for(RecommendedItem recommendedItem:recommendedItemList){
            System.out.print(" 物品:"+recommendedItem.getItemId()+" 兴趣："+recommendedItem.getValue()+"|");
        }
        System.out.println("-----");

        int length=recommendedItemList.size();
        return n >= length?recommendedItemList.subList(0,length):recommendedItemList.subList(0,n);
    }

    @Override
    public void updaterRecommendList(Integer userId,Integer itemId,Double perference) {
        Matrix trainData = context.getTrainData();
        //更新训练集
        trainData.set(userId,itemId,perference);
        //清空原有推荐列表
        recommendedItemList.clear();
        //重新训练
        train();
    }
}
