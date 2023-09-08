package com.ada.grupo5.mscompra.service;

import com.ada.grupo5.mscompra.dto.PurchaseDto;
import com.ada.grupo5.mscompra.entity.Purchase;
import com.ada.grupo5.mscompra.repository.PurchaseRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    private boolean validateDate(LocalDate expireCardDate, LocalDateTime purchaseDate){
        return purchaseDate.isBefore(expireCardDate.atStartOfDay());
    }

    private JsonNode postRequest(String url, PurchaseDto purchaseDto) throws Exception{
        String serverUrl = url;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String requestBody = objectMapper.writeValueAsString(purchaseDto);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverUrl))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        JsonNode jsonResponse = objectMapper.readTree(response.body());

        return jsonResponse;
    }

    private boolean validateCard(PurchaseDto purchaseDto) throws Exception {
        boolean validateDate = validateDate(purchaseDto.expireCardDate(), purchaseDto.purchaseDate());
        if(validateDate){
            JsonNode jsonResponse = postRequest("http://localhost:8080/cards/validate", purchaseDto);
            boolean isValid = jsonResponse.get("valid").asBoolean();
            return isValid;
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão expirado na data de compra");
        }

    }

    private boolean proccessPayment(PurchaseDto purchaseDto) throws Exception {
        JsonNode jsonResponse = postRequest("http://localhost:8080/purchase", purchaseDto);
        String status = jsonResponse.get("status").asText();
        if(status.equals("CONFIRMED")){
            return true;
        }else{
            return false;
        }

    }
    public PurchaseDto createPurchase(PurchaseDto purchaseDto) throws Exception {
        Purchase purchase = new Purchase(purchaseDto);
        boolean proccessPayment = proccessPayment(purchaseDto);

        if (proccessPayment){
            purchase = this.purchaseRepository.save(purchase);
            return purchase.PurchaseToDto();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compra não confirmada");
        }

    }
}
