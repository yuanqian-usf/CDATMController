package com.circulardollar.cdatm.business.mapper.card;

import com.circulardollar.cdatm.business.downstream.model.account.Account;
import com.circulardollar.cdatm.business.downstream.model.card.Card;
import com.circulardollar.cdatm.business.mapper.account.AccountMapper;
import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.circulardollar.cdatm.business.upstream.model.card.CardRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

public class CardMapperTest {

    @Test public void CardMapper_up_01() {
        assertNull(CardMapper.up(null));
    }

    @Test public void CardMapper_up_02() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.up(
                Card.newBuilder().setCardNumber(mockString).setHolderName(mockString).setCvc(mockString).setExpirationDate(mockString).build())
                .getCardNumber());
    }

    @Test public void CardMapper_up_03() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.up(
                Card.newBuilder().setCardNumber(mockString).setHolderName(mockString).setCvc(mockString).setExpirationDate(mockString).build())
                .getHolderName());
    }

    @Test public void CardMapper_up_04() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.up(
                Card.newBuilder().setCardNumber(mockString).setHolderName(mockString).setCvc(mockString).setExpirationDate(mockString).build())
                .getCVC());
    }

    @Test public void CardMapper_up_05() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.up(
                Card.newBuilder().setCardNumber(mockString).setHolderName(mockString).setCvc(mockString).setExpirationDate(mockString).build())
                .getExpirationDate());
    }

    @Test public void CardMapper_down_01() {
        assertNull(CardMapper.down(null));
    }

    @Test public void CardMapper_down_02() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.down(
                CardRecord.newBuilder()
                    .setCardNumber(mockString)
                    .setHolderName(mockString)
                    .setCvc(mockString)
                    .setExpirationDate(mockString)
                    .build())
                .getCardNumber());
    }

    @Test public void CardMapper_down_03() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.down(
                CardRecord.newBuilder()
                    .setCardNumber(mockString)
                    .setHolderName(mockString)
                    .setCvc(mockString)
                    .setExpirationDate(mockString)
                    .build())
                .getHolderName());
    }

    @Test public void CardMapper_down_04() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.down(
                CardRecord.newBuilder()
                    .setCardNumber(mockString)
                    .setHolderName(mockString)
                    .setCvc(mockString)
                    .setExpirationDate(mockString)
                    .build())
                .getCVC());
    }

    @Test public void CardMapper_down_05() {
        String mockString = randomString();
        assertEquals(
            mockString,
            CardMapper.down(
                CardRecord.newBuilder()
                    .setCardNumber(mockString)
                    .setHolderName(mockString)
                    .setCvc(mockString)
                    .setExpirationDate(mockString)
                    .build())
                .getExpirationDate());
    }
}
