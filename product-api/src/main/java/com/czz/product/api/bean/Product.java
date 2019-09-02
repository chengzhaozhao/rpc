package com.czz.product.api.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chengzhzh@datangmobile.com
 * @create 2019-09-02 10:13
 */
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = -8925958063865323754L;
    private long id;
    private String name;
    private double price;
}
