package com.wallet.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.demo.exception.InvalidBalanceException;
import com.wallet.demo.exception.InvalidTransactionTypeException;
import com.wallet.demo.exception.TransactionExistsException;
import com.wallet.demo.models.Transaction;
import com.wallet.demo.models.TransactionResponse;
import com.wallet.demo.service.AccountService;
import com.wallet.demo.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link TransactionController}
 */
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {
        "com.wallet.demo.controller"
})
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    private static final String ENDPOINT_TRANSACTION = "/api/transaction";
    private static final String ENDPOINT_GET_TRANSACTIONS = "/api/transaction/list/%s";

    private static final int ACCOUNT_ID = 2;
    private static final double BALANCE = 50.34;
    private static final String TRANSACTION_ID_1 = "0fa6b8ca-11e4-11ed-861d-0242ac121144";
    private static final String TRANSACTION_ID_2 = "08c8b372-11db-11ed-861d-0242ac120002";
    private static final String TRANSACTION_TYPE_DEBIT = "DEBIT";
    private static final String TRANSACTION_TYPE_CREDIT = "CREDIT";
    private static final String TRANSACTION_TYPE_UNKNOWN = "FREE";

    private static final double AMOUNT_1 = 10.04;
    private static final double AMOUNT_2 = 24.52;

    private static final boolean SUCCESS = true;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @Value("classpath:/fixtures/transaction_request.json")
    private Resource transactionRequest;

    @Value("classpath:/fixtures/transaction_request_malformed.json")
    private Resource transactionRequestMalformed;

    @Value("classpath:/fixtures/transaction_list.json")
    private Resource transactionList;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    @Test
    public void testHandleTransactionReturnsTransactionResponse() throws Exception {
        TransactionResponse transactionResponse = new TransactionResponse(TRANSACTION_ID_1, SUCCESS, BALANCE);

        when(transactionService.handleTransaction(transactionCaptor.capture())).thenReturn(transactionResponse);

        MockHttpServletRequestBuilder request = put(ENDPOINT_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").value(TRANSACTION_ID_1))
                .andExpect(jsonPath("$.success").value(SUCCESS))
                .andExpect(jsonPath("$.balance").value(BALANCE));
    }

    @Test
    public void testHandleTransactionReturnsTransactionExistsException() throws Exception {
        String errorMessage = String.format("A transaction already exists with the [%s] ID.", TRANSACTION_ID_1);

        doThrow(new TransactionExistsException(errorMessage))
                .when(transactionService).handleTransaction(any(Transaction.class));

        MockHttpServletRequestBuilder request = put(ENDPOINT_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.reason").value(errorMessage));
    }

    @Test
    public void testHandleTransactionReturnsInvalidBalanceException() throws Exception {
        String errorMessage = String.format("Your balance cannot be negative. Your remaining balance is: [%s].", BALANCE);

        doThrow(new InvalidBalanceException(errorMessage))
                .when(transactionService).handleTransaction(any(Transaction.class));

        MockHttpServletRequestBuilder request = put(ENDPOINT_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(jsonPath("$.reason").value(errorMessage));
    }

    @Test
    public void testHandleTransactionReturnsInvalidTransactionTypeException() throws Exception {
        String errorMessage = String.format("Unknown transaction of type [%s].", TRANSACTION_TYPE_UNKNOWN);

        doThrow(new InvalidTransactionTypeException(errorMessage))
                .when(transactionService).handleTransaction(any(Transaction.class));

        MockHttpServletRequestBuilder request = put(ENDPOINT_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.reason").value(errorMessage));
    }

    @Test
    public void testHandleTransactionReturnsBadRequestIfMalformedJson() throws Exception {
        MockHttpServletRequestBuilder request = put(ENDPOINT_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(transactionRequestMalformed))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTransactionsReturnsListOfTransactions() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Transaction> transactions = objectMapper.readerForListOf(Transaction.class).readValue(asString(transactionList));

        when(transactionService.getTransactions(ACCOUNT_ID)).thenReturn(transactions);

        MockHttpServletRequestBuilder request = get(String.format(ENDPOINT_GET_TRANSACTIONS, ACCOUNT_ID))
                .contentType(MediaType.ALL_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transaction_id").value(TRANSACTION_ID_1))
                .andExpect(jsonPath("$[0].account_id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$[0].amount").value(AMOUNT_1))
                .andExpect(jsonPath("$[0].transaction_type").value(TRANSACTION_TYPE_DEBIT))
                .andExpect(jsonPath("$[1].transaction_id").value(TRANSACTION_ID_2))
                .andExpect(jsonPath("$[1].account_id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$[1].amount").value(AMOUNT_2))
                .andExpect(jsonPath("$[1].transaction_type").value(TRANSACTION_TYPE_CREDIT));
    }

    private static String asString(Resource resource) throws Exception {
        return resource == null ? null : IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
