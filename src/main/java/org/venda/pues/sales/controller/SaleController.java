package org.venda.pues.sales.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import dto.SaleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.venda.pues.sales.service.SaleServices;

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
    public ResponseEntity<?> create(@PathVariable String userId, @RequestBody SaleDto saleDto, @RequestHeader("authorization") String authorizationHeader) throws UnirestException {
        return ResponseEntity.ok(saleServices.create(userId, saleDto, authorizationHeader));
    }
}
