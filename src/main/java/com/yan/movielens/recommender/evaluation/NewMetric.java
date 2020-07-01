package com.yan.movielens.recommender.evaluation;


import com.yan.movielens.recommender.recommend.Recommender;
import com.yan.movielens.recommender.recommend.RecommenderContext;
import com.yan.movielens.recommender.recommend.factory.impl.RecommendItemImplFactory;
import com.yan.movielens.recommender.recommend.impl.ItemCFRecommender;
import com.yan.movielens.recommender.recommend.item.RecommendedItem;
import com.yan.movielens.recommender.similarity.RecommenderSimilarity;
import com.yan.movielens.recommender.structure.Matrix;

import java.util.*;


public class NewMetric {
    private Matrix test;
    private Matrix train;
    private RecommenderSimilarity similarity;
    private Integer k;
    private Integer n;
    private Map<Integer, List<RecommendedItem>> rank;


    public NewMetric(Integer n, Integer k, Matrix train, Matrix test, RecommenderSimilarity similarity){
        this.n=n;
        this.k=k;
        this.train=train;
        this.test=test;
        this.similarity=similarity;
        rank=new HashMap<Integer, List<RecommendedItem>>();

        getRecommendAndItemList();
//        show();
    }




    //为了防止下面的评价指标重复计算，所以需要在这里一次性算出所有测试集用户的推荐列表放入rank中
    private void getRecommendAndItemList(){
        //获得用户列表
        Set<Integer> userList=test.rowIndex();

        //先对相似度矩阵进行初始化
        this.similarity.simialarityInit();

        for(Integer userId:userList){
            //获取在训练集里这个用户的推荐列表
            RecommenderContext recommenderContext=new RecommenderContext(train,similarity,userId);

            Recommender recommender=new ItemCFRecommender(recommenderContext,new RecommendItemImplFactory(),k);
            recommender.train();
            List<RecommendedItem> recommendedItemList = recommender.recommendTopN(n);

            rank.put(userId,recommendedItemList);
        }
    }

    public void show(){
        for(Map.Entry<Integer,List<RecommendedItem>> entry:rank.entrySet()){
            int userID=entry.getKey();
            System.out.println("用户Id："+userID);
            List<RecommendedItem> list=entry.getValue();
            for(RecommendedItem item:list){
                System.out.print("Id："+item.getItemId()+"..");
            }
            System.out.println();
            System.out.println("----------------------");
        }
    }


    public Map<Integer, List<RecommendedItem>> getRank(){
        return rank;
    }

    //判断一个用户的推荐列表中有多少和测试集中的实际物品重合，即预测正确的物品个数
    private Integer retainall(Integer userId){
        int sum = 0;
        List<RecommendedItem> recommendedItemList = rank.get(userId);
        Set<Integer> items= test.row(userId).indexSet();

        for(RecommendedItem recommendedItem:recommendedItemList){
            int itemId=recommendedItem.getItemId();
            if(items.contains(itemId)){
                sum++;
            }
        }

        return sum;
    }


    /**
     * 计算流行度
     * 相当于计算每个物品出现了几次
     * @return 一个记录了物品出现次数的Map
     */
    private Map<Integer,Integer> population(){
        Map<Integer,Integer> pop=new HashMap<Integer, Integer>();
        for(Integer userId:train.rowIndex()){
            Map<Integer,Double> usersItem=train.row(userId).returnMap();

            for(Map.Entry<Integer,Double> entry:usersItem.entrySet()){
                int itemID=entry.getKey();
                if(pop.get(itemID)==null)
                    pop.put(itemID,0);
                int num=pop.get(itemID);
                num++;
                pop.put(itemID,num);
            }
        }
        return pop;
    }

    //计算精确率
    public Float precision(){



        float all=0;
        float hit=0;

        Set<Integer> userList=test.rowIndex();
        for(Integer userId:userList){
            Integer common=retainall(userId);
            hit += common;
            all += rank.get(userId).size();
        }


        return (hit/all)*100;
    }

    //计算召回率
    public Float recall(){
        float all=0;
        float hit=0;

        Set<Integer> userList=test.rowIndex();
        for(Integer userId:userList){
            Integer common=retainall(userId);
            hit += common;
            all += test.row(userId).indexSet().size();
        }

        return (hit/all)*100;
    }

    //计算覆盖率
    public Float coverage(){
        Set<Integer> rankItem=new HashSet<Integer>();

        //获得用户列表
        Set<Integer> userList=test.rowIndex();

        for(Integer userid:userList){

            List<RecommendedItem> recommendedItemList = rank.get(userid);
            for (RecommendedItem item:recommendedItemList){
                rankItem.add(item.getItemId());
            }
        }


        int itemnum=train.columnIndex().size();
        int ranknum=rankItem.size();

        return ((float)ranknum/itemnum)*100;
    }

    public double popularity(){
        Map<Integer,Integer> pop=population();
        double rankpop=0;
        int num=0;

        List<RecommendedItem> recommendedItemList;
        Set<Integer> userlist=test.rowIndex();

        for(Integer userId:userlist){
            recommendedItemList = rank.get(userId);
            for (RecommendedItem item:recommendedItemList){
                rankpop=rankpop+Math.log(1+pop.get(item.getItemId()));
                num++;
            }
        }
        return rankpop/num;
    }

    public void result(){
        float pre=precision();
        float recall=recall();
        float cov=coverage();
        double pop=popularity();
        System.out.println("精确率为："+pre+" 召回率为："+recall+" 覆盖率为："+cov+" 流行度为："+pop);
    }

}
