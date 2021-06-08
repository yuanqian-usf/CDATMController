package com.circulardollar.cdatm.session;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import org.junit.Test;

import java.util.HashMap;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.*;

public class ATMSessionControllerTest {

    @Test public void setCard() {
        ATMSessionController as = new ATMSessionController();
        String cardNumber = randomString();
        as.setCard(Card.newBuilder().setCardNumber(cardNumber).setCvc(randomString()).setExpirationDate(randomString()).setHolderName(randomString()).build());
        assertEquals(cardNumber, as.getCard().getCardNumber());
    }

    @Test public void setAccounts() {
        ATMSessionController as = new ATMSessionController();
        as.setAccounts(randomString(), randomMap());
        assertNotNull(as.getTokenId());
        assertNotNull(as.getAccounts());
    }

    @Test public void updateAccount_01() {
        ATMSessionController as = new ATMSessionController();
        HashMap<String, IAccount> accountsMap = new HashMap<>();
        String accountNumber = randomString();
        Integer amount = 0;
        IAccount account = Account.newBuilder().setAccountNumber(accountNumber)
            .setBalance(amount).build();
        accountsMap.put(accountNumber, account);
        as.setAccounts(randomString(), accountsMap);
        assertEquals(amount, as.getAccounts().get(accountNumber).getBalance());
        amount++;
        IAccount updatedAccount = Account.newBuilder().setAccountNumber(accountNumber)
            .setBalance(amount).build();
        as.updateAccount(updatedAccount);
        assertEquals(amount, as.getAccounts().get(accountNumber).getBalance());
    }

    @Test public void updateAccount_02() {
        ATMSessionController as = new ATMSessionController();
        HashMap<String, IAccount> accountsMap = new HashMap<>();
        String accountNumber = randomString();
        IAccount account = Account.newBuilder().setAccountNumber(accountNumber)
            .setBalance(randomInt()).build();
        accountsMap.put(accountNumber, account);
        as.setAccounts(randomString(), accountsMap);
        assertNotNull(as.getAccounts().get(accountNumber));
        String anotherAccountNumber = randomString();
        IAccount anotherAccount = Account.newBuilder().setAccountNumber(anotherAccountNumber)
            .setBalance(randomInt()).build();
        as.updateAccount(anotherAccount);
        assertNotNull(as.getAccounts().get(anotherAccountNumber));
    }

    @Test public void setSelectedAccountNumber() {
    }

    @Test public void getTokenId() {
        ATMSessionController as = new ATMSessionController();
        as.setAccounts(randomString(), randomMap());
        assertNotNull(as.getTokenId());
    }

    @Test public void getCard() {
        ATMSessionController as = new ATMSessionController();
        as.setCard(Card.newBuilder().setCardNumber(randomString()).setCvc(randomString()).setExpirationDate(randomString()).setHolderName(randomString()).build());
        assertNotNull(as.getCard());
    }

    @Test public void getAccounts() {
        ATMSessionController as = new ATMSessionController();
        as.setAccounts(randomString(), randomMap());
        assertNotNull(as.getAccounts());
    }

    @Test public void getSelectedAccount_01() {
        ATMSessionController as = new ATMSessionController();
        String selectedAccountNo = randomString();
        HashMap<String, IAccount> accountsMap = new HashMap<>();
        accountsMap.put(selectedAccountNo,
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build());
        as.setAccounts(randomString(), accountsMap);
        as.setSelectedAccountNumber(selectedAccountNo);
        assertNotNull(as.getSelectedAccount());
    }

    @Test public void getSelectedAccount_02() {
        ATMSessionController as = new ATMSessionController();
        String selectedAccountNo = randomString();
        as.setAccounts(randomString(), null);
        as.setSelectedAccountNumber(selectedAccountNo);
        assertNull(as.getSelectedAccount());
    }

    @Test public void getSelectedAccount_03() {
        ATMSessionController as = new ATMSessionController();
        String selectedAccountNo = randomString();
        HashMap<String, IAccount> accountsMap = new HashMap<>();
        accountsMap.put(selectedAccountNo,
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build());
        as.setAccounts(randomString(), accountsMap);
        as.setSelectedAccountNumber(null);
        assertNull(as.getSelectedAccount());
    }

    @Test public void getSelectedAccount_04() {
        ATMSessionController as = new ATMSessionController();
        String selectedAccountNo = randomString();
        String anotherAccountNo = randomString();
        HashMap<String, IAccount> accountsMap = new HashMap<>();
        accountsMap.put(selectedAccountNo,
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build());
        as.setAccounts(randomString(), accountsMap);
        as.setSelectedAccountNumber(anotherAccountNo);
        assertNull(as.getSelectedAccount());
    }

    @Test public void clear() {
        ATMSessionController as = new ATMSessionController();
        String selectedAccountNo = randomString();
        HashMap<String, IAccount> accountsMap = new HashMap<>();
        accountsMap.put(selectedAccountNo,
            Account.newBuilder().setAccountNumber(randomString()).setBalance(randomInt()).build());
        as.setAccounts(randomString(), accountsMap);
        as.setSelectedAccountNumber(selectedAccountNo);
        as.clear();
        assertNull(as.getSelectedAccount());
        assertNull(as.getAccounts());
        assertNull(as.getCard());
        assertNull(as.getTokenId());
    }
}
