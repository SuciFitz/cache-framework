package com.sucifitz.eshop.inventory.vo;

import lombok.Data;

/**
 * 请求的响应
 *
 * @author Sucifitz
 * @date 2021/3/20 20:30
 */
@Data
public class Response {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public Response(String status) {
        this.status = status;
    }

    private String status;
    private String message;
}