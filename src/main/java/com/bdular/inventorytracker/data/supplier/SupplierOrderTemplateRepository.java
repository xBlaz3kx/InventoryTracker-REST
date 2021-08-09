package com.bdular.inventorytracker.data.supplier;

import com.bdular.inventorytracker.data.supplier.data.SupplierOrderTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierOrderTemplateRepository extends MongoRepository<SupplierOrderTemplate, String> {
}
