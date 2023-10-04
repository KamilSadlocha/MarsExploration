package com.codecool.marsexploration.mapexplorer.commandCenter;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandCenter {
    private static int numberOfBases = 1;
    private final String id;
    private final Coordinate commandCenterPosition;
    private final List<Coordinate> mineralPoints;
    private final java.util.Map<String, Set<Coordinate>> objectsPoints;
    private final Set<Coordinate> scannedCoordinates;
    private int mineralsOnStock;
    private int createdRovers;
    private int totalMineralsCollected;
    public CommandCenter(Coordinate commandCenterPosition, List<Coordinate> mineralPoints, Map<String, Set<Coordinate>> objectsPoints, Set<Coordinate> scannedCoordinates) {
        this.mineralPoints = mineralPoints;
        this.objectsPoints = objectsPoints;
        this.scannedCoordinates = scannedCoordinates;
        this.id = String.valueOf(numberOfBases);
        this.commandCenterPosition = commandCenterPosition;
        this.mineralsOnStock = 0;
        this.createdRovers = 0;
        this.totalMineralsCollected = 0;
        numberOfBases++;
    }

    public Coordinate getCommandCenterPosition() {
        return commandCenterPosition;
    }

    public int getMineralsOnStock() {
        return mineralsOnStock;
    }

    public int getTotalMineralsCollected() {
        return totalMineralsCollected;
    }

    public void addMineral() {
        totalMineralsCollected++;
        mineralsOnStock++;
    }

    public int getCreatedRovers() {
        return createdRovers;
    }

    public void addCreatedRovers() {
        this.createdRovers = getCreatedRovers() + 1;
    }

    public String getId() {
        return id;
    }

    public void decreaseMineralStock(int amount) {
        mineralsOnStock -= amount;
    }

    public List<Coordinate> getMineralPoints() {
        return mineralPoints;
    }

    public Map<String, Set<Coordinate>> getObjectsPoints() {
        return objectsPoints;
    }

    public Set<Coordinate> getScannedCoordinates() {
        return scannedCoordinates;
    }
}
