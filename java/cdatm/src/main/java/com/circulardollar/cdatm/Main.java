package com.circulardollar.cdatm;

import static com.circulardollar.cdatm.utils.network.NetworkUtils.buildNetworkClientBuilder;
import static com.circulardollar.cdatm.utils.userInteraction.UserInputUtils.handleUserInput;

import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.constant.DownstreamCommands;
import com.circulardollar.cdatm.constant.UserInterface;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.session.ATMSessionController;
import com.circulardollar.cdatm.state.ATMStateController;
import com.circulardollar.cdatm.utils.network.NetworkUtils;
import com.circulardollar.cdatm.validator.ATMValidator;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Main {

  private Main() {}
  /**
   * providing a simplified command-prompt-friendly access to the ATM Controller run main with -url
   * localhost would be talking to server hosted on {@link NetworkUtils#LOCAL_HOST_URL}
   *
   * @param args {@link DownstreamCommands#URL} localhost or remote host
   *
   */
  public static void main(String[] args) {
    Stream.of(UserInterface.WELCOME, UserInterface.INSTRUCTION)
        .forEach(s -> System.out.println(s.getValue()));
    handleUserInput(
        createATMController(
            buildNetworkClientBuilder(
                args, Stream.of(DownstreamCommands.URL).collect(Collectors.toSet()))),
        new Scanner(System.in));
  }

  public static IATMController createATMController(INetworkClientV2.IBuilder networkClientBuilder) {
    Objects.requireNonNull(networkClientBuilder);
    return createATMControllerBuilder(networkClientBuilder).build();
  }

  public static IATMController.IBuilder createATMControllerBuilder(
      INetworkClientV2.IBuilder networkClientBuilder) {
    Objects.requireNonNull(networkClientBuilder);
    return CDATMController.newBuilder()
        .setAtmStateController(new ATMStateController())
        .setAtmSessionController(new ATMSessionController())
        .setNetworkClientV2(networkClientBuilder.build())
        .setAtmValidator(ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build());
  }
}
