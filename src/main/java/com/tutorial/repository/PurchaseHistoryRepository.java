package com.tutorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tutorial.dto.projection.PurchaseItemProjection;
import com.tutorial.model.PurchaseHistory;

@Repository
public interface PurchaseHistoryRepository
        extends JpaRepository<PurchaseHistory, Long>, JpaSpecificationExecutor<PurchaseHistory> {

    @Query("select b.title as title, ph.price as price, ph.purchaseTime as purchaseTime FROM PurchaseHistory ph "
            + " LEFT JOIN Book b on b.id = ph.bookId "
            + " WHERE ph.userId IN (SELECT id FROM User WHERE username = :username)")
    List<PurchaseItemProjection> findAllPurchasesOfUser(@Param("username") String username);
}