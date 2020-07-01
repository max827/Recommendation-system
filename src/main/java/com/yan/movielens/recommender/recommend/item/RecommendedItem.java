/**
 * Copyright (C) 2016 LibRec
 * <p>
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package com.yan.movielens.recommender.recommend.item;

/**
 * 推荐物品
 */
public interface RecommendedItem {

    /**
     * @return 物品ID
     */
    Integer getItemId();

    /**
     * @return 返回推荐原因
     */
    Integer getTrueReason();

    /**
     * 设置权重
     * @param id
     * @param weight
     */
    void setReason(int id, double weight);

    /**
     * 获取相似度值
     * @return
     */
    double getValue();

}
