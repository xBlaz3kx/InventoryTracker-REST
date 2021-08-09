package com.bdular.inventorytracker.api;


import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.services.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/products")
@Validated
@Api(value = "Product API")
public class ProductAPI {

    @Autowired
    ProductService service;

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all products")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Product.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), OK);
    }

    @GetMapping(value = "/product/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get product with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Product.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Product> getProduct(@ApiParam(required = true, value = "Product id") @PathVariable String id) {
        return new ResponseEntity<>(service.getProduct(id), OK);
    }

    @GetMapping(value = "/product", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get product with barcode")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Product.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Product> getProductFromBarcode(@ApiParam(required = true, value = "Product barcode") @RequestParam String barcode) {
        return new ResponseEntity<>(service.findProductByBarcode(barcode), OK);
    }

    @PostMapping(value = "/product/new", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new product")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addNewProduct(@ApiParam(required = true, value = "Valid product") @Valid @RequestBody Product product) {
        service.addNewProduct(product);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping(value = "/product/{id}/updateSKU", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Update SKU")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> updateProductSKU(@ApiParam(required = true, value = "Product id") @PathVariable String id,
                                                   @ApiParam(required = true, value = "New stock unit value") @RequestParam int SKU) {
        service.updateSKU(id, SKU);
        return new ResponseEntity<>(ACCEPTED);
    }

    @PostMapping(value = "/import-from-excel", consumes = MULTIPART_FORM_DATA_VALUE, produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Import products dynamically from a Excel file")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 415, message = "Unsupported file type"),
    })
    public ResponseEntity<String> addProductsFromExcel(@ApiParam(required = true, value = "An Excel file with products") @RequestParam("file") @NotNull MultipartFile file) {
        if (!Objects.requireNonNull(file.getOriginalFilename()).contains(".xlsx")) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
        try {
            service.importProductFromExcel(file);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }

}
