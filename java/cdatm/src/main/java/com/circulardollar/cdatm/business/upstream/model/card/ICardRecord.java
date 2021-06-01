package com.circulardollar.cdatm.business.upstream.model.card;

public interface ICardRecord {
  String getHolderName();

  String getCardNumber();

  String getCVC();

  String getExpirationDate();
}
