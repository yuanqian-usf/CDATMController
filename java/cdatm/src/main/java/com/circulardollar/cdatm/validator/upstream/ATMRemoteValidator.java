package com.circulardollar.cdatm.validator.upstream;

import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.IAuthRecordV2;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILogoutRecord;
import com.circulardollar.cdatm.business.upstream.model.deposit.IDepositRecord;
import com.circulardollar.cdatm.business.upstream.model.error.IErrorRecord;
import com.circulardollar.cdatm.business.upstream.model.token.IRequestWithToken;
import com.circulardollar.cdatm.business.upstream.model.withdraw.IWithdrawRecord;
import com.circulardollar.cdatm.business.upstream.validation.IRemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.account.AccountsRecordRemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.auth.AuthRecordRemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.auth.AuthRecordV2RemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.auth.LoginRecordRemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.auth.LogoutRecordRemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.deposit.DepositRecordRemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.token.RequestWithTokenRemoteValidator;
import com.circulardollar.cdatm.business.upstream.validation.withdraw.WithdrawRecordRemoteValidator;
import java.util.Objects;
import java.util.Optional;

public class ATMRemoteValidator implements IATMRemoteValidator {

  private final IRemoteValidator<ILoginRecord> loginValidator;
  private final IRemoteValidator<IRequestWithToken> requestWithTokenValidator;
  private final IRemoteValidator<IAuthRecord> authValidator;
  private final IRemoteValidator<IAuthRecordV2> authV2Validator;
  private final IRemoteValidator<IAccountsRecord> accountsValidator;
  private final IRemoteValidator<IDepositRecord> depositValidator;
  private final IRemoteValidator<IWithdrawRecord> withdrawValidator;
  private final IRemoteValidator<ILogoutRecord> logoutValidator;

