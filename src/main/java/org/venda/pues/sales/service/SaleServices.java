package org.venda.pues.sales.service;

import dto.SaleDto;
import error.exception.NotFoundException;
import models.SaleDocument;
import org.springframework.stereotype.Service;
import org.venda.pues.sales.repository.SaleRepository;

import java.util.List;

@Service
public class SaleServices {

    private final SaleRepository saleRepository;

    public SaleServices(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public SaleDocument create(SaleDto saleDto) {
        SaleDocument sale = new SaleDocument(saleDto);
        return saleRepository.save(sale);
    }

    public List<SaleDocument> all() {
        return saleRepository.findAll();
    }

    public SaleDocument findById(String id) {
        SaleDocument sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            return sale;
        }
        throw new NotFoundException("Sale not found");
    }

    public SaleDocument update(String id, SaleDto saleDto) {
        SaleDocument sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            sale.update(saleDto);
            return saleRepository.save(sale);
        }
        throw new NotFoundException("Sale not found");
    }

    public boolean delete(String id) {
        SaleDocument sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            saleRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException("Sale not found");
    }
}
