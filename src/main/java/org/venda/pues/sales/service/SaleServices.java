package org.venda.pues.sales.service;

import models.SaleDocument;
import org.springframework.stereotype.Service;
import org.venda.pues.sales.controller.SaleDto;
import org.venda.pues.sales.repository.SaleRepository;

import java.util.List;

@Service
public class SaleServices {

    private final SaleRepository saleRepository;

    public SaleServices(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public SaleDocument create(SaleDto saleDto) {
        SaleDocument sale = new SaleDocument();

        return saleRepository.save(parse(sale, saleDto));
    }

    public List<SaleDocument> all() {
        return saleRepository.findAll();
    }

    public SaleDocument findById(String id) {
        return saleRepository.findById(id).orElse(null);
    }

    public SaleDocument update(String id, SaleDto saleDto) {
        SaleDocument sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            return saleRepository.save(parse(sale, saleDto));
        }
        return null;
    }

    public boolean delete(String id) {
        SaleDocument sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            saleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SaleDocument parse(SaleDocument document, SaleDto dto) {
        document.setAmount(dto.getAmount());
        document.setProducts(dto.getProducts());
        document.setSoldAt(dto.getSoldAt());

        return document;
    }
}
