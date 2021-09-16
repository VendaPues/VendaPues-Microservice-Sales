package org.venda.pues.sales.controller;

import models.SaleDocument;

import java.util.Date;
import java.util.List;

public class SaleDto {

    Double amount;

    List<String> products;

    Date soldAt;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public Date getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(Date soldAt) {
        this.soldAt = soldAt;
    }
}
