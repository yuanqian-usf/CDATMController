package com.circulardollar.cdatm.session;

import com.circulardollar.cdatm.business.downstream.model.account.IAccount;
import com.circulardollar.cdatm.business.downstream.model.card.ICard;
import java.util.Map;

public interface IATMSessionController {
  String getTokenId();

  Map<String, IAccount> getAccounts();

  IAccount getSelectedAccount();

  ICard getCard();

  void setCard(ICard card);

  void setAccounts(String tokenId, Map<String, IAccount> accountsMap);

  void updateAccount(IAccount account);

  void setSelectedAccountNumber(String selectedAccountNumber);

  void clear();
}
