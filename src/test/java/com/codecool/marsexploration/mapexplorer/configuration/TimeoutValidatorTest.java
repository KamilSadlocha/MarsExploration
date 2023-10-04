package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeoutValidatorTest {

    @Test
    public void isValidWhenTimeoutIsGreaterThanZero() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " "}, {" ", " "}, {" ", " "}};
        Map map = new Map(testMapArray, true);

        TimeoutValidator timeoutValidator = new TimeoutValidator();

        assertTrue(timeoutValidator.isValid(configurationParameters, map));
    }

    @Test
    public void isNotValidWhenTimeoutEqualsZero() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("test", new Coordinate(1, 1), List.of("*"), 0);
        String[][] testMapArray = {{" ", " "}, {" ", " "}, {" ", " "}};
        Map map = new Map(testMapArray, true);

        TimeoutValidator timeoutValidator = new TimeoutValidator();

        assertFalse(timeoutValidator.isValid(configurationParameters, map));
    }

}