package com.zaccount.service;

import com.zaccount.model.Account;
import com.zaccount.repository.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    AccountServiceImpl accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    MoneyManagementService moneyManagementService;

    private Account testAccount;

    @BeforeEach
    public void init(){

    }

    @Test
    public void shouldDeductWithdrawalAmountFromAccount(){
        testAccount = new Account (123456789, "1234", 800, 150);
        accountService.withdrawal(testAccount, "1234", 100);
        assertThat(testAccount.getBalance(), is(equalTo(700.0)));
    }

    @Test
    public void shouldReturnTrueForValidPin(){
        testAccount = new Account (123456789, "1234", 800, 150);
        boolean result = accountService.pinValid(testAccount, "1234");
        assertThat(result, is(equalTo(true)));
    }

    @Test
    public void shouldReturnFalseForInValidPin(){
        testAccount = new Account (123456789, "1234", 800, 150);
        boolean result = accountService.pinValid(testAccount, "0000");
        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void shouldReturnBalanceOf800(){
        testAccount = new Account (123456789, "1234", 800, 150);
        String result = accountService.checkBalance(testAccount, "1234");
        assertThat(result, containsString("800"));
    }

}
