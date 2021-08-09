package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.supplier.data.DeliveryReport;
import com.bdular.inventorytracker.data.supplier.data.Supplier;
import com.bdular.inventorytracker.data.supplier.data.SupplierOrder;
import com.bdular.inventorytracker.services.SupplierService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("api/suppliers")
@Validated
@Api(value = "Supplier API")
public class SupplierAPI {

    @Autowired
    SupplierService supplierService;

    @GetMapping(value = "/supplier/{id}/", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get supplier with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Supplier.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Supplier> getSupplier(@ApiParam(required = true, value = "Supplier ID") @PathVariable String id) {
        return new ResponseEntity<>(supplierService.getSupplier(id), CREATED);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get suppliers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Supplier>> getSuppliers() {
        return new ResponseEntity<>(supplierService.getSuppliers(), CREATED);
    }

    @GetMapping(value = "/orders/", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get supplier orders")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<SupplierOrder>> getAllSupplierOrders() {
        return new ResponseEntity<>(supplierService.getAllSupplierOrders(), CREATED);
    }

    @GetMapping(value = "/orders/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get supplier orders")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<SupplierOrder> getSupplierOrder(@PathVariable String id) {
        return new ResponseEntity<>(supplierService.getSupplierOrder(id), CREATED);
    }

    @GetMapping(value = "/supplier/{id}/orders/new", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create a new supplier order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> createSupplierOrder(@ApiParam(required = true, value = "Supplier ID") @PathVariable String id,
                                                      @ApiParam(required = true, value = "Valid Supplier Order") @Valid @RequestBody SupplierOrder order) {
        supplierService.createSupplierOrder(id, order);
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping(value = "/new_delivery_report", produces = {APPLICATION_JSON_VALUE})
    @Transactional
    @ApiOperation(value = "Create new supplier delivery report")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> newDeliveryReport(@ApiParam(required = true, value = "Valid Delivery Report") @Valid @RequestBody DeliveryReport deliveryReport) {
        supplierService.newDeliveryReport(deliveryReport);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping(value = "/supplier/{id}/delivery_reports/", produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "Get supplier delivery reports from supplier with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = DeliveryReport.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<DeliveryReport>> getDeliveryReports(@ApiParam(required = true, value = "Supplier ID") @PathVariable String id) {
        return new ResponseEntity<>(supplierService.getDeliveryReports(id), ACCEPTED);
    }

    @GetMapping(value = "/delivery_reports/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get supplier delivery report with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = DeliveryReport.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<DeliveryReport> getDeliveryReport(@ApiParam(required = true, value = "Delivery report ID") @PathVariable String id) {
        return new ResponseEntity<>(supplierService.getDeliveryReport(id), OK);
    }

    @GetMapping(value = "/delivery_reports/", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get supplier delivery report with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = DeliveryReport.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<DeliveryReport>> getAllDeliveryReports() {
        return new ResponseEntity<>(supplierService.getAllDeliveryReports(), OK);
    }

    @DeleteMapping(value = "/delivery_reports/{id}/delete", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Delete supplier delivery report")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> deleteDeliveryReport(@ApiParam(required = true, value = "Delivery report ID") @PathVariable String id) {
        supplierService.deleteDeliveryReport(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping(value = "/delivery_reports/{id}/pdf", produces = APPLICATION_PDF_VALUE)
    @ApiOperation(value = "Get supplier delivery report PDF with delivery ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<?> getDeliveryReportPDF(@ApiParam(required = true, value = "Delivery reports from Supplier with ID") @PathVariable String id) {
        FileSystemResource fileSystemResource = null;
        try {
            fileSystemResource = supplierService.getDeliveryReportPDF(id);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().contentType(APPLICATION_PDF).body(fileSystemResource);
    }


    @PutMapping(value = "/supplier/{id}/add_product", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new product to supplier")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addSupplierProduct(@ApiParam(required = true, value = "Supplier ID") @PathVariable String id,
                                                     @ApiParam(required = true, value = "Products (references)") @Valid @RequestBody ArrayList<Product> productReferences) {
        supplierService.addSupplierProduct(id, productReferences);
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping(value = "/supplier/{id}/add_template", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new product to supplier")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addSupplierOrderTemplate(@ApiParam(required = true, value = "Supplier ID") @PathVariable String id,
                                                           @ApiParam(required = true, value = "Template file (.xslx or .pdf)") @RequestParam("file") MultipartFile file) {
        Supplier supplier = supplierService.addSupplierOrderTemplate(id, file);
        if (supplier != null) {
            return new ResponseEntity<>(CREATED);
        } else {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
}
