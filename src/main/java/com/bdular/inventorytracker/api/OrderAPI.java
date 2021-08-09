package com.bdular.inventorytracker.api;


import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.payment.PaymentType;
import com.bdular.inventorytracker.services.OrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@RestController
@Validated
@RequestMapping(value = "/api/orders", produces = {APPLICATION_JSON_VALUE})
@Api(value = "Order API")
public class OrderAPI {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/order/new", produces = {APPLICATION_JSON_VALUE})
    @Transactional
    @Async
    @ApiOperation(value = "Add new order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public CompletableFuture<ResponseEntity<String>> addNewOrder(@ApiParam(required = true, name = "Order") @Valid @RequestBody Order order) {
        Order retOrder = orderService.addNewOrder(order);
        return CompletableFuture.completedFuture(new ResponseEntity<>(CREATED));
    }

    @PutMapping(value = "/order/{id}/update", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Update order's status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> updateOrderStatus(@ApiParam(required = true, name = "Order ID") @PathVariable String id, int status) {
        Order retOrder = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(OK);
    }

    @PutMapping(value = "/order/{id}/payment", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Update order's status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> updatePaymentInfo(@ApiParam(required = true, name = "Order ID") @PathVariable String id,
                                                    @ApiParam(required = true, name = "Payment types with amounts paid for each type")
                                                    @RequestBody HashMap<PaymentType, Double> payments) {
        Order retOrder = orderService.updateOrderPayment(id, payments);
        return new ResponseEntity<>(OK);
    }

    @GetMapping(value = "/order/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get order with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Order.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Order> getOrder(@ApiParam(required = true, name = "Order ID") @PathVariable String id) {
        return new ResponseEntity<>(orderService.getOrder(id), OK);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all orders")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Order.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(orderService.getOrders(), OK);
    }

    @GetMapping(value = "/customer/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all orders from a customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Order.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Order>> getOrdersFromCustomer(@ApiParam(required = true, value = "Customer ID") @PathVariable String id) {
        return new ResponseEntity<>(orderService.getOrdersFromCustomer(id), OK);
    }

    @GetMapping(value = "/order/{id}/pdf", produces = APPLICATION_PDF_VALUE)
    @Transactional
    @ApiOperation(value = "Create PDF of order with id", produces = APPLICATION_PDF_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<?> getOrderPDF(@ApiParam(required = true, name = "Order ID") @PathVariable String id) {
        FileSystemResource file = orderService.getOrderPDF(id);
        return ResponseEntity.ok()
                .contentType(APPLICATION_OCTET_STREAM)
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
