package com.codecool.marsexploration.mapexplorer.colonization;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;

public class SimpleMoveToCoordinateService implements MoveToCoordinateService {

    public void moveToCoordinate(Coordinate destination, Rover rover) {
        Coordinate roverPosition = rover.getPosition();
        int X = roverPosition.X();
        int Y = roverPosition.Y();
        if (roverPosition.X() > destination.X()) {
            X -= 1;
        } else if (roverPosition.X() < destination.X()) {
            X += 1;
        } else if (roverPosition.Y() > destination.Y()) {
            Y -= 1;
        } else if (roverPosition.Y() < destination.Y()) {
            Y += 1;
        }
        Coordinate newPosition = new Coordinate(X, Y);
        rover.setPosition(newPosition);
    }
}
