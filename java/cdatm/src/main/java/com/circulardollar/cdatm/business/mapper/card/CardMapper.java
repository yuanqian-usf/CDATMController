package com.circulardollar.cdatm.business.mapper.card;

import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import com.circulardollar.cdatm.business.upstream.model.card.CardRecord;
import com.circulardollar.cdatm.business.upstream.model.card.ICardRecord;

public class CardMapper {
  public static ICardRecord up(ICard card) {
    return CardRecord.newBuilder()
        .setHolderName(card.getHolderName())
        .setCardNumber(card.getCardNumber())
        .setCvc(card.getCVC())
        .setExpirationDate(card.getExpirationDate())
        .build();
  }

  public static ICard down(ICardRecord card) {
    return Card.newBuilder()
        .setHolderName(card.getHolderName())
        .setCardNumber(card.getCardNumber())
        .setCvc(card.getCVC())
        .setExpirationDate(card.getExpirationDate())
        .build();
  }
}
