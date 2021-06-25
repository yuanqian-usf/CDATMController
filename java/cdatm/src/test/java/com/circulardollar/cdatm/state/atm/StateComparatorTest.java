package com.circulardollar.cdatm.state.atm;

import java.util.Comparator;

import com.circulardollar.cdatm.constant.ATMStates;
import com.circulardollar.cdatm.state.StateComparator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StateComparatorTest {
  Comparator<Integer> comparator;
  @Before
  public void setup() {
    comparator = new StateComparator();
  }

  @Test
  public void testStateComparator_Active_InsertCard() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.ACTIVE.getWeight(), ATMStates.INSERT_CARD.getWeight()));
  }

  @Test
  public void testStateComparator_InsertCard_VerifyPin() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.INSERT_CARD.getWeight(), ATMStates.VERIFY_PIN.getWeight()));
  }

  @Test
  public void testStateComparator_VerifyPin_SelectAccount() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.VERIFY_PIN.getWeight(),
 ATMStates.SELECT_ACCOUNT.getWeight()));
  }

  @Test
  public void testStateComparator_SelectAccount_CheckBalance() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.SELECT_ACCOUNT.getWeight(),
 ATMStates.CHECK_BALANCE.getWeight()));
  }

  @Test
  public void testStateComparator_SelectAccount_Deposit() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.SELECT_ACCOUNT.getWeight(), ATMStates.DEPOSIT.getWeight()));
  }

  @Test
  public void testStateComparator_SelectAccount_Withdraw() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.SELECT_ACCOUNT.getWeight(), ATMStates.WITHDRAW.getWeight()));
  }

  @Test
  public void testStateComparator_CheckBalance_Deposit() {
    Assert.assertEquals(0,
        comparator.compare(ATMStates.CHECK_BALANCE.getWeight(), ATMStates.DEPOSIT.getWeight()));
  }

  @Test
  public void testStateComparator_CheckBalance_Withdraw() {
    Assert.assertEquals(0,
        comparator.compare(ATMStates.CHECK_BALANCE.getWeight(), ATMStates.WITHDRAW.getWeight()));
  }

  @Test
  public void testStateComparator_Deposit_Withdraw() {
    Assert.assertEquals(0,
        comparator.compare(ATMStates.DEPOSIT.getWeight(), ATMStates.WITHDRAW.getWeight()));
  }

  @Test
  public void testStateComparator_InsertCard_Active() {
    Assert.assertEquals(4,
        comparator.compare(ATMStates.INSERT_CARD.getWeight(), ATMStates.ACTIVE.getWeight()));
  }

  @Test
  public void testStateComparator_VerifyPin_Active() {
    Assert.assertEquals(3,
        comparator.compare(ATMStates.VERIFY_PIN.getWeight(), ATMStates.ACTIVE.getWeight()));
  }

  @Test
  public void testStateComparator_SelectAccount_Active() {
    Assert.assertEquals(2,
        comparator.compare(ATMStates.SELECT_ACCOUNT.getWeight(), ATMStates.ACTIVE.getWeight()));
  }

  @Test
  public void testStateComparator_CheckBalance_Active() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.CHECK_BALANCE.getWeight(), ATMStates.ACTIVE.getWeight()));
  }

  @Test
  public void testStateComparator_Deposit_Active() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.DEPOSIT.getWeight(), ATMStates.ACTIVE.getWeight()));
  }

  @Test
  public void testStateComparator_Withdraw_Active() {
    Assert.assertEquals(1,
        comparator.compare(ATMStates.WITHDRAW.getWeight(), ATMStates.ACTIVE.getWeight()));
  }
}
