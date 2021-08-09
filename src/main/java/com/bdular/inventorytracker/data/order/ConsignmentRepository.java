package com.bdular.inventorytracker.data.order;

import com.bdular.inventorytracker.data.order.data.ConsignmentOrder;
import com.bdular.inventorytracker.data.order.data.OrderStatus;
import com.bdular.inventorytracker.data.payment.PaymentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ConsignmentRepository extends MongoRepository<ConsignmentOrder, String> {

    List<ConsignmentOrder> findAllByStatusEqualsOrderByTimestamp(@NotNull OrderStatus status);

    List<ConsignmentOrder> findAllByPaymentInfo_PaymentTypesEqualsOrderByTimestamp(@NotNull ArrayList<PaymentType> payment);

    List<ConsignmentOrder> findAllByCustomerReference_ID(@NotNull String customerReference_ID);

    @Query("{'status': 3, 'timestamp': new Date()}")
    List<ConsignmentOrder> findAllByStatusEqualsConsigned();
}
