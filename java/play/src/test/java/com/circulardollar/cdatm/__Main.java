package com.circulardollar.cdatm;

import com.circulardollar.cdatm.business.upstream.model.accounts.IAccountsRecord;
import com.circulardollar.cdatm.business.upstream.model.auth.ILoginRecord;
import com.circulardollar.cdatm.config.ATMConfigurations;
import com.circulardollar.cdatm.constant.UserInterface;
import com.circulardollar.cdatm.network.INetworkClientV2;
import com.circulardollar.cdatm.network.NonCSNetworkClientV2;
import com.circulardollar.cdatm.session.ATMSessionController;
import com.circulardollar.cdatm.state.ATMStateController;
import com.circulardollar.cdatm.utils.network.NetworkUtils;
import com.circulardollar.cdatm.validator.ATMValidator;

import java.util.Map;
import java.util.Scanner;

import static com.circulardollar.cdatm.utils.userInteraction.UserUtils.handleUserInput;



public class __Main {
    /**
     * providing a simplified command-prompt-friendly access to the ATM Controller
     * run main with -url localhost would be talking to server hosted on
     * {@link NetworkUtils#LOCAL_HOST_URL}
     *
     * @param args -url localhost or potential remote host of correspondent service
     */
    public static void main(String[] args) {
        System.out.println(UserInterface.WELCOME.getValue());
        System.out.println(UserInterface.INSTRUCTION.getValue());
        Map<ILoginRecord, IAccountsRecord> dummyDB = __TestBase.dummyDB();
        INetworkClientV2.IBuilder networkClientBuilder =
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(dummyDB);
        System.out.println(dummyDB);
        handleUserInput(createATMController(networkClientBuilder), new Scanner(System.in));
    }

    public static IATMController createATMController(
        INetworkClientV2.IBuilder networkClientBuilder) {
        return createATMControllerBuilder(networkClientBuilder).build();
    }

    public static IATMController.IBuilder createATMControllerBuilder(
        INetworkClientV2.IBuilder networkClientBuilder) {
        return CDATMController.newBuilder().setAtmStateController(new ATMStateController())
            .setAtmSessionController(new ATMSessionController())
            .setNetworkClientV2(networkClientBuilder.build()).setAtmValidator(
                ATMValidator.newBuilder(ATMConfigurations.newBuilder().build()).build());
    }

    public static IATMController nonCSATMController() {
        return __Main.createATMControllerBuilder(
            NonCSNetworkClientV2.newBuilder().setAccountsRecordTable(__TestBase.dummyDB())).build();
    }
}
