package com.codecool.marsexploration.mapexplorer.exploration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;
import com.codecool.marsexploration.mapexplorer.service.CoordinateCalculatorService;

import java.util.List;
import java.util.Random;

public class RandomMovementService implements MovementService {
    private final Rover rover;
    private final Map map;
    private final Random random;

    public RandomMovementService(Rover rover, Map map) {
        this.rover = rover;
        this.map = map;
        random = new Random();
    }

    @Override
    public void move() {
        Coordinate currentRoverPosition = rover.getPosition();

        rover.addToPreviousPositionsList(currentRoverPosition);

        List<Coordinate> adjacentCoordinates = CoordinateCalculatorService.getAdjacentCoordinates(currentRoverPosition, map.getDimension());

        List<Coordinate> freeAdjacentCoordinates = adjacentCoordinates.stream()
                .filter(map::isEmpty)
                .toList();

        Coordinate randomFreeAdjacentCoordinate = freeAdjacentCoordinates.get(random.nextInt(freeAdjacentCoordinates.size()));

        rover.setPosition(randomFreeAdjacentCoordinate);
    }
}
