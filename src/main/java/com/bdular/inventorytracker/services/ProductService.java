package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.batch.products.ProductImportBatch;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.product.data.ProductRepository;
import com.bdular.inventorytracker.data.supplier.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import static com.bdular.inventorytracker.utils.DateHelper.dateFormat;
import static java.lang.String.format;

@Service
@Validated
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    SupplierRepository supplierRepository;
    //@Autowired
    //ProductImportBatch importBatch;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(@NotNull String id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void addNewProduct(@Valid Product product) {
        productRepository.insert(product);
        supplierRepository.findById(product.getSupplier().getID()).map(supplier -> {
            supplier.getProducts().add(product);
            return supplierRepository.save(supplier);
        }).orElseThrow();
    }

    @Transactional
    public Product updateSKU(@NotNull String id, @NotNull int SKU) {
        return productRepository.findById(id).map(product -> {
            product.setSKU(SKU);
            return productRepository.save(product);
        }).orElseThrow();
    }

    @Transactional
    public void updateSKUByBarcode(@NotNull String barcode, @NotNull int SKU) {
        Product product = productRepository.findDistinctByBarcode(barcode);
        product.setSKU(SKU);
        productRepository.save(product);
    }

    public Product findProductByBarcode(String barcode) {
        return productRepository.findDistinctByBarcode(barcode);
    }

    @Transactional
    public void importProductFromExcel(MultipartFile file) throws Exception {
        String fileName = format("%s %s %s", "Products\\", file.getOriginalFilename(), dateFormat.format(new Date()));
        File file1 = new File(fileName);
        file1.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(file.getBytes());
        outputStream.close();
        FileSystemResource excelFile = new FileSystemResource(file1);
       // importBatch.importProducts(excelFile.getPath());
    }
}
