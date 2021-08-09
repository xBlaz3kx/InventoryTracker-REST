package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.order.OrderRepository;
import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.product.data.ProductRepository;
import com.bdular.inventorytracker.data.user.customer.CustomerRepository;
import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.data.user.seller.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;

@Service
public class StatisticsService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<Customer> getTopCustomers() {
        return customerRepository.findFirst10ByOrderByOrderedTotalDesc();
    }

    public List<Customer> getValuableCustomers() {
        return null;
    }

    public List<Product> getTopSellingProducts() {
        return productRepository.findTop10ByOrderBySold();
    }


    public Double getAvgOrderValue() {
        List<Order> orders = orderRepository.findAll();
        return calculateAvg(orders);
    }

    public List<Order> getMostExpensiveOrders() {
        return orderRepository.findFirstByOrderByTotalDesc();
    }

    public Double getAvgCustomerOrderValue(String customerId) {
        List<Order> customerOrders = orderRepository.findAllByCustomerReference_ID(customerId);
        return calculateAvg(customerOrders);
    }

    public static <S extends Order> Double calculateAvg(List<S> list) {
        AtomicReference<BigDecimal> sum = new AtomicReference<>(ZERO);
        list.forEach(order -> sum.accumulateAndGet(order.getTotal(), null));
        return sum.get().divide(valueOf(list.size()), CEILING).doubleValue();
    }
}
