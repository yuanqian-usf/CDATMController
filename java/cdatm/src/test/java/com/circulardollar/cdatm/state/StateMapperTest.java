package com.circulardollar.cdatm.state;

import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.constant.DownstreamAPIs;
import org.junit.Test;

import static com.circulardollar.cdatm.constant.ATMStates.*;
import static com.circulardollar.cdatm.constant.DownstreamAPIs.EJECT_CARD;
import static org.junit.Assert.*;

public class StateMapperTest {

    @Test public void map() {
        assertEquals(StateMapper.map(ACTIVE), DownstreamAPIs.EJECT_CARD);
        assertEquals(StateMapper.map(INSERT_CARD), DownstreamAPIs.INSERT_CARD);
        assertEquals(StateMapper.map(VERIFY_PIN), DownstreamAPIs.VERIFY_PIN);
        assertEquals(StateMapper.map(SELECT_ACCOUNT), DownstreamAPIs.SELECT_ACCOUNT);
        assertEquals(StateMapper.map(CHECK_BALANCE), DownstreamAPIs.CHECK_BALANCE);
        assertEquals(StateMapper.map(DEPOSIT), DownstreamAPIs.DEPOSIT);
        assertEquals(StateMapper.map(WITHDRAW), DownstreamAPIs.WITHDRAW);
        assertEquals(StateMapper.map(UNSPECIFIED), DownstreamAPIs.UNSPECIFIED);
        assertEquals(StateMapper.map(null), DownstreamAPIs.UNSPECIFIED);

    }
}
