package com.circulardollar.cdatm.business.mapper.pin;

import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.business.upstream.model.pin.PinRecord;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.validRandomPinNumber;
import static org.junit.Assert.assertNotNull;

public class PinMapperTest {

    @Test public void up() {
        assertNotNull(PinMapper.up(Pin.newBuilder().setPinNumber(validRandomPinNumber()).build()));
    }

    @Test public void down() {
        assertNotNull(
            PinMapper.down(PinRecord.newBuilder().setPinNumber(validRandomPinNumber()).build()));
    }
}
