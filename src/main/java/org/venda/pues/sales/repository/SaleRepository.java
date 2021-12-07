package org.venda.pues.sales.repository;

import models.SaleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface SaleRepository extends MongoRepository<SaleDocument, String> {

    SaleDocument findByIdEqualsAndSoldAtBetween(String id, Date startDate, Date endDate);
}
