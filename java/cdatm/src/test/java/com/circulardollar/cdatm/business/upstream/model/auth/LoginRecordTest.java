package com.circulardollar.cdatm.business.upstream.model.auth;

import com.circulardollar.cdatm.business.upstream.model.card.CardRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.PinRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;


public class LoginRecordTest {

    @Test public void testToString() {
        String mockString = randomString();
        CardRecord mockCard = CardRecord.newBuilder()
            .setHolderName(mockString)
            .setExpirationDate(mockString)
            .setCvc(mockString)
            .setCardNumber(mockString)
            .build();

        assertNotNull(LoginRecord.newBuilder().setCard(mockCard)
            .setPin(PinRecord.newBuilder().setPinNumber(mockString).build()).build().toString());
    }
}
