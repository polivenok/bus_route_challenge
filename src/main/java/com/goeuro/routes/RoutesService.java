package com.goeuro.routes;

import java.util.*;

/**
 * Class responsible for operations with routes
 */
public class RoutesService {
    //map to group buses by station they pass, it is useful to check direct bus route
    //<stationId, [buses]>
    private Map<Integer, Set<Integer>> busesByStation = new HashMap<>();

    /**
     * @param route The first integer represents the bus route id.
     *              The bus route id is unique among all other bus route ids in the input.
     *              The remaining integers in the list represent a list of station ids.
     *              A station id may occur in multiple bus routes, but can never occur twice within the same bus route.
     */
    public void addBusRoute(List<Integer> route) {
        Integer busId = route.get(0);
        Set<Integer> stations = new HashSet<>(route.subList(1, route.size()));
        for (Integer station : stations) {
            Set<Integer> buses = busesByStation.get(station);
            if (buses == null) {
                buses = new HashSet<>();
                buses.add(busId);
                busesByStation.put(station, buses);
            } else {
                buses.add(busId);
            }
        }
    }

    /**
     * Check whether there is a direct bus route between two station based on added routes
     *
     * @param departureStationId
     * @param arrivalStationId
     * @return true if there exists a bus route that connects the stations represented by departureStationId and arrivalStationId.
     * Otherwise direct_bus_route must be set to false
     */
    public boolean isDirectBusRouteExists(Integer departureStationId, Integer arrivalStationId) {
        boolean isDirectBusRouteExists = false;
        Set<Integer> busesInDepartureStation = busesByStation.get(departureStationId);
        Set<Integer> busesInArrivalStation = busesByStation.get(arrivalStationId);
        if (busesInDepartureStation != null && busesInArrivalStation != null) {
            //do intersection of sets, so we can have set which have buses which cross both departure and arrival stations
            busesInDepartureStation.retainAll(busesInArrivalStation);
            isDirectBusRouteExists = !busesInDepartureStation.isEmpty();
        }

        return isDirectBusRouteExists;
    }

}
