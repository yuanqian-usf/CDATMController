package com.circulardollar.cdatm.business.mapper;

import static com.circulardollar.cdatm.TestBase.REPEAT_TEST_ITERATION;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.mapper.account.AccountMapper;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.Test;

public class AccountMapperTest {

  @Test
  public void AccountMapper_up_01() {
    assertNull(AccountMapper.up(null));
  }

  @Test
  public void AccountMapper_up_02() {
    String mockString = anyString();
    assertEquals(
        mockString,
        AccountMapper.up(
                Account.newBuilder().setAccountNumber(mockString).setBalance(anyInt()).build())
            .getAccountNumber());
  }

  @Test
  public void AccountMapper_up_03() {
    int mockInt = anyInt();
    assertEquals(
        Optional.of(mockInt).get(),
        AccountMapper.up(
                Account.newBuilder().setAccountNumber(anyString()).setBalance(mockInt).build())
            .getBalance());
  }

  @Test
  public void AccountMapper_down_01() {
    assertNull(AccountMapper.down(null));
  }

  @Test
  public void AccountMapper_down_02() {
    String mockString = anyString();
    assertEquals(
        mockString,
        AccountMapper.down(
                AccountRecord.newBuilder()
                    .setAccountNumber(mockString)
                    .setBalance(anyInt())
                    .build())
            .getAccountNumber());
  }

  @Test
  public void AccountMapper_down_03() {
    int mockInt = anyInt();
    assertEquals(
        Optional.of(mockInt).get(),
        AccountMapper.down(
                AccountRecord.newBuilder()
                    .setAccountNumber(anyString())
                    .setBalance(mockInt)
                    .build())
            .getBalance());
  }

  @Test
  public void AccountMapper_map_01() {
    assertNull(AccountMapper.mapAccounts(null));
  }

  @Test
  public void AccountMapper_map_02() {
    List<IAccount> accounts = new ArrayList<>();
    for (int i = 0; i < REPEAT_TEST_ITERATION; i++) {
      accounts.add(
          Account.newBuilder().setAccountNumber(randomString()).setBalance(anyInt()).build());
    }

    Map<String, IAccount> actual = AccountMapper.mapAccounts(accounts);
    IntStream.range(0, accounts.size())
        .forEach(
            index -> {
              String accountNumber = accounts.get(index).getAccountNumber();
              assertTrue(actual.containsKey(accountNumber));
              assertEquals(accountNumber, actual.get(accountNumber).getAccountNumber());
            });
  }
}
