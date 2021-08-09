package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.services.StatisticsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/statistics")
@Api(value = "Statistics API")
public class StatisticsAPI {

    @Autowired
    StatisticsService statisticsService;

    @GetMapping(value = "/top-sellers", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get top selling items")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Product.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Product>> getTopSellers() {
        return new ResponseEntity<>(statisticsService.getTopSellingProducts(), OK);
    }

    @GetMapping(value = "/top-customers", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get top customers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Customer>> getTopCustomers() {
        return new ResponseEntity<>(statisticsService.getTopCustomers(), OK);
    }

    @GetMapping(value = "/avg-order-value", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get seller with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Double> getAvgOrderValue() {
        return new ResponseEntity<>(statisticsService.getAvgOrderValue(), OK);
    }

    @GetMapping(value = "/customer/{id}/avg-order-value", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get seller with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Double> getAvgOrderValue(@ApiParam(name = "Customer ID") @PathVariable String id) {
        return new ResponseEntity<>(statisticsService.getAvgCustomerOrderValue(id), OK);
    }

}
