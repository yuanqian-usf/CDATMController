package com.circulardollar.cdatm.business.downstream.validation.deposit;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomPostiveInt;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.deposit.Deposit;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.config.ATMConfigurations;
import org.junit.Test;

public class DepositValidatorTest {

  @Test
  public void validate_null_account() {
    assertTrue(
        new DepositValidator(ATMConfigurations.newBuilder().build())
            .validate(
                new IDeposit() {
                  @Override
                  public Integer getAmount() {
                    return randomInt();
                  }

                  @Override
                  public IAccount getAccount() {
                    return null;
                  }

                  @Override
                  public Long getTimeStamp() {
                    return null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_null_amount() {
    assertTrue(
        new DepositValidator(ATMConfigurations.newBuilder().build())
            .validate(
                new IDeposit() {
                  @Override
                  public Integer getAmount() {
                    return null;
                  }

                  @Override
                  public IAccount getAccount() {
                    return new IAccount() {
                      @Override
                      public String getAccountNumber() {
                        return null;
                      }

                      @Override
                      public Integer getBalance() {
                        return null;
                      }
                    };
                  }

                  @Override
                  public Long getTimeStamp() {
                    return null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_01() {
    assertTrue(
        new DepositValidator(ATMConfigurations.newBuilder().build()).validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertFalse(
        new DepositValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Deposit.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomPostiveInt())
                            .build())
                    .setAmount(randomPostiveInt())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_03() {
    assertTrue(
        new DepositValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Deposit.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(Integer.MAX_VALUE)
                            .build())
                    .setAmount(-1)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_04() {
    assertTrue(
        new DepositValidator(
                ATMConfigurations.newBuilder().setMinDepositAmount(Integer.MAX_VALUE).build())
            .validate(
                Deposit.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomPostiveInt())
                            .build())
                    .setAmount(randomPostiveInt())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_05() {
    assertTrue(
        new DepositValidator(
                ATMConfigurations.newBuilder().setMaxDepositAmount(Integer.MIN_VALUE).build())
            .validate(
                Deposit.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(Integer.MIN_VALUE)
                            .build())
                    .setAmount(1)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_06() {
    assertTrue(
        new DepositValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Deposit.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(Integer.MAX_VALUE)
                            .build())
                    .setAmount(randomPostiveInt())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }
}
