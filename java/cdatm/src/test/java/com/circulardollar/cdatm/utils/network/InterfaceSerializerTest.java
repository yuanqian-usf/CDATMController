package com.circulardollar.cdatm.utils.network;

import static com.circulardollar.cdatm.TestBase.randomInt;
import static com.circulardollar.cdatm.TestBase.randomString;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.circulardollar.cdatm.business.upstream.model.account.AccountRecord;
import com.google.gson.JsonSerializationContext;
import org.junit.Test;

public class InterfaceSerializerTest {

  @Test
  public void interfaceSerializer() {
    assertNotNull(InterfaceSerializer.interfaceSerializer(AccountRecord.class));
  }

  @Test
  public void serialize_not_null() {
    AccountRecord accountRecord = AccountRecord.newBuilder()
        .setAccountNumber(randomString())
        .setBalance(randomInt())
        .build();
    JsonSerializationContext context = mock(JsonSerializationContext.class);
    InterfaceSerializer.interfaceSerializer(AccountRecord.class)
        .serialize(accountRecord,
            AccountRecord.class,
            context);
    verify(context, times(1)).serialize(eq(accountRecord), any());
  }

  @Test
  public void serialize_null() {
    AccountRecord accountRecord = null;
    JsonSerializationContext context = mock(JsonSerializationContext.class);
    InterfaceSerializer.interfaceSerializer(AccountRecord.class)
        .serialize(accountRecord, AccountRecord.class, context);
    verify(context, times(1)).serialize(eq(accountRecord), any());
  }

  @Test
  public void deserialize() {

  }
}
