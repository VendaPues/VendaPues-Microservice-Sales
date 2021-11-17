package org.venda.pues.sales.service;

import dto.SaleDto;
import error.exception.NotFoundException;
import error.exception.UnavailableProductsException;
import models.ProductDocument;
import models.SaleDocument;
import org.springframework.stereotype.Service;
import org.venda.pues.sales.repository.ProductRepository;
import org.venda.pues.sales.repository.SaleRepository;

import java.util.List;
import java.util.Set;

@Service
public class SaleServices {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleServices(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public SaleDocument create(SaleDto saleDto) {
        SaleDocument sale = new SaleDocument(saleDto);
        if (areAvailable(sale.getProducts().keySet())) {
            return saleRepository.save(sale);
        }
        throw new UnavailableProductsException("Some products are out of stock.");
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

    private boolean areAvailable(Set<String> productsIds) {
        List<ProductDocument> products = (List<ProductDocument>) productRepository.findAllById(productsIds);
        for (ProductDocument product: products) {
            if(product.getStock() == 0) {
                return false;
            }
        }
        return true;
    }
}
