package com.ada.grupo5.mscompra.controller;


import com.ada.grupo5.mscompra.dto.PurchaseDto;
import com.ada.grupo5.mscompra.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private PurchaseDto createPurchase(@RequestBody PurchaseDto body) throws Exception {
        return purchaseService.createPurchase(body);
    }
}
