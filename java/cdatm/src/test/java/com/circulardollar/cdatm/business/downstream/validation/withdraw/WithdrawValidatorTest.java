package com.circulardollar.cdatm.business.downstream.validation.withdraw;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.model.withdraw.Withdraw;
import com.circulardollar.cdatm.config.ATMConfigurations;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WithdrawValidatorTest {

  @Test public void validate_null_account() {
    assertTrue(
        new WithdrawValidator(ATMConfigurations.newBuilder().build())
            .validate(new IWithdraw() {
                        @Override public IAccount getAccount() {
                          return null;
                        }

                        @Override public Integer getAmount() {
                          return randomInt();
                        }

                        @Override public Long getTimeStamp() {
                          return null;
                        }
                      })
            .isPresent());
  }

  @Test public void validate_null_amount() {
    assertTrue(
        new WithdrawValidator(ATMConfigurations.newBuilder().build())
            .validate(
                new IWithdraw() {
                  @Override public IAccount getAccount() {
                    return new IAccount() {
                      @Override public String getAccountNumber() {
                        return null;
                      }

                      @Override public Integer getBalance() {
                        return null;
                      }
                    };
                  }

                  @Override public Integer getAmount() {
                    return null;
                  }

                  @Override public Long getTimeStamp() {
                    return null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_01() {
    assertTrue(
        new WithdrawValidator(ATMConfigurations.newBuilder().build()).validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertFalse(
        new WithdrawValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Withdraw.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(Integer.MAX_VALUE)
                            .build())
                    .setAmount(ATMConfigurations.MIN_WITHDRAW_AMOUNT)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_03() {
    assertTrue(
        new WithdrawValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Withdraw.newBuilder()
                    .setAccount(
                        Account.newBuilder().setAccountNumber("").setBalance(randomInt()).build())
                    .setAmount(-1)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_04() {
    int balance = randomInt();
    assertTrue(
        new WithdrawValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Withdraw.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(balance)
                            .build())
                    .setAmount(balance + 1)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_05() {
    int withdrawAmount = ATMConfigurations.MAX_WITHDRAW_AMOUNT + 1;
    assertTrue(
        new WithdrawValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Withdraw.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomInt())
                            .build())
                    .setAmount(withdrawAmount)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_06() {
    int withdrawAmount = ATMConfigurations.MIN_WITHDRAW_AMOUNT - 1;
    assertTrue(
        new WithdrawValidator(ATMConfigurations.newBuilder().build())
            .validate(
                Withdraw.newBuilder()
                    .setAccount(
                        Account.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomInt())
                            .build())
                    .setAmount(withdrawAmount)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }
}
