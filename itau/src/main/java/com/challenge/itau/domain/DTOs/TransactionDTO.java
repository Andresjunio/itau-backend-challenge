package com.challenge.itau.domain.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.OffsetDateTime;


public record TransactionDTO(@NotEmpty @PositiveOrZero Double valor,
                             @NotEmpty @PastOrPresent OffsetDateTime dataHora) {
}
