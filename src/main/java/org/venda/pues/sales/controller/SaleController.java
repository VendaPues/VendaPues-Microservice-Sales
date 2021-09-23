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
public class SaleController {

    private final SaleServices saleServices;


    public SaleController(@Autowired SaleServices saleServices) {
        this.saleServices = saleServices;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(saleServices.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        SaleDocument result = saleServices.findById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SaleDto saleDto) {
        return ResponseEntity.ok(saleServices.create(saleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody SaleDto saleDto, @PathVariable String id) {
        SaleDocument result = saleServices.update(id, saleDto);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        return ResponseEntity.ok(saleServices.delete(id));
    }
}
