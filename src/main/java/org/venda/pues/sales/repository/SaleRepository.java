package org.venda.pues.sales.repository;

import models.SaleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<SaleDocument, String> {

}
