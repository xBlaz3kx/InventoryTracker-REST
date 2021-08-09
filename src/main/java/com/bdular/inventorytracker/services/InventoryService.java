package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.inventory.InventoryRepository;
import com.bdular.inventorytracker.data.inventory.data.InventoryReport;
import com.bdular.inventorytracker.data.product.data.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.math.BigDecimal.ZERO;

@Service
@Validated
public class InventoryService {

    @Autowired
    InventoryRepository repository;

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public void newInventoryReport(@Valid InventoryReport report) throws Exception {
        report.setDate(LocalDateTime.now());
        AtomicReference<BigDecimal> expectedValue = new AtomicReference<>(ZERO);
        AtomicReference<BigDecimal> actualValue = new AtomicReference<>(ZERO);
        report.getProducts().forEach(product -> productRepository.findById(product.getID()).map(product2 -> {
            Integer expectedStock = report.getExpectedStock().get(product2);
            Integer actualStock = report.getCurrentStock().get(product2);
            expectedValue.set(expectedValue.get().add(product2.getPrice().add(BigDecimal.valueOf(expectedStock))));
            actualValue.set(actualValue.get().add(product2.getPrice().add(BigDecimal.valueOf(actualStock))));
            return product2;
        }));
        repository.insert(report);
    }

    public List<InventoryReport> getAllInventoryReports() {
        return repository.findAll();
    }

}
