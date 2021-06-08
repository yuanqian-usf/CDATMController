package com.circulardollar.cdatm.network;

import static com.circulardollar.cdatm.TestBase.randomMap;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertNull;

import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.network.INetworkClientV2.IBuilder;
import com.circulardollar.cdatm.validator.upstream.IATMRemoteValidator;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class INetworkClientV2Test {

  IBuilder iBuilder;
  @Before
  public void setUp() {
    iBuilder = new IBuilder() {

      @Override
      public IBuilder setAccountsRecordTable(
          Map<ILoginRecord, IAccountsRecord> accountsRecordTable) {
        return super.setAccountsRecordTable(accountsRecordTable);
      }

      @Override
      public IBuilder setIATMRemoteValidator(IATMRemoteValidator validator) {
        return super.setIATMRemoteValidator(validator);
      }

      @Override
      public IBuilder setUrl(String url) {
        return super.setUrl(url);
      }

      @Override
      public INetworkClientV2 build() {
        return null;
      }
    };
  }

  @Test
  public void verifyPinV2() {
    assertNull(iBuilder.setAccountsRecordTable(randomMap()));
  }

  @Test
  public void getAccounts() {
    IBuilder iBuilder = new IBuilder() {

      @Override
      public IBuilder setAccountsRecordTable(
          Map<ILoginRecord, IAccountsRecord> accountsRecordTable) {
        return super.setAccountsRecordTable(accountsRecordTable);
      }

      @Override
      public IBuilder setIATMRemoteValidator(IATMRemoteValidator validator) {
        return super.setIATMRemoteValidator(validator);
      }

      @Override
      public IBuilder setUrl(String url) {
        return super.setUrl(url);
      }

      @Override
      public INetworkClientV2 build() {
        return null;
      }
    };
    assertNull(iBuilder.setUrl(randomString()));
    assertNull(iBuilder.setIATMRemoteValidator(new IATMRemoteValidator() {
      @Override
      public Optional<IErrorRecord> validate(Object remoteRecord) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateLoginRecord(ILoginRecord loginRecord) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateAuthRecord(IAuthRecord authRecord) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateAuthRecordV2(IAuthRecordV2 authRecordV2) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateRequestWithToken(IRequestWithToken requestWithToken) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateAccountsRecord(IAccountsRecord accountsRecord) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateDepositRecord(IDepositRecord depositRecord) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateWithdrawRecord(IWithdrawRecord withdrawRecord) {
        return Optional.empty();
      }

      @Override
      public Optional<IErrorRecord> validateLogoutRecord(ILogoutRecord logoutRecord) {
        return Optional.empty();
      }
    }));
  }
}
