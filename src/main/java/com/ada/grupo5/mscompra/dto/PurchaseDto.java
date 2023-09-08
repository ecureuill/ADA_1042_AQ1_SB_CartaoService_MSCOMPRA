package com.ada.grupo5.mscompra.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PurchaseDto(LocalDateTime purchaseDate, BigDecimal price, String store, String cardNumber, String cvv, LocalDate expireCardDate,
                          String accountOwner) {

    @Override
    public LocalDateTime purchaseDate() {
        return purchaseDate;
    }

    @Override
    public BigDecimal price() {
        return price;
    }

    @Override
    public String store() {
        return store;
    }

    @Override
    public String cardNumber() {
        return cardNumber;
    }

    @Override
    public String cvv() {
        return cvv;
    }

    @Override
    public LocalDate expireCardDate() {
        return expireCardDate;
    }

    @Override
    public String accountOwner() {
        return accountOwner;
    }
}
