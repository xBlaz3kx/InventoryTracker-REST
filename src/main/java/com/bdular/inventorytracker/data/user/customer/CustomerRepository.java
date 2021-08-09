package com.bdular.inventorytracker.data.user.customer;

import com.bdular.inventorytracker.data.user.customer.data.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByOrderByOrderedTotal();

    List<Customer> findFirst10ByOrderByOrderedTotalDesc();

    List<Customer> findFirst10ByOrderByOrderedTotalDescNumberOfOrdersDesc();

}
