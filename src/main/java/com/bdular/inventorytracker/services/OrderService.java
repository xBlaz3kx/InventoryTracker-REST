package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.order.OrderRepository;
import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.order.data.OrderStatus;
import com.bdular.inventorytracker.data.payment.Payment;
import com.bdular.inventorytracker.data.payment.PaymentType;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.product.data.ProductRepository;
import com.bdular.inventorytracker.data.user.customer.CustomerRepository;
import com.bdular.inventorytracker.data.user.seller.SellerRepository;
import com.bdular.inventorytracker.utils.documents.pdf.order.OrderDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.bdular.inventorytracker.data.order.data.OrderStatus.*;
import static com.bdular.inventorytracker.data.payment.PaymentStatus.NOT_PAID_IN_FULL;
import static com.bdular.inventorytracker.data.payment.PaymentStatus.PAID_FULL;
import static com.bdular.inventorytracker.utils.ResourceUtil.getOrderFilePath;
import static java.math.BigDecimal.ZERO;

@Service
@Validated
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Transactional
    public Order addNewOrder(@Valid Order order) {
        sellerRepository.findById(order.getSellerReference().getID()).map(seller -> {
            order.setReference(seller.getConsecutiveOrderNumber());
            seller.setConsecutiveOrderNumber(seller.getConsecutiveOrderNumber() + 1);
            seller.setProductSoldValue(seller.getProductSoldValue().add(order.getTotal()));
            return sellerRepository.save(seller);
        }).orElseThrow();
        order.setStatus(SUBMITTED);
        order.setTimestamp(LocalDateTime.now());
        order.getPaymentInfo().setPaymentDue(LocalDateTime.now().plusDays(20));
        Map<Product, Integer> numProducts = order.getProductNumbers();
        order.getProductList().forEach(reference -> productRepository.findById(reference.getID()).map(product -> {
            product.setStock(product.getStock() - numProducts.get(product));
            product.setSold(product.getSold() + numProducts.get(product));
            product.setLastUpdated(LocalDateTime.now());
            return productRepository.save(product);
        }).orElseThrow());
        customerRepository.findById(order.getCustomerReference().getID()).map(customer -> {
            customer.setNumberOfOrders(customer.getNumberOfOrders() + 1);
            customer.setOrderedTotal(customer.getOrderedTotal().add(order.getTotal()));
            return customerRepository.save(customer);
        }).orElseThrow();
        return orderRepository.insert(order);
    }

    @Transactional
    public Order updateOrderStatus(@NotNull String id, int status) {
        AtomicReference<OrderStatus> desiredStatus = new AtomicReference<>(null);
        return orderRepository.findById(id).map(order -> {
            OrderStatus currentStatus = order.getStatus();
            if (currentStatus == SUBMITTED && status == 2) {
                desiredStatus.set(FULFILLED);
            } else if (currentStatus == CONSIGNED && status == 2) {
                desiredStatus.set(FULFILLED);
            } else if (status == 5) {
                desiredStatus.set(CANCELLED);
            }
            if (desiredStatus != null) {
                order.setStatus(desiredStatus.get());
            }
            return orderRepository.save(order);
        }).orElseThrow();
    }

    @Transactional
    public Order updateOrderPayment(@NotNull String id, HashMap<PaymentType, Double> payments) {
        return orderRepository.findById(id).map(order -> {
            if (order.getStatus() == FULFILLED) {
                LocalDateTime date = LocalDateTime.now();
                Payment payment = order.getPaymentInfo();
                payments.keySet().forEach(paymentType -> payment.getPaymentDates().put(paymentType, date));
                payments.values().forEach(value -> {
                    BigDecimal paymentAmount = BigDecimal.valueOf(value);
                    payment.getPaymentAmount().put(date, paymentAmount);
                    payment.setAmountPaid(payment.getAmountPaid().add(paymentAmount));
                    payment.setAmountOwed(payment.getAmountOwed().subtract(paymentAmount));
                });
                payment.getPaymentTypes().addAll(payments.keySet());
                if (payment.getAmountPaid().equals(order.getTotal()) && payment.getAmountOwed().equals(ZERO)) {
                    payment.setStatus(PAID_FULL);
                    order.setStatus(PAID);
                } else {
                    payment.setStatus(NOT_PAID_IN_FULL);
                }
            }
            return orderRepository.save(order);
        }).orElseThrow();
    }

    public Order getOrder(@NotNull String id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public FileSystemResource getOrderPDF(@NotNull String id) {
        AtomicReference<FileSystemResource> file = new AtomicReference<>(null);
        orderRepository.findById(id).ifPresent(order -> {
            try {
                String fileName = getOrderFilePath(order);
                FileSystemResource orderPDF = new FileSystemResource(fileName);
                if (!orderPDF.exists()) {
                    OrderDocument<Order> pdf = new OrderDocument<>(order);
                    file.set(pdf.getPDF());
                } else {
                    file.set(orderPDF);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return file.get();
    }

    public List<Order> getOrdersFromCustomer(String id) {
        return orderRepository.findAllByCustomerReference_ID(id);
    }
}
