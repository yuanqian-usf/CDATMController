package com.circulardollar.cdatm.config;

import com.circulardollar.cdatm.constant.APIVersions;

public interface IATMConfigurations {
  Integer MIN_CARD_NUMBER_LENGTH = 4;
  Integer MAX_CARD_NUMBER_LENGTH = 16;
  Integer MIN_PIN_LENGTH = 1;
  Integer MAX_PIN_LENGTH = 6;
  Integer MIN_DEPOSIT_AMOUNT = 1;
  Integer MAX_DEPOSIT_AMOUNT = 1000;
  Integer MIN_WITHDRAW_AMOUNT = 1;
  Integer MAX_WITHDRAW_AMOUNT = 1000;

  Integer getMinCardNumberLength();

  Integer getMaxCardNumberLength();

  Integer getMinPinLength();

  Integer getMaxPinLength();

  Integer getMinDepositAmount();

  Integer getMaxDepositAmount();

  Integer getMinWithdrawAmount();

  Integer getMaxWithdrawAmount();

  APIVersions getAPIVersion();
}
