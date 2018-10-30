# web3scala-ethereum-integration
In this project has implemented an api rest that serves the user to
communicate with a private network Ethreum using the library web3scala.
## 0. What do you need?
- Git
- IDE - IntelliJ 18.x (https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&code=IIC)
- sbt 1.1.1 or later (http://www.scala-sbt.org/download.html)
- Scala 2.12.4 or later (https://downloads.lightbend.com/scala/2.12.4/scala-2.12.4.msi)
- Geth (https://geth.ethereum.org/downloads/)
- Postman (https://www.getpostman.com/)
## 0.1 How to
1. Install geth (https://geth.ethereum.org/downloads/)
2. Install postman (https://www.getpostman.com/)
3. Clone project : git clone https://github.com/JuanManuelGN/Resthereum.git
4. Open a command line console in the project folder and launch the command: sbt compile

## 1. Getting started
In this introducction file has present how install and raise ethereum
client. And how can we play with the application using web3scala library.
### 1.1 Ethereum client
The ethreum client that we used is geth. Geth is the the command line
interface for running a full ethereum node implemented in Go.
You can more information about geth at url:
https://github.com/ethereum/go-ethereum/wiki/geth
#### 1.1.1 Geth, How to install
##### 1.1.1.1 Windows
Download geth for windows from https://geth.ethereum.org/downloads/ and
install.
#### 1.1.2 Start Ethereum network
To boot the network we have to do the following steps:
1. Create a folder on your computer(eth)
2. Create a subfolder where we will save the ethereum files(node)
3. Open a command console prompt from the first folder(eth).
4. create an account to be used as the node's primary account
```
geth account new --datadir node
```
Type the password you want or none by typing enter. Copy the account of
the console for the follow step.

5. Write genesis.json and save it in the folder(eth). The account copied
   in the previous step can be added to the json so geth can configure
   it with an initial amount of ether.
View in the folder docs the file: [genesis.json](docs/genesis-lambda.json/)
An example is shown below:
```
{
    "coinbase": "0x0000000000000000000000000000000000000001",
    "difficulty": "0x20",
    "extraData": "",
    "gasLimit": "0x8000000",
    "nonce": "0x0000000000000042",
    "mixhash": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "parentHash": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "timestamp": "0x00",
    "alloc": {
        "0xc218b26f88d006a34a6f2562787cb9647e0fb806": { "balance" : "5000" }
    },
    "config": {
        "chainId": 1553,
        "homesteadBlock": 0,
        "eip155Block": 0,
        "eip158Block": 0
    }
}
```

7. To Initialize the Ethereum network execute the following command
from the folder where the genesis file is located(eth):
```
geth --datadir <folderName> init genesis.json
```
in our example folderName is equal node

#### 1.1.3 Launch Ethereum client (Geth)
1. To run blockchain private network execute the following command from
the folder where the genesis file is located(eth):
```
geth --datadir <folderName> --rpc --networkid <networkid> --mine --unlock "<account>"
```
* Commands
     --mine: Enable miner
     --rpc: Enable remote process call

Where <account> is the account written in the genesis.json file

2. Open Another command prompt and execute the following line:
```
geth attach \\.\pipe\geth.ipc
```
In this command console you can interative with the blockchain network
using javascript commands.
For example, you can **create a new account** with this command:
```javascript
personal.newAccount(<"password">)
```
The password can be left empty.
To **miner**.
```
eth.miner(<number of cores>)
```
You can also get a **list of accounts**:
```javascript
eth.accounts
```
You can get account balance
```
eth.getBalance(<account>)
```
It is posible to send ether from one account to another
```
personal.sendTransaction({from:"<sourceAccount>",to:"<DestinationAccount>",value:"<wei amonut>"},"<password>")
```
You can get more information about javascript console in
https://ethereum.gitbooks.io/frontier-guide/content/index.html
### 1.2 web3scala-ethereum-integration
#### 1.2.1 Config web3Scala-ethereum-integration
When we launch geth we will view something like this:
```
INFO [09-19|18:23:35.729] Maximum peer count                       ETH=25 LES=0 total=25
INFO [09-19|18:23:35.753] Starting peer-to-peer node               instance=Geth/v1.8.15-stable-89451f7c/windows-amd64/go1.10.3
INFO [09-19|18:23:35.764] Allocated cache and file handles         database=C:\\Users\\j.garcia.navarro\\ethereum\\redesPrivadas\\PoC03\\geth\\chaindata cache=768 handles=1024
INFO [09-19|18:23:35.820] Initialised chain configuration          config="{ChainID: 1534 Homestead: 0 DAO: <nil> DAOSupport: false EIP150: <nil> EIP155: 0 EIP158: 0 Byzantium: <nil> Constantinople: <nil> Engine: unknown}"
INFO [09-19|18:23:35.836] Disk storage enabled for ethash caches   dir=C:\\Users\\j.garcia.navarro\\ethereum\\redesPrivadas\\PoC03\\geth\\ethash count=3
INFO [09-19|18:23:35.854] Disk storage enabled for ethash DAGs     dir=C:\\Users\\j.garcia.navarro\\AppData\\Ethash                              count=2
INFO [09-19|18:23:35.868] Initialising Ethereum protocol           versions="[63 62]" network=1534
WARN [09-19|18:23:35.881] Head state missing, repairing chain      number=1353 hash=5d7bb3…a562e5
INFO [09-19|18:23:35.919] Rewound blockchain to past state         number=835  hash=5c086f…995cb4
INFO [09-19|18:23:35.923] Loaded most recent local header          number=1353 hash=5d7bb3…a562e5 td=226662327
INFO [09-19|18:23:35.930] Loaded most recent local full block      number=835  hash=5c086f…995cb4 td=132987304
INFO [09-19|18:23:35.937] Loaded most recent local fast block      number=1353 hash=5d7bb3…a562e5 td=226662327
INFO [09-19|18:23:35.946] Setting new local account                address=0xe332dA24634D9704c0c52CDEb6B39EBA80ee4De7
INFO [09-19|18:23:35.965] Loaded local transaction journal         transactions=2 dropped=0
INFO [09-19|18:23:36.015] Regenerated local transaction journal    transactions=2 accounts=1
WARN [09-19|18:23:36.023] Blockchain not empty, fast sync disabled
INFO [09-19|18:23:36.038] Starting P2P networking
INFO [09-19|18:23:38.258] UDP listener up                          self=enode://b764eb7fbaf4a73caa088fc048b96351d001744a4ce6cd2681322540db6875dc0ac0fafbafeb4431ae8c08814609f1a46493ec082ebc01d44b22c64bd6390ea4@[::]:30303
INFO [09-19|18:23:38.271] RLPx listener up                         self=enode://b764eb7fbaf4a73caa088fc048b96351d001744a4ce6cd2681322540db6875dc0ac0fafbafeb4431ae8c08814609f1a46493ec082ebc01d44b22c64bd6390ea4@[::]:30303
INFO [09-19|18:23:38.276] IPC endpoint opened                      url=\\\\.\\pipe\\geth.ipc
INFO [09-19|18:23:38.302] HTTP endpoint opened                     url=http://127.0.0.1:8545 cors= vhosts=localhost
INFO [09-19|18:23:38.314] Transaction pool price threshold updated price=1000000000
INFO [09-19|18:23:38.321] Updated mining threads                   threads=0
INFO [09-19|18:23:38.327] Transaction pool price threshold updated price=1000000000
INFO [09-19|18:23:38.332] Etherbase automatically configured       address=0xe332dA24634D9704c0c52CDEb6B39EBA80ee4De7
INFO [09-19|18:23:38.345] Commit new mining work                   number=836  sealhash=c8369d…91b6ef uncles=0 txs=0 gas=0 fees=0 elapsed=0s
INFO [09-19|18:23:38.360] Commit new mining work                   number=836  sealhash=22248d…ce84a2 uncles=0 txs=2 gas=153573 fees=0.000174573 elapsed=15.031ms
```

The following line is important, because we get the ip and the port on
which our blockchain network is listening
url=http://127.0.0.1:8545

#### 1.2.2 Launch web3scala-ethereum-integration
In this project the implementation of an api rest to interact with the
ethereum geth client through the web3Scala library has been carried out.
#### 1.2.3 Run the application
To launch the application you have to run the __Resource.class__ The api
has been implemented with akka http.
The documentation of api rest is in the file __web3scala_rest-client-examples.txt__
From the project root folder execute the following command:
```
sbt run
```
if everything went well you should see the message :
```
Server started at 9010
```
#### 1.2.4 We launch a request!!
With postman or other client we launch the request (Get):
```json
http://localhost:9010/api/accounts
```
And we get a response similar to this one:
```
{
    "accounts": [
        "0x5ee2f5012fdf6f5f73508de833c2325bf8326c15",
        "0x6ac147e6a2a706fe4ed43cba00ca2564524737b9"
    ]
}
```
