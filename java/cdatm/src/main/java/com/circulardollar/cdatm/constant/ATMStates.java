package com.circulardollar.cdatm.constant;

public enum ATMStates {
  UNSPECIFIED(0, 0),
  ACTIVE(1, 1),
  INSERT_CARD(2, 2),
  VERIFY_PIN(3, 3),
  SELECT_ACCOUNT(4, 4),
  CHECK_BALANCE(5, 5),
  DEPOSIT(6, 5),
  WITHDRAW(7, 5);
  private final Integer id;
  private final Integer weight;

  ATMStates(Integer id, Integer weight) {
    this.id = id;
    this.weight = weight;
  }

  /**
   * Not Null
   *
   * @param id id
   * @return if no matches {@link #UNSPECIFIED} would be returned
   */
  public static ATMStates ofId(Integer id) {
    for (ATMStates state : values()) {
      if (state.id.equals(id)) {
        return state;
      }
    }
    return UNSPECIFIED;
  }

  public Integer getId() {
    return id;
  }

  public Integer getWeight() {
    return weight;
  }
}
