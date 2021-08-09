package com.bdular.inventorytracker.data.supplier;

import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.supplier.data.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {

    List<Supplier> findAllByProductsContainsOrderByCompanyName(Product productRef);

    Supplier findByProductsContainsOrderByCompanyName(Product productRef);


}
