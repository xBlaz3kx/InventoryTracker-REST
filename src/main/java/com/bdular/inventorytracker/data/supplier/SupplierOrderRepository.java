package com.bdular.inventorytracker.data.supplier;

import com.bdular.inventorytracker.data.supplier.data.Supplier;
import com.bdular.inventorytracker.data.supplier.data.SupplierOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface SupplierOrderRepository extends MongoRepository<SupplierOrder, String> {

    List<SupplierOrder> findBySupplier(@NotEmpty Supplier supplier);

    SupplierOrder findByReport_ID(String id);

    SupplierOrder findSupplierOrderByReport_ID(String report_ID);
}
