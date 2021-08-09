package com.bdular.inventorytracker.data.product.data;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findDistinctByBarcode(String barcode);

    boolean existsByBarcode(String barcode);

    List<Product> findTop10ByOrderBySold();

}
