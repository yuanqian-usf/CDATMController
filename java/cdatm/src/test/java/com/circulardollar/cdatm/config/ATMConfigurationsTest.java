package com.circulardollar.cdatm.config;

import com.circulardollar.cdatm.constant.APIVersions;
import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static org.junit.Assert.*;

public class ATMConfigurationsTest {

    public void newBuilder() {
        IATMConfigurations iatmConfigurations = ATMConfigurations.newBuilder().build();
        assertNotNull(iatmConfigurations);
        assertEquals(APIVersions.V2, iatmConfigurations.getAPIVersion());
        assertEquals(IATMConfigurations.MIN_CARD_NUMBER_LENGTH, iatmConfigurations.getMaxCardNumberLength());
        assertEquals(IATMConfigurations.MAX_CARD_NUMBER_LENGTH, iatmConfigurations.getMinCardNumberLength());
        assertEquals(IATMConfigurations.MIN_PIN_LENGTH, iatmConfigurations.getMaxPinLength());
        assertEquals(IATMConfigurations.MAX_PIN_LENGTH, iatmConfigurations.getMinPinLength());
        assertEquals(IATMConfigurations.MIN_DEPOSIT_AMOUNT, iatmConfigurations.getMaxDepositAmount());
        assertEquals(IATMConfigurations.MAX_DEPOSIT_AMOUNT, iatmConfigurations.getMinDepositAmount());
        assertEquals(IATMConfigurations.MIN_WITHDRAW_AMOUNT, iatmConfigurations.getMaxWithdrawAmount());
        assertEquals(IATMConfigurations.MAX_WITHDRAW_AMOUNT, iatmConfigurations.getMinWithdrawAmount());
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_01() {
        APIVersions versions = APIVersions.V2;
        assertEquals(versions, ATMConfigurations.newBuilder().setAPIVersion(versions).build().getAPIVersion());
        ATMConfigurations.newBuilder().setAPIVersion(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_02() {
        Integer max = 10;
        assertEquals(max, ATMConfigurations.newBuilder().setMaxCardNumberLength(max).build().getMaxCardNumberLength());
        ATMConfigurations.newBuilder().setMaxCardNumberLength(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_03() {
        Integer min = 1;
        assertEquals(min, ATMConfigurations.newBuilder().setMinCardNumberLength(min).build().getMinCardNumberLength());
        ATMConfigurations.newBuilder().setMinCardNumberLength(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_04() {
        Integer max = 10;
        assertEquals(max, ATMConfigurations.newBuilder().setMaxPinLength(max).build().getMaxPinLength());
        ATMConfigurations.newBuilder().setMaxPinLength(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_05() {
        Integer min = 1;
        assertEquals(min, ATMConfigurations.newBuilder().setMinPinLength(min).build().getMinPinLength());
        ATMConfigurations.newBuilder().setMinPinLength(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_06() {
        Integer max = 10;
        assertEquals(max, ATMConfigurations.newBuilder().setMaxDepositAmount(max).build().getMaxDepositAmount());
        ATMConfigurations.newBuilder().setMaxDepositAmount(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_07() {
        Integer min = 1;
        assertEquals(min, ATMConfigurations.newBuilder().setMinDepositAmount(min).build().getMinDepositAmount());
        ATMConfigurations.newBuilder().setMinDepositAmount(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_08() {
        Integer max = 10;
        assertEquals(max, ATMConfigurations.newBuilder().setMaxWithdrawAmount(max).build().getMaxWithdrawAmount());
        ATMConfigurations.newBuilder().setMaxWithdrawAmount(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void ATMConfigurations_not_null_whenExceptionThrown_thenExpectationSatisfied_09() {
        Integer min = 1;
        assertEquals(min, ATMConfigurations.newBuilder().setMinWithdrawAmount(min).build().getMinWithdrawAmount());
        ATMConfigurations.newBuilder().setMinWithdrawAmount(null).build();
    }

    @Test public void getMinCardNumberLength() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(min, new ATMConfigurations(min, max, randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), APIVersions.UNSPECIFIED).getMinCardNumberLength());
    }

    @Test public void getMaxCardNumberLength() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(max, new ATMConfigurations(min, max, randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), APIVersions.UNSPECIFIED).getMaxCardNumberLength());
    }

    @Test public void getMinPinLength() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(min, new ATMConfigurations(randomInt(), randomInt(), min, max, randomInt(), randomInt(), randomInt(), randomInt(), APIVersions.UNSPECIFIED).getMinPinLength());
    }

    @Test public void getMaxPinLength() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(max, new ATMConfigurations(randomInt(), randomInt(), min, max, randomInt(), randomInt(), randomInt(), randomInt(), APIVersions.UNSPECIFIED).getMaxPinLength());
    }

    @Test public void getMinDepositAmount() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(min, new ATMConfigurations(randomInt(), randomInt(), randomInt(), randomInt(), min, max, randomInt(), randomInt(), APIVersions.UNSPECIFIED).getMinDepositAmount());
    }

    @Test public void getMaxDepositAmount() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(max, new ATMConfigurations(randomInt(), randomInt(), randomInt(), randomInt(), min, max, randomInt(), randomInt(), APIVersions.UNSPECIFIED).getMaxDepositAmount());
    }

    @Test public void getMinWithdrawAmount() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(min, new ATMConfigurations(randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), min, max, APIVersions.UNSPECIFIED).getMinWithdrawAmount());
    }

    @Test public void getMaxWithdrawAmount() {
        Integer min = 1;
        Integer max = 10;
        assertEquals(max, new ATMConfigurations(randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), min, max, APIVersions.UNSPECIFIED).getMaxWithdrawAmount());
    }

    @Test public void getAPIVersion() {
        APIVersions versions = APIVersions.V2;
        assertEquals(versions, new ATMConfigurations(randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), randomInt(), versions).getAPIVersion());
    }
}
