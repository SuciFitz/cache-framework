package com.sucifitz.eshop.cache.model;

import lombok.Data;

/**
 * 商品信息
 *
 * @author Sucifitz
 * @date 2021/3/28 19:48
 */
@Data
public class ProductInfo {

    private Long id;
    private String name;
    private Double price;
    /**
     * 图片列表
     */
    private String pictureList;
    /**
     * 规格
     */
    private String specification;
    /**
     * 额外服务
     */
    private String service;
    /**
     * 颜色
     */
    private String color;
    /**
     * 尺寸
     */
    private String size;
}