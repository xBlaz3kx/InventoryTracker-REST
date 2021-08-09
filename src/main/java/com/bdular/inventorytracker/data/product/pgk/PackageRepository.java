package com.bdular.inventorytracker.data.product.pgk;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends MongoRepository<Package, String> {

    Package findByBarcode(String barcode);

    Package findPackageByProductReference_ID(String ID);

    Package findPackageByProductReference_Barcode(String barcode);

}
