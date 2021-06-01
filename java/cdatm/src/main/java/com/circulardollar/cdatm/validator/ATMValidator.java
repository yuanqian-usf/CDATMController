package com.circulardollar.cdatm.validator;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.downstream.model.deposit.IDeposit;
import com.circulardollar.cdatm.business.downstream.model.error.IError;
import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.withdraw.IWithdraw;
import com.circulardollar.cdatm.business.downstream.validation.IValidator;
import com.circulardollar.cdatm.business.downstream.validation.account.AccountValidator;
import com.circulardollar.cdatm.business.downstream.validation.card.CardValidator;
import com.circulardollar.cdatm.business.downstream.validation.config.ConfigurationsValidator;
import com.circulardollar.cdatm.business.downstream.validation.deposit.DepositValidator;
import com.circulardollar.cdatm.business.downstream.validation.pin.PinValidator;
import com.circulardollar.cdatm.business.downstream.validation.session.SessionControllerValidator;
import com.circulardollar.cdatm.business.downstream.validation.state.StateControllerValidator;
import com.circulardollar.cdatm.business.downstream.validation.withdraw.WithdrawValidator;
import com.circulardollar.cdatm.config.IATMConfigurations;
import com.circulardollar.cdatm.session.IATMSessionController;
import com.circulardollar.cdatm.state.IATMStateController;
import java.util.Objects;
import java.util.Optional;

public class ATMValidator implements IATMValidator {

  public final IATMConfigurations atmConfigurations;
  private final IValidator<IATMConfigurations> configurationsValidator;
  private IValidator<IATMStateController> stateControllerValidator;
  private IValidator<IATMSessionController> sessionControllerValidator;
  private IValidator<ICard> cardValidator;
  private IValidator<IPin> pinValidator;
  private IValidator<IAccount> accountValidator;
  private IValidator<IDeposit> depositValidator;
  private IValidator<IWithdraw> withdrawValidator;

  @Override
  public IATMConfigurations getATMConfigurations() {
    return atmConfigurations;
  }

  ATMValidator(
      IATMConfigurations atmConfigurations,
      IValidator<IATMConfigurations> configurationsValidator,
      IValidator<IATMStateController> stateControllerValidator,
      IValidator<IATMSessionController> sessionControllerValidator,
      IValidator<ICard> cardValidator,
      IValidator<IPin> pinValidator,
      IValidator<IAccount> accountValidator,
      IValidator<IDeposit> depositValidator,
      IValidator<IWithdraw> withdrawValidator)
      throws IllegalArgumentException {
    Objects.requireNonNull(atmConfigurations);
    this.atmConfigurations = atmConfigurations;
    this.configurationsValidator =
        initValidator(configurationsValidator, new ConfigurationsValidator());
    if (validateConfigurations(atmConfigurations).isPresent())
      throw new IllegalArgumentException("invalid configs");
    this.stateControllerValidator =
        initValidator(stateControllerValidator, new StateControllerValidator());
    this.sessionControllerValidator =
        initValidator(sessionControllerValidator, new SessionControllerValidator());
    this.cardValidator = initValidator(cardValidator, new CardValidator(atmConfigurations));
    this.pinValidator = initValidator(pinValidator, new PinValidator(atmConfigurations));
    this.accountValidator = initValidator(accountValidator, new AccountValidator());
    this.depositValidator =
        initValidator(depositValidator, new DepositValidator(atmConfigurations));
    this.withdrawValidator =
        initValidator(withdrawValidator, new WithdrawValidator(atmConfigurations));
  }

  public static Builder newBuilder(IATMConfigurations iatmConfigurations) {
    return new Builder(iatmConfigurations);
  }

  <T extends IValidator<?>> T initValidator(T inputValidator, T defaultValidator) {
    Objects.requireNonNull(defaultValidator);
    if (inputValidator == null) return defaultValidator;
    return inputValidator;
  }

  @Override
  public Optional<IError> validateConfigurations(IATMConfigurations atmConfigurations) {
    return configurationsValidator.validate(atmConfigurations);
  }

  @Override
  public Optional<IError> validateStateController(IATMStateController atmStateController) {
    return stateControllerValidator.validate(atmStateController);
  }

  @Override
  public Optional<IError> validateSessionController(IATMSessionController atmSessionController) {
    return sessionControllerValidator.validate(atmSessionController);
  }

  @Override
  public Optional<IError> validateCard(ICard card) {
    return cardValidator.validate(card);
  }

  @Override
  public Optional<IError> validatePin(IPin pin) {
    return pinValidator.validate(pin);
  }

  @Override
  public Optional<IError> validateAccount(IAccount account) {
    return accountValidator.validate(account);
  }

  @Override
  public Optional<IError> validateDeposit(IDeposit deposit) {
    return depositValidator.validate(deposit);
  }

  @Override
  public Optional<IError> validateWithdraw(IWithdraw withdraw) {
    return withdrawValidator.validate(withdraw);
  }

  public static class Builder {

    private final IATMConfigurations atmConfigurations;
    private IValidator<IATMConfigurations> configurationsValidator;
    private IValidator<IATMStateController> stateControllerValidator;
    private IValidator<IATMSessionController> sessionControllerValidator;
    private IValidator<ICard> cardValidator;
    private IValidator<IPin> pinValidator;
    private IValidator<IAccount> accountValidator;
    private IValidator<IDeposit> depositValidator;
    private IValidator<IWithdraw> withdrawValidator;

    private Builder(IATMConfigurations atmConfigurations) {
      Objects.requireNonNull(atmConfigurations);
      this.atmConfigurations = atmConfigurations;
    }

    public Builder setConfigurationsValidator(IValidator<IATMConfigurations> validator) {
      Objects.requireNonNull(validator);
      this.configurationsValidator = validator;
      return this;
    }

    public Builder setStateControllerValidator(IValidator<IATMStateController> validator) {
      Objects.requireNonNull(validator);
      this.stateControllerValidator = validator;
      return this;
    }

    public Builder setSessionControllerValidator(IValidator<IATMSessionController> validator) {
      Objects.requireNonNull(validator);
      this.sessionControllerValidator = validator;
      return this;
    }

    public Builder setCardValidator(IValidator<ICard> validator) {
      Objects.requireNonNull(validator);
      this.cardValidator = validator;
      return this;
    }

    public Builder setPinValidator(IValidator<IPin> validator) {
      Objects.requireNonNull(validator);
      this.pinValidator = validator;
      return this;
    }

    public Builder setAccountValidator(IValidator<IAccount> validator) {
      Objects.requireNonNull(validator);
      this.accountValidator = validator;
      return this;
    }

    public Builder setDepositValidator(IValidator<IDeposit> validator) {
      Objects.requireNonNull(validator);
      this.depositValidator = validator;
      return this;
    }

    public Builder setWithdrawValidator(IValidator<IWithdraw> validator) {
      Objects.requireNonNull(validator);
      this.withdrawValidator = validator;
      return this;
    }

    public ATMValidator build() {
      return new ATMValidator(
          atmConfigurations,
          configurationsValidator,
          stateControllerValidator,
          sessionControllerValidator,
          cardValidator,
          pinValidator,
          accountValidator,
          depositValidator,
          withdrawValidator);
    }
  }
}
