package com.tutorial.service;

import java.util.List;

import com.tutorial.dto.PurchaseHistoryDTO;
import com.tutorial.dto.projection.PurchaseItemProjection;
import com.tutorial.model.PurchaseHistory;
import com.tutorial.service.utils.BaseService;

public interface PurchaseHistoryService extends BaseService<PurchaseHistory, PurchaseHistoryDTO> {

    List<PurchaseItemProjection> findPurchasedItemsOfUser(String username);
}
