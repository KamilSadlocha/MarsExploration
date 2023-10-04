package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import com.codecool.marsexploration.mapexplorer.service.CoordinateCalculatorService;

import java.util.List;

public class AdjacentCoordinateValidator implements Validator{
    @Override
    public boolean isValid(ConfigurationParameters configurationParameters, Map map) {

        List<Coordinate> adjacentCoordinates = CoordinateCalculatorService.getAdjacentCoordinates(configurationParameters.spaceshipLandingPoint(), map.getDimension());
        return adjacentCoordinates.stream().anyMatch(map::isEmpty);
    }
}
