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

import java.util.ArrayList;
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

    public SaleDocument create(String userId, List<SaleDto> saleDto) {
        UserDocument user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            SaleDocument sale = new SaleDocument(saleDto);
            sale = saleRepository.save(sale);
            user.addNewSale(sale.getId());
            updateUser(user);
            userRepository.save(user);

            return sale;
        }
        throw new NotFoundException("User not found");
    }

    public List<SaleDocument> all(String userId) {
        UserDocument user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<SaleDocument> sales = new ArrayList<>();
            for (String saleId: user.getSales()) {
                sales.add(saleRepository.findById(saleId).orElse(null));
            }

            return sales;
        }
        throw new NotFoundException("User not found");
    }

    private boolean areAvailable(Set<String> productsIds) {
        List<ProductDocument> products = (List<ProductDocument>) productRepository.findAllById(productsIds);
        for (ProductDocument product : products) {
            if (product.getStock() == 0) {
                return false;
            }
        }
        return true;
    }

    private void updateUser(UserDocument user) {
        userRepository.save(user);
    }
}
