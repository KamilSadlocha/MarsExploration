package com.codecool.marsexploration.mapexplorer.service;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class CoordinateCalculatorService {
    public static List<Coordinate> getAdjacentCoordinates(Coordinate coordinate, int dimension) {
        List<Coordinate> coordinates = new ArrayList<>();

        if (coordinate.X() != dimension - 1) {
            coordinates.add(new Coordinate(coordinate.X() + 1, coordinate.Y()));
        }
        if (coordinate.Y() != dimension - 1) {
            coordinates.add(new Coordinate(coordinate.X(), coordinate.Y() + 1));
        }

        if (coordinate.X() != 0) {
            coordinates.add(new Coordinate(coordinate.X() - 1, coordinate.Y()));
        }
        if (coordinate.Y() != 0) {
            coordinates.add(new Coordinate(coordinate.X(), coordinate.Y() - 1));
        }
        return coordinates;
    }

    public static List<Coordinate> getCoordinatesAround(Coordinate position, int sightRange, int dimension) {
        List<Coordinate> coordinatesAround = new ArrayList<>();
        for (int linearSight = 0; linearSight < sightRange; linearSight++) {
            int diagonalSight = sightRange - linearSight;
            coordinatesAround.add(new Coordinate(position.X() + linearSight, position.Y()));
            coordinatesAround.add(new Coordinate(position.X(), position.Y() + linearSight));
            coordinatesAround.add(new Coordinate(position.X() - linearSight, position.Y()));
            coordinatesAround.add(new Coordinate(position.X(), position.Y() - linearSight));
            coordinatesAround.add(new Coordinate(position.X() + diagonalSight, position.Y() + linearSight));
            coordinatesAround.add(new Coordinate(position.X() - diagonalSight, position.Y() + linearSight));
            coordinatesAround.add(new Coordinate(position.X() + diagonalSight, position.Y() - linearSight));
            coordinatesAround.add(new Coordinate(position.X() - diagonalSight, position.Y() - linearSight));
            coordinatesAround.add(new Coordinate(position.X() + linearSight, position.Y() + diagonalSight));
            coordinatesAround.add(new Coordinate(position.X() - linearSight, position.Y() + diagonalSight));
            coordinatesAround.add(new Coordinate(position.X() + linearSight, position.Y() - diagonalSight));
            coordinatesAround.add(new Coordinate(position.X() - linearSight, position.Y() - diagonalSight));
        }
        return coordinatesAround.stream().filter(coordinate ->
                coordinate.Y() >= 0 && coordinate.Y() < dimension &&
                coordinate.X() >= 0 && coordinate.X() < dimension)
                .toList();
    }
}
