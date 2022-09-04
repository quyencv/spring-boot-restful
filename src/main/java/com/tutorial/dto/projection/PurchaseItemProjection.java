package com.tutorial.dto.projection;

import java.time.Instant;

public interface PurchaseItemProjection {
    String getTitle();

    Double getPrice();

    Instant getPurchaseTime();
}
