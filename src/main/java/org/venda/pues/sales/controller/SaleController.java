package org.venda.pues.sales.controller;

import models.SaleDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.venda.pues.sales.service.SaleServices;
import dto.SaleDto;

@RestController
@RequestMapping("/v1/sale")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class SaleController {

    private final SaleServices saleServices;

    public SaleController(@Autowired SaleServices saleServices) {
        this.saleServices = saleServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        SaleDocument result = saleServices.findById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> create(@PathVariable String userId, @RequestBody SaleDto saleDto) {
        return ResponseEntity.ok(saleServices.create(userId, saleDto));
    }
}
