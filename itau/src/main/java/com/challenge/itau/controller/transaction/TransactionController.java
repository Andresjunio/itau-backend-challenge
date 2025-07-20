package com.challenge.itau.controller.transaction;

import com.challenge.itau.domain.DTOs.TransactionDTO;
import com.challenge.itau.domain.DTOs.TransactionStatisticsDTO;
import com.challenge.itau.service.transaction.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transacao")
    public HttpStatus receiveTransaction(@Valid @RequestBody TransactionDTO transaction) {
        return transactionService.receiveTransaction(transaction);
    }

    @DeleteMapping("/transacao")
    public HttpStatus cleanTransactions() {
        return transactionService.cleanTransactions();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<TransactionStatisticsDTO> getStatistics() {
        return new ResponseEntity<TransactionStatisticsDTO>(transactionService.getStatistics(), HttpStatus.OK);
    }



}
