package org.venda.pues.sales.controller;

import dto.SaleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.venda.pues.sales.service.SaleServices;

import java.util.List;

@RestController
@RequestMapping("/v1/sale")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class SaleController {

    private final SaleServices saleServices;

    public SaleController(@Autowired SaleServices saleServices) {
        this.saleServices = saleServices;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> all(@PathVariable String userId) {
        return ResponseEntity.ok(saleServices.all(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> create(@PathVariable String userId, @RequestBody List<SaleDto> saleDto) {
        return ResponseEntity.ok(saleServices.create(userId, saleDto));
    }
}
