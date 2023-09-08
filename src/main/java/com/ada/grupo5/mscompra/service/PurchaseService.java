package com.ada.grupo5.mscompra.service;

import com.ada.grupo5.mscompra.dto.PurchaseDto;
import com.ada.grupo5.mscompra.entity.Purchase;
import com.ada.grupo5.mscompra.repository.PurchaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    private boolean validateDate(LocalDate expireCardDate, LocalDateTime purchaseDate){
        return purchaseDate.isBefore(expireCardDate.atStartOfDay());
    }

    public PurchaseDto createPurchase(PurchaseDto purchaseDto){
        Purchase purchase = new Purchase(purchaseDto);
        boolean validateDate = validateDate(purchase.getExpireCardDate(), purchase.getPurchaseDate());

        if(validateDate){
            purchase = this.purchaseRepository.save(purchase);
            return purchase.PurchaseToDto();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão expirado na data de compra");
        }

    }
}
