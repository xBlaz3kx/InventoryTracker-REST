package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.user.seller.data.Expenses;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import com.bdular.inventorytracker.services.SellerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/sellers")
@Validated
@Api(tags = "Seller API")
public class SellerAPI {

    @Autowired
    SellerService sellerService;

    @GetMapping(value = "/seller/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get seller with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Seller.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Seller> getSeller(@ApiParam(required = true, value = "Seller ID") @PathVariable String id) {
        return new ResponseEntity<>(sellerService.getSeller(id), CREATED);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all sellers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Seller.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Seller>> getAllSellers() {
        return new ResponseEntity<>(sellerService.getSellers(), OK);
    }

    @PostMapping(value = "/seller/new", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new seller")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addNewSeller(@ApiParam(required = true, value = "A valid seller") @Valid @RequestBody Seller seller) {
        sellerService.insertSeller(seller);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping(value = "/seller/{id}/add_expense", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new expense to seller")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addNewExpense(@ApiParam(required = true, value = "Seller ID") @PathVariable String id,
                                                @ApiParam(required = true, value = "Valid expense(s)") @Valid @RequestBody Expenses expense) {
        sellerService.addNewExpense(id, expense);
        return new ResponseEntity<>(CREATED);
    }
}
