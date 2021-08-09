package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.product.data.ProductRepository;
import com.bdular.inventorytracker.data.product.pgk.Package;
import com.bdular.inventorytracker.data.product.pgk.PackageRepository;
import com.bdular.inventorytracker.data.supplier.SupplierOrderRepository;
import com.bdular.inventorytracker.data.supplier.SupplierOrderTemplateRepository;
import com.bdular.inventorytracker.data.supplier.SupplierRepository;
import com.bdular.inventorytracker.data.supplier.data.DeliveryReport;
import com.bdular.inventorytracker.data.supplier.data.Supplier;
import com.bdular.inventorytracker.data.supplier.data.SupplierOrder;
import com.bdular.inventorytracker.data.supplier.data.SupplierOrderTemplate;
import com.bdular.inventorytracker.utils.documents.pdf.supplier.DeliveryDocument;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.bdular.inventorytracker.data.order.data.OrderStatus.FULFILLED;
import static com.bdular.inventorytracker.data.order.data.OrderStatus.SUBMITTED;
import static com.bdular.inventorytracker.utils.ResourceUtil.getDeliveryReportFilePath;
import static java.lang.String.valueOf;
import static org.bson.BsonBinarySubType.BINARY;

@Service
@Validated
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SupplierOrderRepository supplierOrderRepository;
    @Autowired
    SupplierOrderTemplateRepository templateRepository;

    public Supplier getSupplier(@NotNull String id) {
        return supplierRepository.findById(id).orElseThrow();
    }

    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public List<SupplierOrder> getAllSupplierOrders() {
        return supplierOrderRepository.findAll();
    }

    public SupplierOrder getSupplierOrder(String id) {
        return supplierOrderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void createSupplierOrder(@NotNull String id, @Valid SupplierOrder order) {
        supplierRepository.findById(id).map(supplier -> {
            order.setSupplier(supplier);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(SUBMITTED);
            return supplier;
        }).orElseThrow();
    }

    @Transactional
    public void newDeliveryReport(@Valid DeliveryReport deliveryReport) {
        SupplierOrder order = deliveryReport.getSupplierOrder();
        order.setReport(deliveryReport);
        order.setStatus(FULFILLED);
        supplierOrderRepository.save(order);
        Map<Package, Integer> numPackages = deliveryReport.getPackagesTotal();
        Map<Package, Integer> productsTotal = deliveryReport.getProductsTotal();
        deliveryReport.getPackages().forEach(aPackage -> {
            //update each product
            packageRepository.findById(aPackage.getID()).flatMap(aPackage1 ->
                    productRepository.findById(valueOf(aPackage1.getProductReference())))
                    .map(product -> {
                        product.setStock(product.getStock() + productsTotal.get(aPackage));
                        product.setLastUpdated(LocalDateTime.now());
                        productRepository.save(product);
                        aPackage.setReceived(aPackage.getReceived() + numPackages.get(aPackage));
                        aPackage.setLastReceived(LocalDateTime.now());
                        return packageRepository.save(aPackage);
                    });
        });
    }

    public DeliveryReport getDeliveryReport(@NotNull String id) {
        return supplierOrderRepository.findByReport_ID(id).getReport();
    }

    @Transactional
    public void deleteDeliveryReport(@NotNull String id) {
        SupplierOrder order = supplierOrderRepository.findSupplierOrderByReport_ID(id);
        order.getReport().getPackages().forEach(aPackage -> {

        });
        order.setReport(null);
        supplierOrderRepository.save(order);
    }

    public List<DeliveryReport> getDeliveryReports(@NotNull String id) {
        ArrayList<DeliveryReport> reports = new ArrayList<>();
        supplierRepository.findById(id).map(supplier -> {
            supplier.getOrders().forEach(supplierOrder -> reports.add(supplierOrder.getReport()));
            return supplier;
        }).orElseThrow();
        reports.removeIf(Objects::isNull);
        return reports;
    }

    public List<DeliveryReport> getAllDeliveryReports() {
        List<DeliveryReport> reports = new ArrayList<>();
        supplierOrderRepository.findAll().forEach(supplierOrder -> reports.add(supplierOrder.getReport()));
        return reports;
    }

    @Transactional
    public FileSystemResource getDeliveryReportPDF(@NotNull String id) throws IOException {
        DeliveryReport report = supplierOrderRepository.findByReport_ID(id).getReport();
        String filePath = getDeliveryReportFilePath(report);
        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        if (!fileSystemResource.exists()) {
            DeliveryDocument pdf = new DeliveryDocument(report);
            fileSystemResource = pdf.getPDF();
        }
        return fileSystemResource;
    }

    @Transactional
    public void addSupplierProduct(@NotNull String id, @Valid ArrayList<Product> productReferences) {
        supplierRepository.findById(id).map(supplier -> {
            supplier.getProducts().addAll(productReferences);
            return supplierRepository.save(supplier);
        });
    }

    @Transactional
    public Supplier addSupplierOrderTemplate(@NotNull String id, @NotNull MultipartFile file) {
        return supplierRepository.findById(id).map(supplier -> {
            try {
                SupplierOrderTemplate template = new SupplierOrderTemplate(supplier);
                template.setFile(new Binary(BINARY, file.getBytes()));
                templateRepository.insert(template);
                supplier.setOrderTemplate(template);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return supplierRepository.save(supplier);
        }).orElseThrow();
    }

}
