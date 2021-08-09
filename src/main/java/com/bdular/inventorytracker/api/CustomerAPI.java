package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.services.CustomerService;
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
@RequestMapping(value = "api/customers", produces = {APPLICATION_JSON_VALUE})
@Validated
@Api(value = "Customer API")
public class CustomerAPI {

    @Autowired
    CustomerService customerService;

    @GetMapping(value = "/customer/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get customer with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public @ResponseBody
    ResponseEntity<Customer> getCustomer(@ApiParam(required = true, value = "Customer ID") @PathVariable String id) {
        return new ResponseEntity<>(customerService.getCustomer(id), OK);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all customers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), OK);
    }

    @PostMapping(value = "/customer/add", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addNewCustomer(@ApiParam(required = true, value = "Valid Customer") @Valid @RequestBody Customer customer) {
        customerService.addNewCustomer(customer);
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping(value = "/customer/{id}/send_invoice", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Sends invoice to customer if customer has an email")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> sendInvoiceToCustomer(@ApiParam(required = true, value = "Customer ID") @PathVariable String id,
                                                        @ApiParam(required = true, value = "Order ID") @RequestBody String orderID) {
        customerService.sendInvoiceToCustomer(id, orderID);
        return new ResponseEntity<>(CREATED);
    }

    @DeleteMapping(value = "/customer/{id}/delete", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Delete customer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> deleteCustomer(@ApiParam(required = true, value = "Customer ID") @PathVariable String id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(ACCEPTED);
    }

    @PostMapping(value = "/import-from-excel", consumes = MULTIPART_FORM_DATA_VALUE, produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Import customers dynamically from an Excel file")
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
            customerService.importCustomersFromExcel(file);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
}
