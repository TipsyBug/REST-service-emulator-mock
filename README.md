# REST service emulator/mock

Spring with Java Project @PerformanceLab

A training task for creating a Java-based Spring MVC application to emulate/mock REST services as part of a performance testing course.

The mock emulator is designed according to the MVC pattern.

 
- At the input, we receive a JSON request containing a client ID ```clientId```, account number ```account```, and additional fields ```rqUID, openDate, closeDate```.
- In response, it generates a JSON with information about the account currency ```currency```, balance ```balance```, and maximum limit ```maxLimit```.


- The balance is a random number less than the maximum limit.
- If the client ID starts with ```8```, the currency is ```US``` with a maximum limit of ```2000.00```.
- If it starts with ```9```, the currency is ```EU``` with a maximum limit of ```1000.00```.
- For any other starting digit, the currency is ```RUB``` with a maximum limit of ```10000.00```.

## Prerequisites

This program is built using Java 17 for compilation and execution, with Maven configured in the pom.xml.

## Example of use
We use Postman or cURL to send a POST request to the URL endpoint, for example http://localhost:8080/info/postBalances.

In the request body, we send raw JSON data:
```
{
	"rqUID": "58dgtf565j8547f64ke7",
	"clientId": "8050000000000000000",
	"account": "30500000000000000001",
	"openDate": "2020-01-01",
	"closeDate": "2025-01-01"
	}
```

Here is the response we receive:

```
{
	"rqUID": "79dgtf565j8158f64gt4",
	"clientId": "8050000000000000000",
	"account": "80500000000000000001",
	"currency": "US",
	"balance": "1400.00",
	"maxLimit": "2000.00"
	}
```
