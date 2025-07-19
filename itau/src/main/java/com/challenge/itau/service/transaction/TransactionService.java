package com.challenge.itau.service.transaction;

import com.challenge.itau.domain.DTOs.TransactionDTO;
import com.challenge.itau.domain.DTOs.TransactionStatisticsDTO;
import com.challenge.itau.repository.transaction.TransactionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public HttpStatus receiveTransaction(TransactionDTO transaction) {
        if(transaction.dataHora() == null && transaction.valor() == null)
            return HttpStatus.BAD_REQUEST;

        try {
            transactionRepository.save(transaction);
        }catch(DataIntegrityViolationException e) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return HttpStatus.CREATED;
    }

    public HttpStatus cleanTransactions() {
        transactionRepository.deleteAll();
        return HttpStatus.OK;
    }

    public TransactionStatisticsDTO getStatistics() {
        List<TransactionDTO> transactions = transactionRepository.getAllTransactions();

        Double sum = getSum(transactions);
        Double avg = getAvg(transactions);
        Double min = getMin(transactions);
        Double max = getMax(transactions);

        return new TransactionStatisticsDTO(transactions.size(), sum, avg, min, max);
    }

    private static Double getMax(List<TransactionDTO> transactions) {
        return transactions.stream()
                .mapToDouble(TransactionDTO::valor)
                .max()
                .orElse(0.0);
    }

    private static Double getMin(List<TransactionDTO> transactions) {
        return transactions.stream()
                .mapToDouble(TransactionDTO::valor)
                .min()
                .orElse(0.0);
    }

    private static Double getAvg(List<TransactionDTO> transactions) {
        return transactions.stream()
                .mapToDouble(TransactionDTO::valor)
                .average()
                .orElse(0.0);
    }

    private static double getSum(List<TransactionDTO> transactions) {
        return transactions.stream()
                .mapToDouble(TransactionDTO::valor)
                .sum();
    }


}
