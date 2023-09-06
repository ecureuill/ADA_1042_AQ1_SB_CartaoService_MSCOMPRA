package com.ada.grupo5.mscompra.service;

import com.ada.grupo5.mscompra.dto.PurchaseDto;
import com.ada.grupo5.mscompra.entity.Purchase;
import com.ada.grupo5.mscompra.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public PurchaseDto createPurchase(PurchaseDto purchaseDto){
        Purchase purchase = new Purchase(purchaseDto);
        purchase = this.purchaseRepository.save(purchase);
        return purchase.PurchaseToDto();

    }
}
