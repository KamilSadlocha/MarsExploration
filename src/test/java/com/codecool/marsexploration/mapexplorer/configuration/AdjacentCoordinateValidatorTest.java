package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacentCoordinateValidatorTest {

    @Test
    public void isValidWhenAllAdjacentFieldsAreFree() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        Map map = new Map(testMapArray, true);

        AdjacentCoordinateValidator adjacentCoordinateValidator = new AdjacentCoordinateValidator();

        assertTrue(adjacentCoordinateValidator.isValid(configurationParameters, map));
    }

    @Test
    public void isNotValidWhenAllAdjacentFieldsAreTaken() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{"x", "x", "x"}, {"x", "x", "x"}, {"x", "x", "x"}};
        Map map = new Map(testMapArray, true);

        AdjacentCoordinateValidator adjacentCoordinateValidator = new AdjacentCoordinateValidator();

        assertFalse(adjacentCoordinateValidator.isValid(configurationParameters, map));
    }

    @Test
    public void isValidWhenSomeAdjacentFieldsAreTaken() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{"x", " ", "x"}, {" ", "x", "x"}, {"x", "x", " "}};
        Map map = new Map(testMapArray, true);

        AdjacentCoordinateValidator adjacentCoordinateValidator = new AdjacentCoordinateValidator();

        assertTrue(adjacentCoordinateValidator.isValid(configurationParameters, map));
    }

    @Test
    public void isValidWhenOneAdjacentFieldIsTaken() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{"x", "x", "x"}, {" ", "x", "x"}, {"x", "x", "x"}};
        Map map = new Map(testMapArray, true);

        AdjacentCoordinateValidator adjacentCoordinateValidator = new AdjacentCoordinateValidator();

        assertTrue(adjacentCoordinateValidator.isValid(configurationParameters, map));
    }

}