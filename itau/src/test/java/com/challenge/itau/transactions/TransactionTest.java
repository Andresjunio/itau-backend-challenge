package com.challenge.itau.transactions;

import com.challenge.itau.controller.transaction.TransactionController;
import com.challenge.itau.domain.DTOs.TransactionDTO;
import com.challenge.itau.repository.transaction.TransactionRepository;
import com.challenge.itau.service.transaction.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void shouldReceiveTransaction() throws Exception {
        final var value = 1324.56;
        final var dateHour = OffsetDateTime.now();

        var transaction = new TransactionDTO(value, dateHour);
        var requestBody = mapper.writeValueAsString(transaction);

        mvc.perform(
                post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenTransactionIsNull() throws Exception{
        final var transaction = new TransactionDTO(null, null);
        var requestBody = mapper.writeValueAsString(transaction);

        mvc.perform(
                post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenTransactionIsInvalid() throws Exception{
        final var value = -12.5;
        final var dateHour = OffsetDateTime.now();

        final var transaction = new TransactionDTO(value, dateHour);
        var requestBody = mapper.writeValueAsString(transaction);

        mvc.perform(
                post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(status().isUnprocessableEntity());
    }


}
