package com.wallet.demo.service;

import com.wallet.demo.exception.InvalidBalanceException;
import com.wallet.demo.exception.InvalidTransactionTypeException;
import com.wallet.demo.exception.TransactionExistsException;
import com.wallet.demo.models.Account;
import com.wallet.demo.models.Transaction;
import com.wallet.demo.models.TransactionResponse;
import com.wallet.demo.persistence.AccountEntity;
import com.wallet.demo.persistence.TransactionEntity;
import com.wallet.demo.persistence.TransactionRepository;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link TransactionService} class.
 *
 * @author David Winn
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    private static final int ACCOUNT_ID = 2;
    private static final String ACCOUNT_NAME = "Winner Today";
    private static final double BALANCE = 50.34;
    private static final String TRANSACTION_ID_1 = "0fa6b8ca-11e4-11ed-861d-0242ac121144";
    private static final String TRANSACTION_ID_2 = "08c8b372-11db-11ed-861d-0242ac120002";
    private static final String TRANSACTION_TYPE_DEBIT = "DEBIT";
    private static final String TRANSACTION_TYPE_CREDIT = "CREDIT";
    private static final String TRANSACTION_TYPE_UNKNOWN = "FREE";

    private static final double AMOUNT_1 = 10.04;
    private static final double AMOUNT_2 = 124.52;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Mapper mapper;

    @Captor
    private ArgumentCaptor<TransactionEntity> transactionEntityArgumentCaptor;

    @Mock
    private TransactionEntity mockTransactionEntity;

    @Captor
    private ArgumentCaptor<AccountEntity> accountEntityArgumentCaptor;


    @Before
    public void setUp() {
        when(accountService.getAccount(ACCOUNT_ID)).thenReturn(new Account(ACCOUNT_ID, ACCOUNT_NAME, BALANCE));
    }

    @Test
    public void handleTransactionShouldUpdateBalanceAndLogTransactionForDebit() {
        when(transactionRepository.save(transactionEntityArgumentCaptor.capture())).thenReturn(mockTransactionEntity);
        doNothing().when(accountService).save(accountEntityArgumentCaptor.capture());

        assertThatCode(() -> transactionService.handleTransaction(new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_1, TRANSACTION_TYPE_DEBIT)))
                .doesNotThrowAnyException();

        // Check the response object.
        TransactionResponse response = transactionService.handleTransaction(
                new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_1, TRANSACTION_TYPE_DEBIT));
        assertThat(response.getTransactionId()).isEqualTo(TRANSACTION_ID_1);
        assertThat(response.getBalance()).isEqualTo(BALANCE - AMOUNT_1);

        // Check that the balance was updated.
        AccountEntity accountEntity = accountEntityArgumentCaptor.getValue();
        assertThat(accountEntity.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(accountEntity.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(accountEntity.getBalance()).isEqualTo(BALANCE - AMOUNT_1);

        // Check that the transaction was saved.
        TransactionEntity transactionEntity = transactionEntityArgumentCaptor.getValue();
        assertThat(transactionEntity.getTransactionId()).isEqualTo(TRANSACTION_ID_1);
        assertThat(transactionEntity.getAccountId()).isEqualTo(ACCOUNT_ID);
        assertThat(transactionEntity.getAmount()).isEqualTo(AMOUNT_1);
        assertThat(transactionEntity.getTransactionType()).isEqualTo(TRANSACTION_TYPE_DEBIT);
    }

    @Test
    public void handleTransactionShouldUpdateBalanceAndLogTransactionForCredit() {
        when(transactionRepository.save(transactionEntityArgumentCaptor.capture())).thenReturn(mockTransactionEntity);
        doNothing().when(accountService).save(accountEntityArgumentCaptor.capture());

        assertThatCode(() -> transactionService.handleTransaction(new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_1, TRANSACTION_TYPE_CREDIT)))
                .doesNotThrowAnyException();

        // Check the response object.
        TransactionResponse response = transactionService.handleTransaction(
                new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_1, TRANSACTION_TYPE_CREDIT));
        assertThat(response.getTransactionId()).isEqualTo(TRANSACTION_ID_1);
        assertThat(response.getBalance()).isEqualTo(BALANCE + AMOUNT_1);

        // Check that the balance was updated.
        AccountEntity accountEntity = accountEntityArgumentCaptor.getValue();
        assertThat(accountEntity.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(accountEntity.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(accountEntity.getBalance()).isEqualTo(BALANCE + AMOUNT_1);

        // Check that the transaction was saved.
        TransactionEntity transactionEntity = transactionEntityArgumentCaptor.getValue();
        assertThat(transactionEntity.getTransactionId()).isEqualTo(TRANSACTION_ID_1);
        assertThat(transactionEntity.getAccountId()).isEqualTo(ACCOUNT_ID);
        assertThat(transactionEntity.getAmount()).isEqualTo(AMOUNT_1);
        assertThat(transactionEntity.getTransactionType()).isEqualTo(TRANSACTION_TYPE_CREDIT);
    }

    @Test
    public void handleTransactionShouldThrowExceptionIfTransactionExists() {
        when(mockTransactionEntity.getTransactionId()).thenReturn(TRANSACTION_ID_1);
        when(transactionRepository.findByTransactionId(TRANSACTION_ID_1)).thenReturn(Optional.of(mockTransactionEntity));

        assertThatThrownBy(() -> transactionService.handleTransaction(
                new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_1, TRANSACTION_TYPE_CREDIT)))
                .isInstanceOf(TransactionExistsException.class)
                .hasMessage(String.format("A transaction already exists with the [%s] ID.", TRANSACTION_ID_1));
    }

    @Test
    public void handleTransactionShouldThrowExceptionIfBalanceBelowZero() {
        assertThatThrownBy(() -> transactionService.handleTransaction(
                new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_2, TRANSACTION_TYPE_DEBIT)))
                .isInstanceOf(InvalidBalanceException.class)
                .hasMessage(String.format("Your balance cannot be negative. Your remaining balance is: [%s].", BALANCE));
    }

    @Test
    public void handleTransactionShouldThrowExceptionIfUnknownTransactionType() {
        assertThatThrownBy(() -> transactionService.handleTransaction(
                new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_1, TRANSACTION_TYPE_UNKNOWN)))
                .isInstanceOf(InvalidTransactionTypeException.class)
                .hasMessage(String.format("Unknown transaction of type [%s].", TRANSACTION_TYPE_UNKNOWN));
    }

    @Test
    public void getTransactionsShouldListAllTransactionsForAccount() {
        when(transactionRepository.findByAccountId(ACCOUNT_ID)).thenReturn(List.of(mockTransactionEntity, mockTransactionEntity));

        when(mapper.map(any(), any()))
                .thenReturn(new Transaction(TRANSACTION_ID_1, ACCOUNT_ID, AMOUNT_1, TRANSACTION_TYPE_CREDIT))
                .thenReturn(new Transaction(TRANSACTION_ID_2, ACCOUNT_ID, AMOUNT_2, TRANSACTION_TYPE_DEBIT));

        List<Transaction> transactions = transactionService.getTransactions(ACCOUNT_ID);

        assertThat(transactions).hasSize(2);

        assertThat(transactions.get(0).getTransactionId()).isEqualTo(TRANSACTION_ID_1);
        assertThat(transactions.get(0).getAccountId()).isEqualTo(ACCOUNT_ID);
        assertThat(transactions.get(0).getAmount()).isEqualTo(AMOUNT_1);
        assertThat(transactions.get(0).getTransactionType()).isEqualTo(TRANSACTION_TYPE_CREDIT);

        assertThat(transactions.get(1).getTransactionId()).isEqualTo(TRANSACTION_ID_2);
        assertThat(transactions.get(1).getAccountId()).isEqualTo(ACCOUNT_ID);
        assertThat(transactions.get(1).getAmount()).isEqualTo(AMOUNT_2);
        assertThat(transactions.get(1).getTransactionType()).isEqualTo(TRANSACTION_TYPE_DEBIT);
    }
}
