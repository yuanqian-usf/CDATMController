package com.circulardollar.cdatm.business.mapper.card;

import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.upstream.model.card.CardRecord;
import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;

public final class CardMapper {
  private CardMapper() {}

  public static ICardRecord up(ICard card) {
    if (card == null) {
      return null;
    }
    return CardRecord.newBuilder()
        .setHolderName(card.getHolderName())
        .setCardNumber(card.getCardNumber())
        .setCvc(card.getCVC())
        .setExpirationDate(card.getExpirationDate())
        .build();
  }

  public static ICard down(ICardRecord card) {
    if (card == null) {
      return null;
    }
    return Card.newBuilder()
        .setHolderName(card.getHolderName())
        .setCardNumber(card.getCardNumber())
        .setCvc(card.getCVC())
        .setExpirationDate(card.getExpirationDate())
        .build();
  }
}
