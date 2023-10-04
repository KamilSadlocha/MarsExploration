package com.codecool.marsexploration.mapexplorer.rovers;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import com.codecool.marsexploration.mapexplorer.service.CoordinateCalculatorService;

import java.util.List;
import java.util.Random;

public class RoverPlacement {
    private final Map map;
    private final Coordinate spaceshipCoordinate;
    private final Random random;

    public RoverPlacement(Map map, Coordinate spaceshipCoordinate) {
        this.map = map;
        this.spaceshipCoordinate = spaceshipCoordinate;
        random = new Random();
    }

    public Coordinate generateCoordinateForRover() {

        List<Coordinate> adjacentCoordinates = CoordinateCalculatorService.getAdjacentCoordinates(spaceshipCoordinate, map.getDimension());

        List<Coordinate> freeAdjacentCoordinates = getFreeAdjacentCoordinates(adjacentCoordinates);

        return freeAdjacentCoordinates.get(random.nextInt(freeAdjacentCoordinates.size()));
    }

    private List<Coordinate> getFreeAdjacentCoordinates(List<Coordinate> adjacentCoordinates) {
        return adjacentCoordinates.stream()
                .filter(map::isEmpty)
                .toList();
    }

}
