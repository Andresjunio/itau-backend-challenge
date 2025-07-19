package com.challenge.itau.domain.DTOs;

public record TransactionStatisticsDTO(Integer count,
                                       Double sum,
                                       Double avg,
                                       Double min,
                                       Double max) {
}
