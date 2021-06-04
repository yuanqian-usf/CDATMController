package com.circulardollar.cdatm;

import com.circulardollar.cdatm.constant.DownstreamCommands;
import com.circulardollar.cdatm.constant.UserInterface;
import com.circulardollar.cdatm.utils.network.NetworkUtils;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.circulardollar.cdatm.utils.network.NetworkUtils.buildNetworkClientBuilder;
import static com.circulardollar.cdatm.utils.userInteraction.UserUtils.createATMController;
import static com.circulardollar.cdatm.utils.userInteraction.UserUtils.handleUserInput;

public class Main {

    /**
     * providing a simplified command-prompt-friendly access to the ATM Controller
     * run main with -url localhost would be talking to server hosted on
     * {@link NetworkUtils#LOCAL_HOST_URL}
     *
     * @param args -url localhost or potential remote host of correspondent service
     */
    public static void main(String[] args) {
        Stream.of(UserInterface.WELCOME, UserInterface.INSTRUCTION)
            .forEach(s -> System.out.println(s.getValue()));
        handleUserInput(createATMController(buildNetworkClientBuilder(args,
            Stream.of(DownstreamCommands.URL).collect(Collectors.toSet()))),
            new Scanner(System.in));

    }
}
