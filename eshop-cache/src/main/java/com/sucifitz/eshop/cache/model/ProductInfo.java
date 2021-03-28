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


}