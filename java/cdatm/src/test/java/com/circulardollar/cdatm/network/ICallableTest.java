package com.circulardollar.cdatm.network;

import org.junit.Test;

import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.*;

public class ICallableTest {

    @Test public void call() {

        ICallable<String, String> callable = new ICallable<String, String>(null) {
            @Override public String call() throws Exception {
                return super.call();
            }

            @Override public String getInput() {
                return super.getInput();
            }
        };
        try {
            assertNull(callable.call());
            assertNull(callable.getInput());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test public void getInput() {

        String randomInput = randomString();
        ICallable<String, String> callable = new ICallable<String, String>(randomInput) {
            @Override public String call() throws Exception {
                return super.call();
            }

            @Override public String getInput() {
                return randomInput;
            }
        };
        try {
            assertNotNull(callable.getInput());
            assertEquals(randomInput, callable.getInput());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
