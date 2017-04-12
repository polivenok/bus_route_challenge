package com.goeuro.routes;

public class DirectBusResponse {
    private Integer departureStationId;
    private Integer arrivalStationId;
    private boolean directRouteExists;

    public Integer getDepartureStationId() {
        return departureStationId;
    }

    public DirectBusResponse setDepartureStationId(Integer departureStationId) {
        this.departureStationId = departureStationId;
        return this;
    }

    public Integer getArrivalStationId() {
        return arrivalStationId;
    }

    public DirectBusResponse setArrivalStationId(Integer arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
        return this;
    }

    public boolean isDirectRouteExists() {
        return directRouteExists;
    }

    public DirectBusResponse setDirectRouteExists(boolean directRouteExists) {
        this.directRouteExists = directRouteExists;
        return this;
    }
}
