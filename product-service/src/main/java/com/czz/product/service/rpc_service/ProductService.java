package com.czz.product.service.rpc_service;

import com.czz.product.api.bean.Product;
import com.czz.product.api.service.IProductService;

/**
 * @author chengzhzh@datangmobile.com
 * @create 2019-09-02 10:29
 */
public class ProductService implements IProductService {
    @Override
    public Product queryById(long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("water");
        product.setPrice(1.0);

        return product;
    }
}
