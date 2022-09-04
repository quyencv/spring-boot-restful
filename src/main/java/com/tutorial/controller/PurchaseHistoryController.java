package com.tutorial.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.constant.UrlPathConstant;
import com.tutorial.dto.PurchaseHistoryDTO;
import com.tutorial.service.PurchaseHistoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = UrlPathConstant.PurchaseHistory.PRE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @PostMapping
    public ResponseEntity<?> savePurchaseHistory(@RequestBody @Valid PurchaseHistoryDTO dto) throws Exception {
        return new ResponseEntity<>(purchaseHistoryService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPurchasesOfUser(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok(purchaseHistoryService.findPurchasedItemsOfUser(username));
    }
}
