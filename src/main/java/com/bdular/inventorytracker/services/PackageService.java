package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.product.pgk.Package;
import com.bdular.inventorytracker.data.product.pgk.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class PackageService {

    @Autowired
    PackageRepository packageRepository;

    public void addNewPackage(@Valid Package pkg) {
        packageRepository.insert(pkg);
    }

    @Transactional
    public void updatePackage(@NotNull String id, double weight, double height, double width, double depth) {
        packageRepository.findById(id).map(aPackage -> {
            aPackage.setWeight(weight);
            aPackage.setHeight(height);
            aPackage.setWidth(width);
            aPackage.setDepth(depth);
            return packageRepository.save(aPackage);
        }).orElseThrow();
    }

    public List<Package> getPackages() {
        return packageRepository.findAll();
    }

    public Package getPackage(@NotNull String id) {
        return packageRepository.findById(id).orElseThrow();
    }

    public Package getPackageByBarcode(@NotNull String barcode) {
        return packageRepository.findByBarcode(barcode);
    }

    public Package getPackageWithProductBarcode(@NotNull String barcode) {
        return packageRepository.findPackageByProductReference_Barcode(barcode);
    }
}