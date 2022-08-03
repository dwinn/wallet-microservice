package com.wallet.demo.service;

import com.wallet.demo.exception.AccountNotFoundException;
import com.wallet.demo.exception.DuplicateAccountException;
import com.wallet.demo.models.Account;
import com.wallet.demo.persistence.AccountEntity;
import com.wallet.demo.persistence.AccountRepository;
import org.dozer.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link AccountService} class.
 *
 * @author David Winn
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    private static final int ACCOUNT_ID = 5;
    private static final String ACCOUNT_NAME = "Winner Today";
    private static final double DEFAULT_BALANCE = 0;
    private static final double BALANCE = 50;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Mapper mapper;

    @Captor
    private ArgumentCaptor<AccountEntity> accountEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Mock
    private AccountEntity mockAccountEntity;

    @Test
    public void createAccountShouldAddAccountEntityToDatabase() {
        when(accountRepository.save(accountEntityArgumentCaptor.capture())).thenReturn(mockAccountEntity);

        assertThatCode(() -> accountService.createAccount(new Account(ACCOUNT_ID, ACCOUNT_NAME, DEFAULT_BALANCE)))
                .doesNotThrowAnyException();

        AccountEntity accountEntity = accountEntityArgumentCaptor.getValue();

        assertThat(accountEntity.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(accountEntity.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(accountEntity.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    public void createAccountShouldThrowExceptionIfAccountNameAlreadyPresent() {
        when(mockAccountEntity.getName()).thenReturn(ACCOUNT_NAME);
        when(accountRepository.findByName(ACCOUNT_NAME)).thenReturn(Optional.of(mockAccountEntity));

        assertThatThrownBy(() -> accountService.createAccount(new Account(ACCOUNT_ID, ACCOUNT_NAME, DEFAULT_BALANCE)))
                .isInstanceOf(DuplicateAccountException.class)
                .hasMessage(String.format("An account with the name [%s] is already present.", ACCOUNT_NAME));
    }

    @Test
    public void getAccountShouldReturnAccountFromDatabase() {
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(mockAccountEntity));
        when(mapper.map(accountEntityArgumentCaptor.capture(), any())).thenReturn(new Account(ACCOUNT_ID, ACCOUNT_NAME, BALANCE));

        Account account = accountService.getAccount(ACCOUNT_ID);

        assertThat(account.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(account.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(account.getBalance()).isEqualTo(BALANCE);
    }

    @Test
    public void getAccountShouldThrowAccountNotFoundException() {
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getAccount(ACCOUNT_ID))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage(String.format("Account not found with account ID [%s].", ACCOUNT_ID));
    }
}
