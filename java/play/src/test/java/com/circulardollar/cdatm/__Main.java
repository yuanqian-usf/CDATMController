package com.circulardollar.cdatm;

import static com.circulardollar.cdatm.utils.userInteraction.UserInputUtils.handleUserInput;

import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.constant.UserInterface;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.play.network.__NetworkClientV2;
import com.circulardollar.cdatm.session.ATMSessionController;
import com.circulardollar.cdatm.state.ATMStateController;
import com.circulardollar.cdatm.utils.network.NetworkUtils;
import com.circulardollar.cdatm.validator.ATMValidator;
import com.circulardollar.cdatm.validator.upstream.ATMRemoteValidator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class __Main {
  /**
   * providing a simplified command-prompt-friendly access to the ATM Controller run main with -url
   * localhost would be talking to server hosted on {@link NetworkUtils#LOCAL_HOST_URL}
   *
   * @param args -url localhost or potential remote host of correspondent service
   */
  public static void main(String[] args) {
    Map<ILoginRecord, IAccountsRecord> dummyDB = __TestBase.dummyDB();
    INetworkClientV2.IBuilder networkClientBuilder = buildNetworkClientBuilder(dummyDB);
    Stream.of(
            UserInterface.WELCOME.getValue(),
            UserInterface.INSTRUCTION.getValue(),
            dummyDB.toString())
        .forEach(System.out::println);
    handleUserInput(createATMController(networkClientBuilder), new Scanner(System.in));
  }

  public static INetworkClientV2.IBuilder buildNetworkClientBuilder(
      Map<ILoginRecord, IAccountsRecord> dummyDB) {
    return __NetworkClientV2.newBuilder()
        .setAccountsRecordTable(dummyDB)
        .setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build());
  }

  public static IATMController createATMController(INetworkClientV2.IBuilder networkClientBuilder) {
    return createATMControllerBuilder(
            networkClientBuilder.setIATMRemoteValidator(ATMRemoteValidator.newBuilder().build()))
        .build();
  }

  public static IATMController.IBuilder createATMControllerBuilder(
      INetworkClientV2.IBuilder networkClientBuilder) {
    return CDATMController.newBuilder()
        .setAtmStateController(new ATMStateController())
        .setAtmSessionController(new ATMSessionController())
        .setNetworkClientV2(networkClientBuilder.build())
        .setAtmValidator(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build());
  }

  public static IATMController __ATMController() {
    return __Main.createATMControllerBuilder(buildNetworkClientBuilder(__TestBase.dummyDB()))
        .build();
  }
}
