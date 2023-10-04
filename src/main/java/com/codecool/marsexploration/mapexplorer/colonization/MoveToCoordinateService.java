package com.codecool.marsexploration.mapexplorer.colonization;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;

public interface MoveToCoordinateService {

    void moveToCoordinate(Coordinate destination, Rover rover);
}
