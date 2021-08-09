package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.order.ConsignmentRepository;
import com.bdular.inventorytracker.data.order.data.ConsignmentOrder;
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
import javax.validation.constraints.Size;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.bdular.inventorytracker.data.order.data.OrderStatus.CONSIGNED;
import static com.bdular.inventorytracker.data.order.data.OrderStatus.FULFILLED;
import static com.bdular.inventorytracker.utils.ResourceUtil.getOrderFilePath;

@Service
@Validated
public class ConsignmentService {

    @Autowired
    ConsignmentRepository consignmentRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;

    public ConsignmentOrder getConsignment(@NotNull String id) {
        return consignmentRepository.findById(id).orElseThrow();
    }

    public List<ConsignmentOrder> getConsignedOrders() {
        return consignmentRepository.findAllByStatusEqualsConsigned();
    }

    public List<ConsignmentOrder> getConsignedOrdersFromCustomer(String ID) {
        return consignmentRepository.findAllByCustomerReference_ID(ID);
    }

    @Transactional
    public ConsignmentOrder addNewConsignment(@Valid ConsignmentOrder order) {
        sellerRepository.findById(order.getSellerReference().getID()).map(seller -> {
            order.setReference(seller.getConsecutiveOrderNumber());
            seller.setConsecutiveOrderNumber(seller.getConsecutiveOrderNumber() + 1);
            return sellerRepository.save(seller);
        }).orElseThrow();
        order.setStatus(CONSIGNED);
        order.setTimestamp(LocalDateTime.now());
        Map<Product, Integer> numProducts = order.getItemsConsigned();
        order.getProductList().forEach(reference ->
                productRepository.findById(reference.getID()).map(product -> {
                    product.setStock(product.getStock() - numProducts.get(product));
                    product.setLastUpdated(LocalDateTime.now());
                    return productRepository.save(product);
                }).orElseThrow());
        customerRepository.findById(order.getCustomerReference().getID()).map(customer -> {
            customer.setNumberOfOrders(customer.getNumberOfOrders() + 1);
            return customerRepository.save(customer);
        }).orElseThrow();
        return consignmentRepository.insert(order);
    }

    @Transactional
    public ConsignmentOrder closeConsignment(@NotNull String id, @NotNull @Size(min = 1) HashMap<Product, Integer> products) {
        return consignmentRepository.findById(id).map(consignmentOrder -> {
            if (consignmentOrder.getStatus() == CONSIGNED) {
                consignmentOrder.setProductNumbers(products);
                Map<Product, Integer> consignedItems = consignmentOrder.getItemsConsigned();
                products.keySet().forEach(product -> productRepository.findById(product.getID()).map(product1 -> {
                    product.setStock(product.getStock() - (consignedItems.get(product1) - products.get(product1)));
                    product.setSold(product.getSold() + (consignedItems.get(product1) - products.get(product1)));
                    product.setLastUpdated(LocalDateTime.now());
                    return productRepository.save(product1);
                }).orElseThrow());
                consignmentOrder.setStatus(FULFILLED);
            }
            return consignmentRepository.save(consignmentOrder);
        }).orElseThrow();
    }

    @Transactional
    public FileSystemResource getConsignmentOrderPDF(@NotNull String id) {
        AtomicReference<FileSystemResource> file = new AtomicReference<>(null);
        consignmentRepository.findById(id).ifPresent(order -> {
            try {
                String fileName = getOrderFilePath(order);
                FileSystemResource orderPDF = new FileSystemResource(fileName);
                if (!orderPDF.exists()) {
                    OrderDocument<ConsignmentOrder> pdf = new OrderDocument<>(order);
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
}
