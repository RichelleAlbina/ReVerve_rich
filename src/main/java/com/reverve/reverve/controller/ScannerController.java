package com.reverve.reverve.controller;

import com.reverve.reverve.domain.ProductScan;
import com.reverve.reverve.service.ProductScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScannerController {
    @Autowired
    private ProductScanService productScanService;

    @GetMapping("/scanner")
    public String scanner() {
        return "scanner";
    }

    @PostMapping("/scan")
    public String scanProduct(ProductScan scan, Model model) {
        productScanService.saveScan(scan);
        model.addAttribute("product", scan);
        return "scan_result";
    }
}