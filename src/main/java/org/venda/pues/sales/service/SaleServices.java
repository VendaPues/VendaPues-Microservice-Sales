package org.venda.pues.sales.service;

import dto.SaleDto;
import error.exception.NotFoundException;
import models.ProductDocument;
import models.SaleDocument;
import models.UserDocument;
import org.springframework.stereotype.Service;
import org.venda.pues.sales.repository.ProductRepository;
import org.venda.pues.sales.repository.SaleRepository;
import org.venda.pues.sales.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class SaleServices {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SaleServices(SaleRepository saleRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public SaleDocument create(String userId, SaleDto saleDto) {
        UserDocument user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            SaleDocument sale = new SaleDocument(saleDto);
            sale = saleRepository.save(sale);
            user.addNewSale(sale.getId());
            updateUser(user);

            return sale;
        }
        throw new NotFoundException("User not found");
    }

    public SaleDocument findById(String id) {
        SaleDocument sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            return sale;
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

    private void updateUser(UserDocument user) {
        userRepository.save(user);
    }
}
