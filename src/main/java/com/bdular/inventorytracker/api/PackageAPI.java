package com.bdular.inventorytracker.api;

import com.bdular.inventorytracker.data.product.pgk.Package;
import com.bdular.inventorytracker.services.PackageService;
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
@RequestMapping("api/packages")
@Validated
@Api(value = "Package API")
public class PackageAPI {

    @Autowired
    PackageService service;

    @PostMapping(value = "/new", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add new package")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> addNewPackage(@ApiParam(required = true, value = "A valid package") @Valid @RequestBody Package pkg) {
        service.addNewPackage(pkg);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping(value = "/package/{id}/", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Update package properties")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<String> updatePackage(@ApiParam(required = true, value = "Package ID") @PathVariable String id,
                                                @ApiParam(value = "Package weight") @RequestParam(required = false) double weight,
                                                @ApiParam(value = "Package height") @RequestParam(required = false) double height,
                                                @ApiParam(value = "Package width") @RequestParam(required = false) double width,
                                                @ApiParam(value = "Package depth") @RequestParam(required = false) double depth) {
        service.updatePackage(id, weight, height, width, depth);
        return new ResponseEntity<>(OK);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get all packages")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Package.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<Package>> getPackages() {
        return new ResponseEntity<>(service.getPackages(), OK);
    }

    @GetMapping(value = "/package/{id}", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get package with ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Package.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Package> getPackage(@ApiParam(required = true, value = "Package ID") @PathVariable String id) {
        return new ResponseEntity<>(service.getPackage(id), OK);
    }

    @GetMapping(value = "/package", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get package with barcode")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Package.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Package> getPackageByBarcode(@ApiParam(required = true, value = "Package barcode") @RequestParam String barcode) {
        return new ResponseEntity<>(service.getPackageByBarcode(barcode), OK);
    }

    @GetMapping(value = "/package/product_barcode", produces = {APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get package with barcode")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Package.class),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Package> getPackageWithProductBarcode(@ApiParam(required = true, value = "Product barcode from package") @RequestParam String barcode) {
        return new ResponseEntity<>(service.getPackageWithProductBarcode(barcode), OK);
    }
}
