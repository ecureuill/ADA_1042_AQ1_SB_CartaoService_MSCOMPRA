package com.ada.grupo5.mscompra.entity;

import com.ada.grupo5.mscompra.dto.PurchaseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "O campo de Data da Compra não deve ser nulo")
    private LocalDateTime purchaseDate;

    @NotNull(message = "O campo de Preço não deve ser nulo")
    private BigDecimal price;

    @NotBlank(message = "O campo de Nome da Loja não deve estar em branco")
    @NotNull(message = "O campo de Nome da Loja não deve ser nulo")
    private String store;

    @Column(name = "card_number")
    @Size(min = 12, max = 12, message = "Número do cartão deve ser composto por exatamente 12 números")
    @NotBlank(message = "O campo Número de Cartão não deve estar em branco")
    @NotNull(message = "O campo Número de Cartão não deve ser nulo")
    private String cardNumber;


    @Size(min = 3, max = 3, message = "CVV deve ser composto por exatamente 3 números")
    @NotBlank(message = "O campo CVV não deve estar em branco")
    @NotNull(message = "O campo CVV não deve ser nulo")
    private String cvv;

    @Column(name = "expire_card_date")
    @NotNull(message = "O campo Data de Expiração não deve ser nulo")
    private LocalDate expireCardDate;

    @NotBlank(message = "O campo de Nome de Titular não deve estar em branco")
    private String accountOwner;

    public Purchase(PurchaseDto purchaseDto) {
        this.purchaseDate = purchaseDto.purchaseDate();
        this.price = purchaseDto.price();
        this.store = purchaseDto.store();
        this.cardNumber = purchaseDto.cardNumber();
        this.cvv = purchaseDto.cvv();
        this.expireCardDate = purchaseDto.expireCardDate();
        this.accountOwner = purchaseDto.accountOwner();
    }

    public PurchaseDto PurchaseToDto(){
        return new PurchaseDto(this.purchaseDate, this.price, this.store, this.cardNumber, this.cvv, this.expireCardDate,
        this.accountOwner);
    }

}
