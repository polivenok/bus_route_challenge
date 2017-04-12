# Bus Route Challenge

### Problem

We are adding a new bus provider to our system. In order to implement a very
specific requirement of this bus provider our system needs to be able to filter
direct connections. We have access to a weekly updated list of bus routes
in form of a **bus route data file**. As this provider has a lot of long bus
routes, we need to come up with a proper service to quickly answer if two given
stations are connected by a bus route.


### Task

The bus route data file provided by the bus provider contains a list of bus
routes. These routes consist of an unique identifier and a list of stations
(also just unique identifiers). A bus route **connects** its list of stations.

Your task is to implement a micro service which is able to answer whether there
is a bus route providing a direct connection between two given stations. *Note:
The station identifiers given in a query may not be part of any bus route!*


### Bus Route Data

The first line of the data gives you the number **N** of bus routes, followed by
**N** bus routes. For each bus route there will be **one** line containing a
space separated list of integers. This list contains at least three integers. The
**first** integer represents the bus **route id**. The bus route id is unique
among all other bus route ids in the input. The remaining integers in the list
represent a list of **station ids**. A station id may occur in multiple bus
routes, but can never occur twice within the same bus route.

You may assume 100,000 as upper limit for the number of bus routes, 1,000,000 as
upper limit for the number of stations, and 1,000 as upper limit for the number
of station of one bus route. Therefore, your internal data structure should
still fit into memory on a suitable machine.

*Note: The bus route data file will be a local file and your service will get
the path to file as the first command line argument. Your service will get
restarted if the file or its location changes.*


### REST API

Your micro service has to implement a REST-API supporting a single URL and only
GET requests. It has to serve
`http://localhost:8088/api/direct?dep_sid={}&arr_sid={}`. The parameters
`dep_sid` (departure) and `arr_sid` (arrival) are two station ids (sid)
represented by 32 bit integers.

The response has to be a single JSON Object:

```
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "type": "object",
  "properties": {
    "dep_sid": {
      "type": "integer"
    },
    "arr_sid": {
      "type": "integer"
    },
    "direct_bus_route": {
      "type": "boolean"
    }
  },
  "required": [
    "dep_sid",
    "arr_sid",
    "direct_bus_route"
  ]
}
```

The `direct_bus_route` field has to be set to `true` if there exists a bus route
in the input data that connects the stations represented by `dep_sid` and
`arr_sid`. Otherwise `direct_bus_route` must be set to `false`.




### Example Data

Bus Routes Data File:
```
3
0 0 1 2 3 4
1 3 1 6 5
2 0 6 4
```

Query:
```
http://localhost:8088/api/direct?dep_sid=3&arr_sid=6
```

Response:
```
{
    "dep_sid": 3,
    "arr_sid": 6,
    "direct_bus_route": true
}
```

### Quick Smoke Test

*Note: This smoke test only checks for compliance, not for correctness!*

We will run some tests on your implementation, and because we are a friendly
bunch of developers, we share a (simplified) version of what we run. There 
are some bash scripts located in the `tests/` directory:
```
build_docker_image.sh
run_test_docker.sh
run_test_local.sh
simple_test.sh
```

Assuming a `bash` environment, you can do a quick local test:
```
bash build.sh
cd tests/
bash run_test_local.sh ../service.sh
```
This should output:
```
TEST PASSED!
```

Given a running `docker` installation and a UNIX-like environment you can run:
```
cd tests/
bash build_docker_image.sh YOUR_GIT_REPO_URL|ZIP_FILE
bash run_test_docker.sh
```
This should output:
```
TEST PASSED!
```



*Note: The docker based test assumes your running native docker. If not (e.g.
your on OSX) please adopt the `run_test_docker.sh` file and replace `localhost`
with the IP of your docker VM*

### Solution

Solution is based on using data structure (map or associative array) to allow us 
easily to query whether departure and arrival station has direct bus connection.
Map has station id as key and set of buses which passes this station as values. 
This allows us to easily check if there is a direct bus by querying first all 
buses passing departure station, then querying all buses passing arrival station 
and if this sets intersect then we have bus that directly connects them.
More details can be found in RoutesService class.
Considering that we prepared map with routes on app startup complexity of given 
solution depends on map set retrieval with O(1) complexity and intersection 
operation which based on AbstractCollection sources seems to be O(n), and in our 
case n is number of buses for given station.

Memory usage of map depends on how many buses will pass through each station, and
considering given information doesn't seem to be a limitation with this approach.
If memory is our main concern we should review algorithm as current one gives
quite good performance even on large data sets.
 
Uses SpringBoot with simple controller, service and basic exception handler.
Main scenarios is covered by Spring Boot Tests (integration tests)
Spring Actuator is also connected as it is good idea to have metrics and makes our 
service more production ready.

Considering specific of current task there is almost no foundation for further 
extension and if I had information about other tasks and use cases then project 
can be organised differently.

Approach for logging based on AOP can be also introduced. If we want to provide public 
API then documentation for REST services should be provided.

