package com.codecool.marsexploration.mapexplorer.exploration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;

import java.util.List;

public class ReturnMovementService implements MovementService {
    private final Rover rover;


    public ReturnMovementService(Rover rover) {
        this.rover = rover;
    }

    @Override
    public void move() {
        List<Coordinate> previousPositions = rover.getPreviousPositions();
        int indexOfCurrentPosition = previousPositions.indexOf(rover.getPosition());
        int indexOfPreviousPosition = indexOfCurrentPosition - 1;
        Coordinate previousPosition = rover.getPreviousPositions().get(indexOfPreviousPosition);
        rover.setPosition(previousPosition);
    }
}