  ATMRemoteValidator(
      IRemoteValidator<ILoginRecord> loginValidator,
      IRemoteValidator<IRequestWithToken> requestWithTokenValidator,
      IRemoteValidator<IAuthRecord> authValidator,
      IRemoteValidator<IAuthRecordV2> authV2Validator,
      IRemoteValidator<IAccountsRecord> accountsValidator,
      IRemoteValidator<IDepositRecord> depositValidator,
      IRemoteValidator<IWithdrawRecord> withdrawValidator,
      IRemoteValidator<ILogoutRecord> logoutValidator)
      throws IllegalArgumentException {
    this.loginValidator = initValidator(loginValidator, new LoginRecordRemoteValidator());
    this.requestWithTokenValidator = initValidator(requestWithTokenValidator, new RequestWithTokenRemoteValidator());
    this.authValidator = initValidator(authValidator, new AuthRecordRemoteValidator());
    this.authV2Validator = initValidator(authV2Validator, new AuthRecordV2RemoteValidator());
    this.accountsValidator = initValidator(accountsValidator, new AccountsRecordRemoteValidator());
    this.depositValidator = initValidator(depositValidator, new DepositRecordRemoteValidator());
    this.withdrawValidator = initValidator(withdrawValidator, new WithdrawRecordRemoteValidator());
    this.logoutValidator = initValidator(logoutValidator, new LogoutRecordRemoteValidator());
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  <T extends IRemoteValidator<?>> T initValidator(T inputValidator, T defaultValidator) {
    Objects.requireNonNull(defaultValidator);
    if (inputValidator == null) return defaultValidator;
    return inputValidator;
  }

  @Override
  public Optional<IErrorRecord> validate(Object remoteRecord) {
      if(remoteRecord instanceof ILoginRecord) {
        return validateLoginRecord((ILoginRecord) remoteRecord);
      }
      if(remoteRecord instanceof IRequestWithToken) {
        return validateRequestWithToken((IRequestWithToken) remoteRecord);
      }
      if(remoteRecord instanceof IAuthRecord) {
        return validateAuthRecord((IAuthRecord) remoteRecord);
      }
      if(remoteRecord instanceof IAuthRecordV2) {
        return validateAuthRecordV2((IAuthRecordV2) remoteRecord);
      }
      if(remoteRecord instanceof IAccountsRecord) {
        return validateAccountsRecord((IAccountsRecord) remoteRecord);
      }
      if(remoteRecord instanceof IDepositRecord) {
        return validateDepositRecord((IDepositRecord) remoteRecord);
      }
      if(remoteRecord instanceof IWithdrawRecord) {
        return validateWithdrawRecord((IWithdrawRecord) remoteRecord);
      }
      if(remoteRecord instanceof ILogoutRecord) {
        return validateLogoutRecord((ILogoutRecord) remoteRecord);
    }
    return Optional.empty();
  }

  @Override
  public Optional<IErrorRecord> validateLoginRecord(ILoginRecord loginRecord) {
    return loginValidator.validate(loginRecord);
  }

  @Override
  public Optional<IErrorRecord> validateAuthRecord(IAuthRecord authRecord) {
    return authValidator.validate(authRecord);
  }

  @Override
  public Optional<IErrorRecord> validateAuthRecordV2(IAuthRecordV2 authRecordV2) {
    return authV2Validator.validate(authRecordV2);
  }

  @Override
  public Optional<IErrorRecord> validateRequestWithToken(IRequestWithToken requestWithToken) {
    return requestWithTokenValidator.validate(requestWithToken);
  }

  @Override
  public Optional<IErrorRecord> validateAccountsRecord(IAccountsRecord accountsRecord) {
    return accountsValidator.validate(accountsRecord);
  }

  @Override
  public Optional<IErrorRecord> validateDepositRecord(IDepositRecord depositRecord) {
    return depositValidator.validate(depositRecord);
  }

  @Override
  public Optional<IErrorRecord> validateWithdrawRecord(IWithdrawRecord withdrawRecord) {
    return withdrawValidator.validate(withdrawRecord);
  }

  @Override
  public Optional<IErrorRecord> validateLogoutRecord(ILogoutRecord logoutRecord) {
    return logoutValidator.validate(logoutRecord);
  }

  public static class Builder {

    private IRemoteValidator<ILoginRecord> loginValidator;
    private IRemoteValidator<IRequestWithToken> requestWithTokenValidator;
    private IRemoteValidator<IAuthRecord> authValidator;
    private IRemoteValidator<IAuthRecordV2> authV2Validator;
    private IRemoteValidator<IAccountsRecord> accountsValidator;
    private IRemoteValidator<IDepositRecord> depositValidator;
    private IRemoteValidator<IWithdrawRecord> withdrawValidator;
    private IRemoteValidator<ILogoutRecord> logoutValidator;

    private Builder() {}

    public Builder setLoginValidator(
        IRemoteValidator<ILoginRecord> validator) {
      Objects.requireNonNull(validator);
      this.loginValidator = validator;
      return this;
    }

    public Builder setRequestWithTokenValidator(IRemoteValidator<IRequestWithToken> validator) {
      Objects.requireNonNull(validator);
      this.requestWithTokenValidator = validator;
      return this;
    }

    public Builder setAuthValidator(IRemoteValidator<IAuthRecord> validator) {
      Objects.requireNonNull(validator);
      this.authValidator = validator;
      return this;
    }

    public Builder setAuthV2Validator(IRemoteValidator<IAuthRecordV2> validator) {
      Objects.requireNonNull(validator);
      this.authV2Validator = validator;
      return this;
    }
    public Builder setAccountsValidator(IRemoteValidator<IAccountsRecord> validator) {
      Objects.requireNonNull(validator);
      this.accountsValidator = validator;
      return this;
    }

    public Builder setDepositValidator(IRemoteValidator<IDepositRecord> validator) {
      Objects.requireNonNull(validator);
      this.depositValidator = validator;
      return this;
    }

    public Builder setWithdrawValidator(IRemoteValidator<IWithdrawRecord> validator) {
      Objects.requireNonNull(validator);
      this.withdrawValidator = validator;
      return this;
    }

    public Builder setLogoutValidator(IRemoteValidator<ILogoutRecord> validator) {
      Objects.requireNonNull(validator);
      this.logoutValidator = validator;
      return this;
    }

    public ATMRemoteValidator build() {
      return new ATMRemoteValidator(
          loginValidator,
          requestWithTokenValidator,
          authValidator,
          authV2Validator,
          accountsValidator,
          depositValidator,
          withdrawValidator,
          logoutValidator);
    }
  }


}
