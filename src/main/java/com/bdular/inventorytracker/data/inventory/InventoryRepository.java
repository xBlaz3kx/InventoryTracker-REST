package com.bdular.inventorytracker.data.inventory;

import com.bdular.inventorytracker.data.inventory.data.InventoryReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryReport, String> {
}
