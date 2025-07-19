package com.challenge.itau.repository.transaction;

import com.challenge.itau.domain.DTOs.TransactionDTO;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository {

    public Map<Integer, TransactionDTO> TRANSACTIONS = new HashMap<>();

    public void save(TransactionDTO transaction) {
        int id = TRANSACTIONS.size() + 1;
        TRANSACTIONS.put(id, transaction);
    }

    public void deleteAll() {
        TRANSACTIONS.clear();
    }

    public List<TransactionDTO> getAllTransactions() {
        return TRANSACTIONS.values().stream()
                .filter(transaction -> transaction.dataHora()
                        .toInstant()
                        .isAfter(Instant.now().minusSeconds(60)))
                .toList();
    }

}
