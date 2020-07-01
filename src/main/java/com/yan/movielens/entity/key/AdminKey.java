package com.yan.movielens.entity.key;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AdminKey implements Serializable {
    @Column(name = "admind")
    private Integer admind;

    @Column(name = "Id")
    private Integer thatId;
    // 省略setter,getter方法

    @Override
    public String toString() {
        return "PeopleKey [adminId=" + admind + ", thatId=" + thatId + "]";
    }
}
