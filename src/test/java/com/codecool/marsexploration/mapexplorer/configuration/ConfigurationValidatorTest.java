package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigurationValidatorTest {

    @Test
    public void validateWhenConfigurationIsOk() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("src/main/resources/exploration-1.map", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        Map map = new Map(testMapArray, true);
        Set<Validator> validators = Set.of(new EmptyLandingSpotValidator(), new FilePathValidator(), new AdjacentCoordinateValidator(), new ResourcesValidator(), new TimeoutValidator());

        ConfigurationValidator configurationValidator = new ConfigurationValidator(map, validators);

        assertTrue(configurationValidator.validate(configurationParameters));
    }

    @Test
    public void validateWhenFilePathIsMissing() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        Map map = new Map(testMapArray, true);
        Set<Validator> validators = Set.of(new EmptyLandingSpotValidator(), new FilePathValidator(), new AdjacentCoordinateValidator(), new ResourcesValidator(), new TimeoutValidator());

        ConfigurationValidator configurationValidator = new ConfigurationValidator(map, validators);

        assertFalse(configurationValidator.validate(configurationParameters));
    }

    @Test
    public void validateWhenFilePathIsMissingAndTimeoutEqualsZero() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("", new Coordinate(1, 1), List.of("*"), 0);
        String[][] testMapArray = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        Map map = new Map(testMapArray, true);
        Set<Validator> validators = Set.of(new EmptyLandingSpotValidator(), new FilePathValidator(), new AdjacentCoordinateValidator(), new ResourcesValidator(), new TimeoutValidator());

        ConfigurationValidator configurationValidator = new ConfigurationValidator(map, validators);

        assertFalse(configurationValidator.validate(configurationParameters));
    }

}