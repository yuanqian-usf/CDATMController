package com.circulardollar.cdatm.session;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import java.util.Map;

public class ATMSessionController implements IATMSessionController {
  private String tokenId;
  private ICard card;
  private Map<String, IAccount> accountsMap;
  private String selectedAccountNumber;

  @Override
  public void setCard(ICard card) {
    this.card = card;
  }

  @Override
  public void setAccounts(String tokenId, Map<String, IAccount> accountsMap) {
    this.tokenId = tokenId;
    this.accountsMap = accountsMap;
  }

  @Override
  public void updateAccount(IAccount account) {
    accountsMap.put(account.getAccountNumber(), account);
  }

  @Override
  public void setSelectedAccountNumber(String selectedAccountNumber) {
    this.selectedAccountNumber = selectedAccountNumber;
  }

  @Override
  public String getTokenId() {
    return tokenId;
  }

  @Override
  public ICard getCard() {
    return card;
  }

  @Override
  public Map<String, IAccount> getAccounts() {
    return accountsMap;
  }

  @Override
  public IAccount getSelectedAccount() {
    if (accountsMap == null || selectedAccountNumber == null ||
        !accountsMap.containsKey(selectedAccountNumber)) {
      return null;
    }
    return accountsMap.get(selectedAccountNumber);
  }

  @Override
  public void clear() {
    this.tokenId = null;
    this.card = null;
    this.accountsMap = null;
    this.selectedAccountNumber = null;
  }
}
