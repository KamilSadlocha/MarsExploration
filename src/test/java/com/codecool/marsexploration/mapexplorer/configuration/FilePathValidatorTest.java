package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilePathValidatorTest {

    @Test
    public void isValidWhenPathIsCorrect() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("src/main/resources/exploration-1.map", new Coordinate(0, 0), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " "}, {" ", " "}};
        Map map = new Map(testMapArray, true);

        FilePathValidator filePathValidator = new FilePathValidator();

        assertTrue(filePathValidator.isValid(configurationParameters, map));
    }

    @Test
    public void isNotValidWhenPathIsEmpty() {
        ConfigurationParameters configurationParameters = new ConfigurationParameters("", new Coordinate(0, 0), List.of("*"), 1000);
        String[][] testMapArray = {{" ", " "}, {" ", " "}};
        Map map = new Map(testMapArray, true);

        FilePathValidator filePathValidator = new FilePathValidator();

        assertFalse(filePathValidator.isValid(configurationParameters, map));
    }

}