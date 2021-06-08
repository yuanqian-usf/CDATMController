package com.circulardollar.cdatm.business.upstream.validation.withdraw;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.model.withdraw.Withdraw;
import com.circulardollar.cdatm.business.downstream.validation.withdraw.WithdrawValidator;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.model.withdraw.WithdrawRecord;
import com.circulardollar.cdatm.config.ATMConfigurations;
import org.junit.Test;

public class WithdrawRecordRemoteValidatorTest {

  @Test public void validate_null_account() {
    assertTrue(
        new WithdrawRecordRemoteValidator()
            .validate(new IWithdrawRecord() {
              @Override public IAccountRecord getAccount() {
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
        new WithdrawRecordRemoteValidator()
            .validate(
                new IWithdrawRecord() {
                  @Override public IAccountRecord getAccount() {
                    return new IAccountRecord() {
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
        new WithdrawRecordRemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertFalse(
        new WithdrawRecordRemoteValidator()
            .validate(
                WithdrawRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder()
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
        new WithdrawRecordRemoteValidator()
            .validate(
                WithdrawRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder().setAccountNumber("").setBalance(randomInt()).build())
                    .setAmount(-1)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }

  @Test
  public void validate_04() {
    int balance = randomInt();
    assertTrue(
        new WithdrawRecordRemoteValidator()
            .validate(
                WithdrawRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder()
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
        new WithdrawRecordRemoteValidator()
            .validate(
                WithdrawRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder()
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
        new WithdrawRecordRemoteValidator()
            .validate(
                WithdrawRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(randomInt())
                            .build())
                    .setAmount(withdrawAmount)
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }
}
