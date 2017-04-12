package com.goeuro;

import com.goeuro.routes.RoutesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.CommandLinePropertySource;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class BusRouteChallengeApplication {


    public static void main(String[] args) {
        SpringApplication.run(BusRouteChallengeApplication.class, args);
    }

    /**
     * Based on command line parameter reads file content and initialises RoutesService
     *
     * @param dataFilePath path to data file with routes
     * @return initialized RoutesService bean
     * @throws IOException in case of file reading issues
     * @see CommandLinePropertySource Spring docs for description of 'nonOptionArgs'
     */
    @Bean
    public RoutesService directBusRouteService(@Value("${nonOptionArgs}") String dataFilePath) throws IOException {
        File dataFile = new File(dataFilePath);
        RoutesService routesService = new RoutesService();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            //ignoring first line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                List<Integer> route = Stream.of(line.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                routesService.addBusRoute(route);
            }
        }
        return routesService;
    }


}
