package com.circulardollar.cdatm.validator.upstream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.circulardollar.cdatm.business.upstream.model.account.IAccountRecord;
import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class ATMRemoteValidatorTest {

  @Test(expected = NullPointerException.class)
  public void initValidator_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().build().initValidator(null, null);
  }

  @Test
  public void validateLogin() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateLoginRecord(null)
            .isPresent());
  }

  @Test
  public void validateRequestWithToken() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateRequestWithToken(null)
            .isPresent());
  }

  @Test
  public void validateAuth() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateAuthRecord(null)
            .isPresent());
  }

  @Test
  public void validateAuthV2() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateAuthRecordV2(null)
            .isPresent());
  }

  @Test
  public void validateAccounts() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateAccountsRecord(null)
            .isPresent());
  }

  @Test
  public void validateDeposit() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateDepositRecord(null)
            .isPresent());
  }

  @Test
  public void validateWithdraw() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateWithdrawRecord(null)
            .isPresent());
  }

  @Test
  public void validateLogout() {
    assertTrue(
        new ATMRemoteValidator(null, null, null, null, null, null, null, null)
            .validateLogoutRecord(null)
            .isPresent());
  }

  @Test
  public void newBuilder() {
    assertNotNull(ATMRemoteValidator.newBuilder().build());
  }

  @Test
  public void initValidator_default() {
    IRemoteValidator<Object> actual = target -> Optional.empty();
    assertEquals(actual, ATMRemoteValidator.newBuilder().build().initValidator(null, actual));
  }

  @Test
  public void initValidator() {
    IRemoteValidator<Object> actual = target -> Optional.empty();

    IRemoteValidator<Object> defaultValidator = target -> Optional.empty();
    assertEquals(
        actual, ATMRemoteValidator.newBuilder().build().initValidator(actual, defaultValidator));
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateLogin_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setLoginValidator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateRequestWithToken_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setRequestWithTokenValidator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateAuth_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setAuthValidator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateAuthV2_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setAuthV2Validator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateAccounts_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setAccountsValidator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateDeposit_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setDepositValidator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateWithdraw_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setWithdrawValidator(null).build();
  }

  @Test(expected = NullPointerException.class)
  public void Builder_validateLogout_null_whenExceptionThrown_thenExpectationSatisfied() {
    ATMRemoteValidator.newBuilder().setLogoutValidator(null).build();
  }

  @Test
  public void Builder_setLoginValidator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder().setLoginValidator(target -> Optional.empty()).build());
  }

  @Test
  public void Builder_setRequestWithTokenValidator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder()
            .setRequestWithTokenValidator(target -> Optional.empty())
            .build());
  }

  @Test
  public void Builder_setAuthValidator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder().setAuthValidator(target -> Optional.empty()).build());
  }

  @Test
  public void Builder_setAuthV2Validator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder().setAuthV2Validator(target -> Optional.empty()).build());
  }

  @Test
  public void Builder_setAccountValidator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder().setAccountsValidator(target -> Optional.empty()).build());
  }

  @Test
  public void Builder_setDepositValidator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder().setDepositValidator(target -> Optional.empty()).build());
  }

  @Test
  public void Builder_setWithdrawValidator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder().setWithdrawValidator(target -> Optional.empty()).build());
  }

  @Test
  public void Builder_setLogoutValidator() {
    assertNotNull(
        ATMRemoteValidator.newBuilder().setLogoutValidator(target -> Optional.empty()).build());
  }

  @Test
  public void validate_ILoginRecord() {
    assertTrue(
        ATMRemoteValidator.newBuilder()
            .build()
            .validate(
                new ILoginRecord() {
                  @Override
                  public ICardRecord getCard() {
                    return null;
                  }

                  @Override
                  public IPinRecord getPin() {
                    return null;
                  }
                })
            .isPresent());
  }

  @Test
  public void validate_IRequestWithToken() {
    assertTrue(
        ATMRemoteValidator.newBuilder()
            .build()
            .validate((IRequestWithToken) () -> null)
            .isPresent());
  }

  @Test
  public void validate_IAuthRecord() {
    assertTrue(
        ATMRemoteValidator.newBuilder()
            .build()
            .validate(
                new IAuthRecord() {
                  @Override
                  public String getTokenId() {
                    return null;
                  }

                  @Override
                  public List<IAccountRecord> getAccounts() {
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
  public void validate_IAuthRecordV2() {
    assertTrue(
        ATMRemoteValidator.newBuilder()
            .build()
            .validate(
                new IAuthRecordV2() {
                  @Override
                  public String getTokenId() {
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
  public void validate_IAccountsRecord() {
    assertTrue(
        ATMRemoteValidator.newBuilder()
            .build()
            .validate(
                new IAccountsRecord() {
                  @Override
                  public List<IAccountRecord> getAccounts() {
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
  public void validate_IDepositRecord() {
    assertTrue(
        ATMRemoteValidator.newBuilder()
            .build()
            .validate(
                new IDepositRecord() {
                  @Override
                  public Integer getAmount() {
                    return null;
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
  public void validate_IWithdrawRecord() {
    assertTrue(
        ATMRemoteValidator.newBuilder()
            .build()
            .validate(
                new IWithdrawRecord() {
                  @Override
                  public IAccountRecord getAccount() {
                    return null;
                  }

                  @Override
                  public Integer getAmount() {
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
  public void validate_ILogoutRecord() {
    assertTrue(
        ATMRemoteValidator.newBuilder().build().validate((ILogoutRecord) () -> null).isPresent());
  }

  @Test
  public void validate_other() {
    assertFalse(ATMRemoteValidator.newBuilder().build().validate(null).isPresent());
  }
}
