package com.goeuro.routes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectBusRouteController {

    private static final Logger LOG = LoggerFactory.getLogger(DirectBusRouteController.class);

    @Autowired
    private RoutesService routesService;

    @RequestMapping("/direct")
    //TODO: consider using swagger or REST Docs for documentation
    public DirectBusResponse findDirectBusRoute(@RequestParam(value = "dep_sid") Integer departureStationId,
                                                @RequestParam(value = "arr_sid") Integer arrivalStationId) {
        long start = System.currentTimeMillis();
        try {
            //TODO: consider using AOP for logs
            LOG.info("findDirectBusRoute invoked with dep_sid={} and arr_sid={}", departureStationId, arrivalStationId);
            //TODO: consider using validation framework implementing javax.validate
            if (departureStationId == null) {
                throw new IllegalArgumentException("Departure Station ID should not be null");
            }
            if (arrivalStationId == null) {
                throw new IllegalArgumentException("Arrival Station ID should not be null");
            }
            boolean directBusRouteExists = routesService.isDirectBusRouteExists(departureStationId, arrivalStationId);
            LOG.info("Is direct route exists between {} and {}: {}", departureStationId, arrivalStationId, directBusRouteExists);
            DirectBusResponse response = new DirectBusResponse();
            response.setArrivalStationId(arrivalStationId)
                    .setDepartureStationId(departureStationId)
                    .setDirectRouteExists(directBusRouteExists);
            return response;
        } finally {
            LOG.info("findDirectBusRoute completed in {} ms", System.currentTimeMillis() - start);
        }

    }
}
