const { v4: uuidv4 } = require('uuid');
var RANDOM_TEST_ITERATION = 4;
var MIN_CARD_NUMBER_LENGTH = 4;
var MAX_CARD_NUMBER_LENGTH = 16;
var MIN_PIN_LENGTH = 1;
var MAX_PIN_LENGTH = 6;
var INTEGER_MAX_VALUE = 2147483647;
var INTEGER_MIN_VALUE = -2147483648;

const generateDB = () => {
    var dictCardNoPin = {};
    var dictCardNoAcctNo = {};
    var totalCards = Math.pow(RANDOM_TEST_ITERATION, 2);
    var totalAccts = Math.pow(RANDOM_TEST_ITERATION, 3);
    var logOfTotalAccts = Math.floor(Math.log(totalAccts));
    for (var i = 0; i < totalCards; i++) {
        var login = {"cardNumber" : randomLengthRandomNumber(MIN_CARD_NUMBER_LENGTH, MAX_CARD_NUMBER_LENGTH),
            "pin": {"pinNumber": randomLengthRandomNumber(MIN_PIN_LENGTH, MAX_PIN_LENGTH)}
            };
        dictCardNoPin[login.cardNumber] = {};
        dictCardNoPin[login.cardNumber] = login.pin;
        var randomAccounts = randomNumber(logOfTotalAccts) + 1;
        var accts = [];
        for (var j = 0; j < randomAccounts; j++) {
            var randomAcctNo = uuidv4();
            accts.push({'accountNumber':randomAcctNo, 'balance':randomBalance() })
        }
        dictCardNoAcctNo[login.cardNumber] = {}
        accts.map(acct=> {
            dictCardNoAcctNo[login.cardNumber][acct.accountNumber] = acct;
        })

    }
    return [
        dictCardNoPin,
        dictCardNoAcctNo];

}
function randomNumber(end) {
    return Math.floor(Math.random() * end);
}
function randomDigit() {
    return randomNumber(10);
}

function randomNonZeroDigit() {
    return randomNumber(9) + 1;
}
function randomLengthRandomNumber(min, max) {
    return [...Array(randomNumber(max - min) + min).keys()]
        .map(index => (index == 0 ? randomNonZeroDigit() : randomDigit()).toString()).join('');
}

function fixedLengthRandomNumber(range) {
    return [...Array(range).keys()]
        .map(index => (index == 0 ? randomNonZeroDigit() : randomDigit()).toString()).join('');
}

function anyInteger() {
    return 0;
}

function randomBalance() {
    var num = randomNumber(INTEGER_MAX_VALUE) * 1.3;
    var negFlag = Math.random() < 0.5 ? -1 : 1;
    num = num * negFlag;
    if(num > INTEGER_MAX_VALUE) return INTEGER_MAX_VALUE;
    if(num < INTEGER_MIN_VALUE) return INTEGER_MIN_VALUE;
    return Math.floor(num);
}

module.exports = { generateDB, INTEGER_MAX_VALUE, INTEGER_MIN_VALUE};