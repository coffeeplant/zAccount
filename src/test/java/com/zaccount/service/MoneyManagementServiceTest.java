package com.zaccount.service;

import com.zaccount.exceptions.FundsException;
import com.zaccount.model.Atm;
import org.junit.Test;
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
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.class)
public class MoneyManagementServiceTest {

    @InjectMocks
    MoneyManagementServiceImpl moneyManagementService;
    @Mock
    Atm atm;
    LinkedHashMap<Integer, Integer> theNotes;

    @Test
    public void successfulWithDrawalWhenFundsAreAvailable() throws FundsException {
        theNotes = new LinkedHashMap<>();
        theNotes.put(50, 10);
        theNotes.put(20, 30);
        theNotes.put(10, 30);
        theNotes.put(5, 20);

        when(atm.getAvailableFunds()).thenReturn(100);
        when(atm.getNotes()).thenReturn(theNotes);
        String result = moneyManagementService.withdrawFromVault(50);
        assertThat(result, containsString("You have withdrawn this amount"));
    }

    @Test(expected=FundsException.class)
    public void shouldThrowExceptionWhenATMFundsAreNotSufficient() throws FundsException {
        when(atm.getAvailableFunds()).thenReturn(40);
        String result = moneyManagementService.withdrawFromVault(100);
        assertThat(result, containsString("does not have sufficient cash"));
    }
}
