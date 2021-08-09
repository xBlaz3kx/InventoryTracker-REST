package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.order.data.ConsignmentOrder;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.services.ConsignmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@RestController
@Validated
@RequestMapping(value = "/api/orders/consignment", produces = {APPLICATION_JSON_VALUE})
@Api(value = "Consignment Order API")
public class ConsignmentAPI {

    @Autowired
    ConsignmentService consignmentService;

    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get Consignment Order with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ConsignmentOrder.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<ConsignmentOrder> getConsignment(@ApiParam(required = true, name = "Consignment ID") @PathVariable String id) {
        return new ResponseEntity<>(consignmentService.getConsignment(id), OK);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all consigned orders")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ConsignmentOrder.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<ConsignmentOrder>> getConsignedOrders() {
        return new ResponseEntity<>(consignmentService.getConsignedOrders(), OK);
    }

    @PostMapping(value = "/new", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new Consignment Order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addNewConsignment(@ApiParam(required = true, name = "Consignment order") @Valid @RequestBody ConsignmentOrder order) {
        ConsignmentOrder order1 = consignmentService.addNewConsignment(order);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping(value = "/customer", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all consignment orders from customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ConsignmentOrder.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<ConsignmentOrder>> getConsignmentsFromCustomer(@RequestParam String customerID) {
        return new ResponseEntity<>(consignmentService.getConsignedOrdersFromCustomer(customerID), OK);
    }

    @PutMapping(value = "/{id}/close", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Close consignment with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> closeConsignment(@ApiParam(required = true, name = "Consignment ID") @PathVariable String id,
                                                   @ApiParam(required = true, name = "Products Consigned") @RequestBody HashMap<Product, Integer> products) {
        ConsignmentOrder order = consignmentService.closeConsignment(id, products);
        return new ResponseEntity<>(ACCEPTED);
    }

    @GetMapping(value = "/{id}/pdf", produces = APPLICATION_PDF_VALUE)
    @ApiOperation(value = "Get PDF of Consigned order", produces = APPLICATION_PDF_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<?> getConsignmentOrderPDF(@ApiParam(required = true, name = "Consignment ID") @PathVariable String id) {
        FileSystemResource file = consignmentService.getConsignmentOrderPDF(id);
        if (file == null || !file.exists()) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok()
                .contentType(APPLICATION_PDF)
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
