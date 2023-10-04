package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesValidatorTest {
    @Test
    public void isValidWhenSymbolsAreNotEmpty() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " "}, {" ", " "}};
        Map map = new Map(testMapArray, true);

        ResourcesValidator resourcesValidator = new ResourcesValidator();

        assertTrue(resourcesValidator.isValid(configurationParameters, map));
    }

    @Test
    public void isNotValidWhenSymbolsAreEmpty() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of(), 1000);
        String[][] testMapArray = {{" ", " "}, {" ", " "}};
        Map map = new Map(testMapArray, true);

        ResourcesValidator resourcesValidator = new ResourcesValidator();

        assertFalse(resourcesValidator.isValid(configurationParameters, map));
    }

}