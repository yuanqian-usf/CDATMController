# CircularDollar ATM Controller

This is a [Java](https://www.java.com/) application built with [Gradle](https://gradle.org/)

For the requirement of potential integration with potential bank server
A light-weighted [NodeJS](https://nodejs.org/en/) server is provisioned

For the requirement of integrating to an ATM machine please refer to the APIs:
_/java/cdatm/src/main/java/com/circulardollar/cdatm/IATMController.java_

1. _**insertCard**_ [card number]
2. _**verifyPin**_ [pin number]
3. _**selectAccount**_ [account number]
4. _**checkBalance**_
5. _**deposit**_ [amount]
6. _**withdraw**_ [amount]
7. _**ejectCard**_
	for potentially logging out the customer from real bank server.
8. _**availableStates**_
	returns a list of available operations a customer is allowed to
	perform at a specific step or state.

**Note:** last 2 APIs are not in the original requirements.

Pre Installation:
-----
[Java 1.8.x](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html#license-lightbox)

Optional Installation for mocking bank server:
[Node JS use the latest](https://nodejs.org/en/download/)

__Below are Mac OS Terminal commands help the tester
run play the controller in a light-UI and simple user-friendly approach:__
(for time constraint, non-UI scripts haven't been provided)

Build the project
-----

```$ cd {repo_root_path}/java```

```$ ./gradlew build```

View the Code Coverage
-----

```$ open cdatm/build/reports/jacoco/test/html/index.html```

_Option 1:_ Play with the mock server doesn't need NodeJS
-----
**Launch the CircularDollar ATM Controller**

```$ ./gradlew :play:runMockDB -q --console=plain --args='-url localhost'```

**Note:** Customer credentials are randomly generated and shown to the tester
for once the program is up and run for easy testing


_Option 2:_ Play with localhost server with NodeJS installed
-----
**Build and Run the Node Server**

```$ cd {repo_root_path}/node```

```$ npm i```

```$ node mock_server.js```

Use CURL command to get a list of random customer credentials info which is unchanged per mock server run-time

**Note:** Please use the CURL command in a seperate Terminal window

```$ curl --request GET 'http://localhost:8080/credentials' --header 'Content-Type: application/json'```

**Launch the CircularDollar ATM Controller**
** Note:** use a third Terminal window for below commands

_Go back to project_

```$ cd {repo_root_path}/java```

_build the project if haven't done so_

```$ ./gradlew build```

```$ ./gradlew :cdatm:runLocal -q --console=plain --args='-url localhost'```

Update
-----
Besides full commands shown in the hint, testers can enter commands like below:

```$ i 1234```

```$ v 1234```

```$ s abcd```

```$ c```

```$ d 100```

```$ w 100```

```$ e```

Summary:
-----
The design meets other non-functional requirements such as:
1. **Provision configuration capability** where an integrator is able to define valid card number/pin length (range), deposit/withdraw limits (range) for world-wide ATM machines that meet different specifications.

2. **Defensively Programmed** to validate on both customer(downstream) inputs and remote server responses(upstream) and other components for robustness.

3. **Integration (or Flow-based) Testing** along with unit tests to guard the state controlling. For example, a customer can't insert a card twice both consecutively (without a card ejection operation) or asynchronously.

4. **Separation of Concerns** 4 major components the ATM controller dependents on :validator, state controller, session controller and network client, managing isolated sets of responsibilities.

5. **Loose Coupling** separates models/contracts for upstream with versioning and downstream, in order to improve compatibility and maintainability, i.e. when bank contract changes moderately, no impact on atm machine contract

Todos:
-----
1. Consolidate needed for duplicate code in both main and test 
2. write more comments and generate a JavaDoc

Code Coverage (100%):
-----
![code-coverage](https://user-images.githubusercontent.com/54569131/121297188-c9298580-c8a6-11eb-816e-8a175fa8ffef.png)

Demo-1 (Insufficient Balance & Invalid Command with NodeJS):
-----
![demo1-node-js](https://user-images.githubusercontent.com/54569131/121300828-31c73100-c8ac-11eb-9ec8-28d642799a2a.gif)

Demo-2 (Mock DB and Server):
-----
![demo2-mock-db-server](https://user-images.githubusercontent.com/54569131/121300881-41467a00-c8ac-11eb-8110-e0e5117d4c76.gif)

_Thanks for your review. 
Please contact cugbhappymoney@gmail.com for any suggestions or bug report_