package org.venda.pues.sales.clients;

import com.google.common.base.Joiner;
import com.mashape.unirest.http.HttpResponse;
import dto.ProductSaleDetailsDto;
import dto.SaleDto;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMicroServiceClient {

    public int sellProducts(SaleDto saleData, String bearerToken) throws UnirestException {
        HttpResponse<String> response = Unirest
                .put("http://localhost:8081/v1/product/decrease-stock")
                .header("Authorization", bearerToken)
                .header("Content-Type", "application/json")
                .body(saleDataToJsonString(saleData))
                .asString();
        return response.getCode();
    }

    private String saleDataToJsonString(SaleDto saleData) {
        List<String> productsStringList = new ArrayList<>();
        for (ProductSaleDetailsDto sale : saleData.getSaleData()) {
            String saleString = "{\"productId\":" + "\"" + sale.getProductId() + "\"," +
                    "\"productPrice\":" + sale.getProductPrice() + "," +
                    "\"quantity\":" + sale.getQuantity() + "}";
            productsStringList.add(saleString);
        }

        return "{\"saleData\": [" + Joiner.on(",").join(productsStringList) + "]}";
    }
}
