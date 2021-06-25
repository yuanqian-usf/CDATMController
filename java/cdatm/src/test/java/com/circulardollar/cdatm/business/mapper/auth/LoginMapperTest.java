package com.circulardollar.cdatm.business.mapper.auth;

import com.circulardollar.cdatm.business.upstream.model.auth.LoginRecord;
import com.circulardollar.cdatm.business.upstream.model.card.CardRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.PinRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

public class LoginMapperTest {

    @Test public void LoginMapper_down_01() {
        assertNull(LoginMapper.down(null));
    }

    @Test public void LoginMapper_down_02() {
        String mockString = randomString();
        CardRecord mockCard = CardRecord.newBuilder().setHolderName(mockString).setExpirationDate(mockString).setCvc(mockString).setCardNumber(mockString).build();
        assertEquals(
            mockCard.getCardNumber(),
            LoginMapper.down(
                LoginRecord.newBuilder().setCard(mockCard)
                    .setPin(PinRecord.newBuilder().setPinNumber(mockString).build()).build())
                .getCard().getCardNumber());

        assertEquals(
            mockCard.getCVC(),
            LoginMapper.down(
                LoginRecord.newBuilder().setCard(mockCard)
                    .setPin(PinRecord.newBuilder().setPinNumber(mockString).build()).build())
                .getCard().getCVC());

        assertEquals(
            mockCard.getExpirationDate(),
            LoginMapper.down(
                LoginRecord.newBuilder().setCard(mockCard)
                    .setPin(PinRecord.newBuilder().setPinNumber(mockString).build()).build())
                .getCard().getExpirationDate());

        assertEquals(
            mockCard.getHolderName(),
            LoginMapper.down(
                LoginRecord.newBuilder().setCard(mockCard)
                    .setPin(PinRecord.newBuilder().setPinNumber(mockString).build()).build())
                .getCard().getHolderName());
    }

    @Test public void LoginMapper_down_03() {
        String mockString = randomString();
        CardRecord mockCard = CardRecord.newBuilder().setHolderName(mockString).setExpirationDate(mockString).setCvc(mockString).setCardNumber(mockString).build();
        PinRecord pinCard = PinRecord.newBuilder().setPinNumber(mockString).build();
        assertEquals(
            pinCard.getPinNumber(),
            LoginMapper.down(
                LoginRecord.newBuilder().setCard(mockCard)
                    .setPin(PinRecord.newBuilder().setPinNumber(mockString).build()).build())
                .getPin().getPinNumber());

    }

}
