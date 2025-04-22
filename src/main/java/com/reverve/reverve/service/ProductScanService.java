package com.reverve.reverve.service;

import com.reverve.reverve.domain.ProductScan;
import com.reverve.reverve.repository.ProductScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductScanService {
    @Autowired
    private ProductScanRepository productScanRepository;

    public ProductScan saveScan(ProductScan scan) {
        return productScanRepository.save(scan);
    }
}