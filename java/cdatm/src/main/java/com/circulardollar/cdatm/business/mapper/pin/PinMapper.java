package com.circulardollar.cdatm.business.mapper.pin;

import com.circulardollar.cdatm.business.downstream.model.pin.IPin;
import com.circulardollar.cdatm.business.downstream.model.pin.Pin;
import com.circulardollar.cdatm.business.upstream.model.pin.IPinRecord;
import com.circulardollar.cdatm.business.upstream.model.pin.PinRecord;

public final class PinMapper {
    private PinMapper() {
    }

    public static IPinRecord up(IPin pin) {
        return PinRecord.newBuilder().setPinNumber(pin.getPinNumber()).build();
    }

    public static IPin down(IPinRecord pin) {
        return Pin.newBuilder().setPinNumber(pin.getPinNumber()).build();
    }
}
