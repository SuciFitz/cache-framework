package com.sucifitz.eshop.cache.model;

import lombok.Data;

/**
 * 店铺信息
 *
 * @author szh
 * @date 2021/5/4
 */
@Data
public class ShopInfo {

    private Long id;
    private String name;
    private Integer level;
    /**
     * 好评率
     */
    private String goodCommentRate;
}
