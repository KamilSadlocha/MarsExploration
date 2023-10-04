package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmptyLandingSpotValidatorTest {

    @Test
    public void isValidWhenLandingSpotIsEmpty() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        Map map = new Map(testMapArray, true);

        EmptyLandingSpotValidator emptyLandingSpotValidator = new EmptyLandingSpotValidator();

        assertTrue(emptyLandingSpotValidator.isValid(configurationParameters, map));
    }

    @Test
    public void isNotValidWhenLandingSpotIsTaken() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " ", " "}, {" ", "x", " "}, {" ", " ", " "}};
        Map map = new Map(testMapArray, true);

        EmptyLandingSpotValidator emptyLandingSpotValidator = new EmptyLandingSpotValidator();

        assertFalse(emptyLandingSpotValidator.isValid(configurationParameters, map));
    }

}