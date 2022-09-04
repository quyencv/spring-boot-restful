package com.tutorial.mapper;

import org.mapstruct.Mapper;

import com.tutorial.dto.PurchaseHistoryDTO;
import com.tutorial.model.PurchaseHistory;

@Mapper(componentModel = "spring", uses = {})
public interface PurchaseHistoryMapper extends EntityConvert<PurchaseHistory, PurchaseHistoryDTO> {
}