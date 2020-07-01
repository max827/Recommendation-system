package com.yan.movielens.entity;

import com.yan.movielens.entity.key.AdminKey;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class MovieManagement {
    /**
     * 复合主键
     */
    @EmbeddedId
    private AdminKey key;

    /**
     * 操作代码
     * 1表示增加，2表示删除，3表示修改，4表示查找
     */
    @Column(name = "operation")
    private int operation;

    /**
     * 时间戳
     */
    @Column(name="timestamp",columnDefinition = "bigint")
    private long timeStamp;

    public MovieManagement(){

    }
    public MovieManagement(int adminId,int movieId,int operation,long timeStamp){
        this.key=new AdminKey();
        this.key.setAdmind(adminId);
        this.key.setThatId(movieId);
        this.operation=operation;
        this.timeStamp=timeStamp;
    }
    public MovieManagement(int adminId,int operation,long timeStamp){
        this.key=new AdminKey();
        this.key.setAdmind(adminId);

        this.operation=operation;
        this.timeStamp=timeStamp;
    }
}
