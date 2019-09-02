package com.czz.product.api.service;

import com.czz.product.api.bean.Product;

/**
 * @author chengzhzh@datangmobile.com
 * @create 2019-09-02 10:15
 */
public interface IProductService {
    public Product queryById(long id);
}
