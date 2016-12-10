# Bowl game services

## Description of a task
Game information (# Players, Type of Game) can be accessed via the Game service.
You do NOT have to write the interaction for that service. For now it can be mocked with the data below.
Example:
{"gameId":1,"gameType":1,"players":[{"playerId":1},{"playerId":2},{"playerId":3},{"playerId":4}]}

For now, we only have one type of game, which is ten pin bowling. The decision logic for the scoring is as follows:
_Strike_: When all ten pins are knocked down with the first ball (called a strike and typically rendered as an "X" on a scoresheet), a player is awarded ten points, plus a bonus of whatever is scored with the next two balls. In this way, the points scored for the two balls after the strike are counted twice.
_Spare_: A "spare" is awarded when no pins are left standing after the second ball of a frame; i.e., a player uses both balls of a frame to clear all ten pins. A player achieving a spare is awarded ten points, plus a bonus of whatever is scored with the next ball (only the first ball is counted). It is typically rendered as a slash on scoresheets in place of the second pin count for a frame.
_Others_: Amount of pins knocked down. 0 if non were hit

Data transfer should be done under JSON format

##Components
The Setup consists of 

* a main bowl service which performs main actions of a bowl game
* a game service (now just like a `black box` External API)
* a scoring service, calculates gaming score
* a discovery services registry (eureka)

## Requirements
* Docker
* Java8

## Used tools list+short description

**Actuator**
Allows to see what happens to each service via http, including full monitoring.

**Eureca**
Services discovery registry for, each service registers by it, so other services may interact each other by name.
server.port=8761

**Ribbon**
Client side load balancer, in pair w/ Eurica

**Hystrix**
Circuit breaker service to have a backup/fallback logic for getting default data if service is down (failover).

## Possible improvements
**Config server**
Allows to dynamically change the config w/o restarting connected services (properties are stored locally/changed by git)

**Hystrix service**
Add Hystrix dashboard to see the status of all services

**Data storing**
_Game service_ store data not in memory, but on a real db, say H2
_Scoring service_ impl at least serialization/deserialization of a throws data

## Running info
* cd to the project root

* Test all projects `./gradlew test`, build them `./gradlew build`, and create docker images for each `./gradlew buildDocker`,
OR we can simply run short notation for gradle task to do all of that in one scope
```
./gradlew bDoc
```

* Get sure that `BUILD SUCCESSFUL`

* If it's not - we should fix error(s) which will be shown in `stdout` 

* Check that docker images were created, should be smth like that
```
scoring-service              latest              16849e83f73b        16 seconds ago      270.6 MB
game-service                 latest              475422ab2f93        25 seconds ago      269 MB
discovery-service            latest              aab73f1707ca        36 seconds ago      263.1 MB
bowl-service                 latest              f31f95f26036        47 seconds ago      246 MB
frolvlad/alpine-oraclejdk8   slim                a290f8607aef        6 weeks ago         167.2 MB
```

* Launch all services with docker in background except _bowl-service_
```
docker-compose up -d
```

* Check in a while that services started and 2 of them connected to discovery registry service with [http://localhost:8761/](http://localhost:8761/)
```
Instances currently registered with Eureka

Application	    AMIs	Availability Zones	Status
GAME-SERVICE	n/a     (1)	         (1)	UP (1) - game-service:game-service:8082
SCORING-SERVICE	n/a     (1)	         (1)	UP (1) - scoring-service:scoring-service:8081
```
* For getting more info we can use
```
curl http://localhost:8081/actuator
curl http://localhost:8082/actuator
curl http://localhost:8083/actuator
```

* Launch _bowl-service_ separately with interactive mode support using existing script
Initially check the network created by `docker-compose` and patch `bowlServiceDockerRun.sh` if needed

```
docker network ls
chmod +x ./bowlServiceDockerRun.sh
chmod +x ./wait-for-it.sh
./bowlServiceDockerRun.sh
```

As you can see, here we use script which allow us to check services are up
check it here [vishnubob/wait-for-it](https://github.com/vishnubob/wait-for-it) for details


* After _bowl-service_ starts, it will register itself on _discovery-service_,
and will ask for some inputs from you,
please remember that checking user input was not validated in this version ;)

## Localhost running

If you like to start services from localhost instead of docker
please add additionally  `-Dspring.profiles.active=local` to java params
for all services except _discovey-service_

## In addition

As far all services communicate each other with requests, you can also test requests/responses manually.
The easiest way is to see all available abilities through project/java/controller/<JavaClass> it will describe all available methods

For example:

* _bowl-service_
get info about winner by gameId
```
curl localhost:8083/bowl/winner/678
```
probably such gameId was not created yet, so circuit breaker (Hystrix) will return fallback data
and case of an exception will be rreported inside ap (so dev team will get know about that).
But user will get a _valid response_
Try it with real gameId and will get such one response
```
curl localhost:8083/bowl/winner/9
{"playerId":8,"name":"Irina","gameId":9,"throwz":[3,5,8,0,5,4,3,6,1,2,6,1,4,4,7,1,6,2,6,2]}}
```
to see throws of a specific player we can use gameId and playerId. Here we also use `CircuitBreaker`
```
curl localhost:8083/bowl/throws/3/4
{"playerId":4,"name":"Thomas","gameId":3,"throwz":[0,8,5,0,5,3,0,4,9,0,6,0,2,3,5,1,2,6,7,0]}
```
To get similar info we can access directly to _scoring-service_ as well
```
curl localhost:8081/throws/3/4
[0,8,5,0,5,3,0,4,9,0,6,0,2,3,5,1,2,6,7,0]
```

* _game-service_

creates a new game and gives info about it
```
curl -X PUT localhost:8082/game
curl -X GET localhost:8082/game/1
```
responses will be like
```
{"gameId":1,"type":10,"players":[]}

```
Also, since this service has in-memory db, you can access/manipulate the data by
investigating 
```
curl http://localhost:8082/profile
curl http://localhost:8082/profile/games
curl http://localhost:8082/profile/players
```
and it's links. With PUT you will create records, GET will get data,
DELETE deletes anything you want etc... This is are common JPA's CRUD abilities.

* etc