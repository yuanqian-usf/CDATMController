package com.circulardollar.cdatm.constant;

import org.junit.Test;

import static org.junit.Assert.*;

public class DownstreamCommandsTest {

    @Test public void ofValue() {
        assertEquals(DownstreamCommands.ofValue("Hello"), DownstreamCommands.UNSPECIFIED);
        assertEquals(DownstreamCommands.ofValue("-url"), DownstreamCommands.URL);
    }

    @Test public void getValue() {
        assertEquals(DownstreamCommands.UNSPECIFIED.getValue(), DownstreamCommands.ofValue("Hello").getValue());
        assertEquals(DownstreamCommands.URL.getValue(), DownstreamCommands.ofValue("-url").getValue());
    }
}
