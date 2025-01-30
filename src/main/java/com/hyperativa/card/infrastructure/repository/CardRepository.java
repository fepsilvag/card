package com.hyperativa.card.infrastructure.repository;

import com.hyperativa.card.domain.domain.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    CardEntity findByCardNumber(String cardNumber);

}