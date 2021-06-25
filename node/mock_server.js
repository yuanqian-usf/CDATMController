const express = require('express')
var http = require('http');
const app = express()
const port = 8080
const lib = require('./mock_db');
const { v4: uuidv4 } = require('uuid');
var dicts = lib.generateDB();
var dictTokenCard = {};
var dictTokenTimeStamp = {};
var dictCardToken = {};
var version1 = '/v1'
var version2 = '/v2'

app.use(express.json());
app.get('/', (req, res) => {
	res.end('Hello MockBankServer');
  })

app.get('/credentials', (req, res) => {
	res.json({'credentials': dicts[0]});
  })

app.post(`${version1}/verify_pin`, (req, res) => {
	var cardNo = req.body.card.cardNumber;
	var pinNo = req.body.pin.pinNumber;
	var dictCardNoPin = JSON.parse(JSON.stringify(dicts[0]))
	if (cardNo && pinNo) {
		if (cardNo in dictCardNoPin && pinNo == dictCardNoPin[cardNo].pinNumber) {
			var latestToken = uuidv4();
			refreshToken(cardNo, latestToken);
			var accounts = Object.values(dicts[1][cardNo]);
	res.json({'accounts': accounts, 'tokenId': latestToken, 'timeStamp': dictTokenTimeStamp[latestToken]});
		} else {
			res.json({'errorCode': 404, 'errorMessages' : ['invalid card or pin']});
		}
	} else {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid login']});
	}
  })

app.post(`${version2}/verify_pin`, (req, res) => {
	var cardNo = req.body.card.cardNumber;
	var pinNo = req.body.pin.pinNumber;
	var dictCardNoPin = JSON.parse(JSON.stringify(dicts[0]))
	if (cardNo && pinNo) {
		if (cardNo in dictCardNoPin && pinNo == dictCardNoPin[cardNo].pinNumber) {
			var latestToken = uuidv4();
			refreshToken(cardNo, latestToken);
			res.json({'tokenId': latestToken});
		} else {
			res.json({'errorCode': 404, 'errorMessages' : ['invalid card or pin']});
		}
	} else {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid login']});
	}
  })

app.get(`${version2}/accounts`, (req, res) => {
	var authHeader = req.headers.authorization
	if (!authHeader) {
		return res.json({'errorCode': 404, 'errorMessages' : ['no token']});
	}
	if (!authHeader.startsWith("Bearer ")) {
		return res.json({'errorCode': 404, 'errorMessages' : ['invalid bearer token']});
	}
	var tokenId = authHeader.split(' ')[1];
	if (!(tokenId in dictTokenCard)) {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid token']});
	}
	var lastTimeStamp = dictTokenTimeStamp[tokenId];
	if(isExpiredToken(lastTimeStamp)) {
		res.json({'errorCode': 404, 'errorMessages' : ['token expired']});
	}
	var cardNo = dictTokenCard[tokenId];
	res.json({'accounts': Object.values(dicts[1][cardNo]), 'timeStamp': new Date().getTime()});
  })


app.post(`${version1}/deposit`, (req, res) => {
	var tokenId = req.body.tokenId;
	var accountNo = req.body.account.accountNumber;
	var amount = req.body.amount;
	if (!(tokenId in dictTokenCard)) {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid token']});
	}
	var lastTimeStamp = dictTokenTimeStamp[tokenId];
	if(isExpiredToken(lastTimeStamp)) {
		res.json({'errorCode': 404, 'errorMessages' : ['token expired']});
	}
	var cardNo = dictTokenCard[tokenId];
	var account = dicts[1][cardNo][accountNo];
	var existingBalance = account.balance;
	var latestBalance = existingBalance + amount;
	dicts[1][cardNo][accountNo].balance = latestBalance;
	if(tokenId && accountNo && amount) {
		if (amount <= 0) {
			res.json({'errorCode': 404, 'errorMessages' : ['invalid amount']});
		}
		if(existingBalance + amount > lib.INTEGER_MAX_VALUE) {
			res.json({'errorCode': 404, 'errorMessages' : ['max balance reached']});
		}
		res.json({'account': {'accountNumber': accountNo, 'balance': latestBalance}, 'amount': amount, 'timeStamp': new Date().getTime()})
	} else {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid parameter']});
	}	
  })

  app.post(`${version1}/withdraw`, (req, res) => {
	var tokenId = req.body.tokenId;
	var accountNo = req.body.account.accountNumber;
	var amount = req.body.amount;
	if (!(tokenId in dictTokenCard)) {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid token']});
	}
	var lastTimeStamp = dictTokenTimeStamp[tokenId];
	if(isExpiredToken(lastTimeStamp)) {
		res.json({'errorCode': 404, 'errorMessages' : ['token expired']});
	}
	var cardNo = dictTokenCard[tokenId];
	var account = dicts[1][cardNo][accountNo];
	var existingBalance = account.balance;
	var latestBalance = existingBalance - amount;
	dicts[1][cardNo][accountNo].balance = latestBalance;
	if(tokenId && accountNo && amount) {
		if (amount <= 0) {
			res.json({'errorCode': 404, 'errorMessages' : ['invalid amount']});
		}
		if(existingBalance - amount < lib.INTEGER_MIN_VALUE) {
			res.json({'errorCode': 404, 'errorMessages' : ['min balance reached']});
		}
		res.json({'account': {'accountNumber': accountNo, 'balance': latestBalance}, 'amount': amount, 'timeStamp': new Date().getTime()})
	} else {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid parameter']});
	}	
  })

  app.post(`${version1}/logout`, (req, res) => {
	var tokenId = req.body.tokenId;
	if (!(tokenId in dictTokenCard)) {
		res.json({'result': true, 'timeStamp': new Date().getTime()});
	}
	if (tokenId && dictTokenCard[tokenId]) {
		var cardNo = dictTokenCard[tokenId]
		delete dictTokenCard[tokenId];
		delete dictTokenTimeStamp[tokenId];
		delete dictCardToken[cardNo];
		res.json({'result': true, 'timeStamp': new Date().getTime()});
	} else {
		res.json({'errorCode': 404, 'errorMessages' : ['invalid token']});
	}
  })

app.listen(port, () => {
	console.log(`mock db app listening at http://localhost:${port}`)
  })

function isExpiredToken(lastLoginTimeStamp) {
	if (!lastLoginTimeStamp) return true;
	var current = new Date().getTime();
	return (current - lastLoginTimeStamp) > 5*60*1000;
}

function refreshToken(cardNo, latestToken) {
	var lastToken = dictCardToken[cardNo];
	delete dictTokenTimeStamp[lastToken];
	delete dictTokenCard[lastToken];	
	dictTokenCard[latestToken] = cardNo;
	dictCardToken[cardNo] = latestToken;
	dictTokenTimeStamp[latestToken] = new Date().getTime();
}
