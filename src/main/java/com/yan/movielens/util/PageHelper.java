package com.yan.movielens.util;

import java.util.ArrayList;
import java.util.List;

public class PageHelper<K> {
    private List<K> data;
    private Integer pageSize=0;
    private List<List<K>> result;

    /**
     * 构造函数,并直接在构造时对数据进行分页
     * @param data 需要分页的数据
     * @param pageSize 每一页数据的大小
     */
    public PageHelper(List<K> data,Integer pageSize){
        this.data=data;
        this.pageSize=pageSize-1;
        split();
    }

    private void split(){
        result=CommonUtils.splitList(data,pageSize+1);
    }

    /**
     * 根据传进来的页面索引获得页面的内容
     * @param newPageIndex 页面索引，从1开始
     * @return 页面内容
     */
    public List<K> getPage(Integer newPageIndex){
        int pageIndex=newPageIndex-1;
        int size=getPageNum();
        if(pageIndex<0){
            pageIndex=0;
        }else if(pageIndex>=size){
            pageIndex=size-1;
        }
        if(data.size()==0)
            return new ArrayList<K>();
        return result.get(pageIndex);
    }

    public Integer getDataSize(){
        return data.size();
    }

    public Integer getPageNum(){
        return result.size();
    }
}
