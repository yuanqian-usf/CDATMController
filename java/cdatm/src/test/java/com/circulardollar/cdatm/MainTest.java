package com.circulardollar.cdatm;

import com.circulardollar.cdatm.constant.DownstreamCommands;
import org.junit.Test;

public class MainTest {

  @Test
  public void main() {
    Main.main(new String[]{DownstreamCommands.URL.getValue(), "abc"});
  }

  @Test(expected = NullPointerException.class)
  public void createATMController() {
    Main.createATMController(null);
  }

  @Test(expected = NullPointerException.class)
  public void createATMControllerBuilder() {
    Main.createATMControllerBuilder(null);
  }
}
