package org.venda.pues.sales.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import dto.SaleDto;
import error.exception.NotFoundException;
import models.ProductDocument;
import models.SaleDocument;
import models.UserDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.venda.pues.sales.clients.UserMicroServiceClient;
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
    private final UserMicroServiceClient userMicroServiceClient;

    public SaleServices(SaleRepository saleRepository, ProductRepository productRepository, UserRepository userRepository, @Autowired UserMicroServiceClient userMicroServiceClient) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userMicroServiceClient = userMicroServiceClient;
    }

    public SaleDocument create(String userId, SaleDto saleDto, String bearerToken) throws UnirestException {
        UserDocument user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            SaleDocument sale = new SaleDocument(saleDto);
            sale = saleRepository.save(sale);
            user.addNewSale(sale.getId());
            updateUser(user);
            userRepository.save(user);
            System.out.println(userMicroServiceClient.sellProducts(saleDto, bearerToken));

            return sale;
        }
        throw new NotFoundException("User not found");
    }

    public List<SaleDocument> all(String userId) {
        UserDocument user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<SaleDocument> sales = new ArrayList<>();
            for (String saleId : user.getSales()) {
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
