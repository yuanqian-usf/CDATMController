package com.circulardollar.cdatm.business.upstream.validation.deposit;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomLong;
import static com.circulardollar.cdatm.TestBase.randomPostiveInt;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.downstream.model.deposit.Deposit;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.validation.deposit.DepositValidator;
import com.circulardollar.cdatm.business.upstream.model.deposit.DepositRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.config.ATMConfigurations;
import org.junit.Test;

public class DepositRecordRemoteValidatorTest {
  @Test
  public void validate_null_account() {
    assertTrue(
        new DepositRecordRemoteValidator()
            .validate(
                new IDepositRecord() {
                  @Override
                  public Integer getAmount() {
                    return randomInt();
                  }

                  @Override
                  public IAccountRecord getAccount() {
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
        new DepositRecordRemoteValidator()
            .validate(
                new IDepositRecord() {
                  @Override
                  public Integer getAmount() {
                    return null;
                  }

                  @Override
                  public IAccountRecord getAccount() {
                    return new IAccountRecord() {
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
        new DepositRecordRemoteValidator().validate(null).isPresent());
  }

  @Test
  public void validate_02() {
    assertFalse(
        new DepositRecordRemoteValidator()
            .validate(
                DepositRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder()
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
        new DepositRecordRemoteValidator()
            .validate(
                DepositRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder()
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
        new DepositRecordRemoteValidator()
            .validate(
                DepositRecord.newBuilder()
                    .setAccount(
                        AccountRecord.newBuilder()
                            .setAccountNumber(randomString())
                            .setBalance(Integer.MAX_VALUE)
                            .build())
                    .setAmount(randomPostiveInt())
                    .setTimeStamp(randomLong())
                    .build())
            .isPresent());
  }
}
