package com.tutorial.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorial.dto.PurchaseHistoryDTO;
import com.tutorial.dto.projection.PurchaseItemProjection;
import com.tutorial.exception.custom.NotFoundException;
import com.tutorial.mapper.PurchaseHistoryMapper;
import com.tutorial.model.PurchaseHistory;
import com.tutorial.model.User;
import com.tutorial.repository.PurchaseHistoryRepository;
import com.tutorial.service.PurchaseHistoryService;
import com.tutorial.service.UserService;
import com.tutorial.service.utils.QueryService;
import com.tutorial.utils.SecurityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PurchaseHistoryServiceImpl extends QueryService<PurchaseHistory, PurchaseHistoryDTO>
        implements PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    private final PurchaseHistoryMapper purchaseHistoryMapper;
    
    private final UserService userService;

    @Override
    public PurchaseHistoryDTO save(PurchaseHistoryDTO dto) {
        String username = SecurityUtils.getCurrentUser();
        Long userId = userService.findByUsername(username).map(item -> item.getId())
                .orElseThrow(() -> new NotFoundException(User.class, "username", username));
        dto.setUserId(userId);
        dto.setPurchaseTime(Instant.now());
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.save(purchaseHistoryMapper.toEntity(dto));
        return purchaseHistoryMapper.toDTO(purchaseHistory);
    }

    @Override
    public List<PurchaseItemProjection> findPurchasedItemsOfUser(String username) {
        return purchaseHistoryRepository.findAllPurchasesOfUser(username);
    }

}
