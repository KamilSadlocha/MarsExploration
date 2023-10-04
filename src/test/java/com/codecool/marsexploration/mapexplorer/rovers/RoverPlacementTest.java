package com.codecool.marsexploration.mapexplorer.rovers;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RoverPlacementTest {

    @Test
    public void testGenerateRandomCoordinateForRover(){
        String[][] mapData = {
                {" ", "*", "*", "*", "*"},
                {"*", "*", " ", "*", "*"},
                {"*", "*", " ", "*", "*"},
                {"*", "*", "*", "*", "*"},
                {"*", "*", "*", "*", "*"}
        };
        Map map = new Map(mapData, true);
        Coordinate spaceshipCoordinate = new Coordinate(2, 2);
        RoverPlacement roverPlacement = new RoverPlacement(map, spaceshipCoordinate);
        Coordinate coordinate = roverPlacement.generateCoordinateForRover();

        assertNotNull(coordinate);
        assertTrue(coordinate.Y() >= 0 && coordinate.Y() < map.getDimension());
        assertTrue(coordinate.X() >= 0 && coordinate.X() < map.getDimension());
        assertTrue(map.isEmpty(coordinate));
    }
}