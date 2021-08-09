package com.bdular.inventorytracker.data.order;


import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.order.data.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByCustomerReference_ID(String customerReference_ID);

    List<Order> findFirstByOrderByTotalDesc();

    List<Order> findAllByStatusEqualsOrderByTimestamp(@NotNull OrderStatus status);
}
