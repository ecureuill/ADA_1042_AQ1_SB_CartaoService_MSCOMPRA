package com.ada.grupo5.mscompra.entity;

import com.ada.grupo5.mscompra.dto.PurchaseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    private BigDecimal price;

    private String store;

    @Column(name = "card_number")
    private String cardNumber;

    private String cvv;

    @Column(name = "expire_card_date")
    private LocalDate expireCardDate;

    public Purchase(PurchaseDto purchaseDto) {
        this.purchaseDate = purchaseDto.purchaseDate();
        this.price = purchaseDto.price();
        this.store = purchaseDto.store();
        this.cardNumber = purchaseDto.cardNumber();
        this.cvv = purchaseDto.cvv();
        this.expireCardDate = purchaseDto.expireCardDate();
    }

    public PurchaseDto PurchaseToDto(){
        return new PurchaseDto(this.purchaseDate, this.price, this.store, this.cardNumber, this.cvv, this.expireCardDate);
    }

}
