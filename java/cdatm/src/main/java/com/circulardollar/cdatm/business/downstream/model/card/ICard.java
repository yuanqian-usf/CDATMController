package com.circulardollar.cdatm.business.downstream.model.card;

public interface ICard {
  String getHolderName();

  String getCardNumber();

  String getCVC();

  String getExpirationDate();
}
