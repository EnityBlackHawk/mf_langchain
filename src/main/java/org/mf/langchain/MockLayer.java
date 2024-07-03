package org.mf.langchain;

import org.mf.langchain.enums.ProcessStepName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockLayer {

    public static Map<ProcessStepName, String> values = Map.of();

    public static boolean isActivated = false;
    public static final String MOCK_GENERATE_MODEL = """
            // Aircraft collection
            {
            \t"id": NumberInt,
            \t"type": String,
            \t"airline": {
            \t\t"id": NumberInt,
            \t\t"name": String
            \t},
            \t"manufacturer": {
            \t\t"id": NumberInt,
            \t\t"name": String
            \t},
            \t"registration": String,
            \t"max_passengers": NumberInt
            }

            // Airport collection
            {
            \t"id": String,
            \t"name": String,
            \t"city": String,
            \t"country": String
            }

            // Passenger collection
            {
            \t"id": NumberInt,
            \t"first_name": String,
            \t"last_name": String,
            \t"passport_number": String,
            \t"bookings": [
            \t\t{
            \t\t\t"flight_number": String,  // flight.number
            \t\t\t"seat": String
            \t\t}
            \t]
            }

            // Flight collection
            {
            \t"number": String,
            \t"airport_from": {
            \t\t"id": String,
            \t\t"name": String,
            \t\t"city": String,
            \t\t"country": String
            \t},
            \t"airport_to": {
            \t\t"id": String,
            \t\t"name": String,
            \t\t"city": String,
            \t\t"country": String
            \t},
            \t"departure_time_scheduled": Date,
            \t"departure_time_actual": Date,
            \t"arrival_time_scheduled": Date,
            \t"arrival_time_actual": Date,
            \t"gate": NumberInt,
            \t"aircraft": {
            \t\t"id": NumberInt,
            \t\t"type": String,
            \t\t"airline": {
            \t\t\t"id": NumberInt,
            \t\t\t"name": String
            \t\t}
            \t},
            \t"connects_to": String  // flight.number of connecting flight
            }""";

    public static final String MOCK_GENERATE_JAVA_CODE = "";


    public void init(ProcessStepName[] ... steps) {
        isActivated = true;
        var l = Arrays.stream(steps).toList();
        if(l.contains(ProcessStepName.GENERATE_MODEL)) {
            values.put(ProcessStepName.GENERATE_MODEL, MOCK_GENERATE_MODEL);
        }
        if(l.contains(ProcessStepName.GENERATE_JAVA_CODE)) {
            values.put(ProcessStepName.GENERATE_JAVA_CODE, MOCK_GENERATE_JAVA_CODE);
        }

    }

    public static Optional<String> getValue(ProcessStepName step) {
        return Optional.ofNullable(values.get(step));
    }

}
