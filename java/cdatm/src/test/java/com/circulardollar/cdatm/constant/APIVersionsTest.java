package com.circulardollar.cdatm.constant;

import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class APIVersionsTest {

    @Test public void getVersion() {
        assertEquals(0, APIVersions.UNSPECIFIED.getVersion().intValue());
        assertEquals(1, APIVersions.V1.getVersion().intValue());
        assertEquals(2, APIVersions.V2.getVersion().intValue());
    }

    @Test public void ofVersion() {
        assertNotNull(APIVersions.ofVersion(randomInt()));
        assertEquals(APIVersions.UNSPECIFIED, APIVersions.ofVersion(-1));
        assertEquals(APIVersions.UNSPECIFIED, APIVersions.ofVersion(null));
        assertEquals(APIVersions.UNSPECIFIED, APIVersions.ofVersion(0));
        assertEquals(APIVersions.V1, APIVersions.ofVersion(1));
        assertEquals(APIVersions.V2, APIVersions.ofVersion(2));
    }
}
