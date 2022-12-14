package com.wallet.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.demo.exception.AccountNotFoundException;
import com.wallet.demo.exception.DuplicateAccountException;
import com.wallet.demo.models.Account;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link AccountController}
 */
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {
        "com.wallet.demo.controller"
})
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    private static final String ENDPOINT_CREATE_ACCOUNT = "/api/account";
    private static final String ENDPOINT_GET_ACCOUNT = "/api/account/%s";

    private static final int ACCOUNT_ID = 5;
    private static final String ACCOUNT_NAME = "Winner Today";
    private static final double BALANCE = 50.34;

    @Autowired
    private MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @Value("classpath:/fixtures/create_account_request.json")
    private Resource createAccountRequest;

    @Value("classpath:/fixtures/create_account_request_malformed.json")
    private Resource createAccountRequestMalformed;

    @Value("classpath:/fixtures/account.json")
    private Resource accountObject;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Test
    public void testGetAccountIsSuccessful() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(asString(accountObject), Account.class);

        when(accountService.getAccount(ACCOUNT_ID)).thenReturn(account);

        MockHttpServletRequestBuilder request = get(String.format(ENDPOINT_GET_ACCOUNT, ACCOUNT_ID))
                .contentType(MediaType.ALL_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$.name").value(ACCOUNT_NAME))
                .andExpect(jsonPath("$.balance").value(BALANCE));
    }

    @Test
    public void testGetAccountNotFound() throws Exception {
        String errorMessage = "Account not found with account ID. [5]";

        when(accountService.getAccount(ACCOUNT_ID)).thenThrow(new AccountNotFoundException(errorMessage));

        MockHttpServletRequestBuilder request = get(String.format(ENDPOINT_GET_ACCOUNT, ACCOUNT_ID))
                .contentType(MediaType.ALL_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.reason").value(errorMessage));
    }

    @Test
    public void testCreateAccountSucceeds() throws Exception {
        doNothing().when(accountService).createAccount(accountCaptor.capture());

        MockHttpServletRequestBuilder request = put(ENDPOINT_CREATE_ACCOUNT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(createAccountRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        final Account capturedRequest = accountCaptor.getValue();

        assertThat(capturedRequest.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(capturedRequest.getName()).isEqualTo(ACCOUNT_NAME);
    }

    @Test
    public void testCreateAccountDiscoversDuplicateReturnsConflict() throws Exception {
        String errorMessage = String.format("An account with the name [%s] is already present.", ACCOUNT_NAME);

        doThrow(new DuplicateAccountException(errorMessage))
                .when(accountService).createAccount(any(Account.class));

        MockHttpServletRequestBuilder request = put(ENDPOINT_CREATE_ACCOUNT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(createAccountRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.reason").value(errorMessage));
    }

    @Test
    public void testCreateAccountWithMalformedJsonReturnsBadRequest() throws Exception {
        MockHttpServletRequestBuilder request = put(ENDPOINT_CREATE_ACCOUNT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asString(createAccountRequestMalformed))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    private static String asString(Resource resource) throws Exception {
        return resource == null ? null : IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
