package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.inventory.data.InventoryReport;
import com.bdular.inventorytracker.services.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/inventory")
@Validated
@Api(value = "Inventory API")
public class InventoryAPI {

    @Autowired
    InventoryService service;

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all inventory reports")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<InventoryReport>> getAllReports() {
        return new ResponseEntity<>(service.getAllInventoryReports(), OK);
    }

    @PostMapping(value = "/new", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create new inventory report")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> newInventory(@RequestBody InventoryReport report) {
        try {
            service.newInventoryReport(report);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
}


