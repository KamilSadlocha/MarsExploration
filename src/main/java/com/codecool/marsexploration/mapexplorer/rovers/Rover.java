package com.codecool.marsexploration.mapexplorer.rovers;

import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import com.codecool.marsexploration.mapexplorer.service.CoordinateCalculatorService;

import java.util.*;

import static com.codecool.marsexploration.mapexplorer.maploader.model.Symbol.MINERAL;

public class Rover {
    private static int numberOfRovers = 1;
    private final List<Coordinate> previousPositions;
    private final String id;
    private final Map map;
    private final int sightRange;
    private java.util.Map<String, Set<Coordinate>> objectsPoints;
    private Set<Coordinate> scannedCoordinates;
    private Coordinate position;
    private List<Coordinate> mineralPoints;
    private RoverStatus roverStatus;
    private String[] resourceInventory = new String[1];
    private Coordinate destination;
    private int collectedResources;


    public Rover(Coordinate position, int sightRange, Map map) {
        this.id = "rover-" + numberOfRovers;
        this.position = position;
        this.sightRange = sightRange;
        this.objectsPoints = new HashMap<>();
        this.map = map;
        previousPositions = new ArrayList<>();
        scannedCoordinates = new HashSet<>();
        mineralPoints = null;
        this.roverStatus = RoverStatus.EXPLORE;
        this.collectedResources = 0;
        numberOfRovers++;
    }


    public List<Coordinate> getPreviousPositions() {
        return previousPositions;
    }

    public RoverStatus getRoverStatus() {
        return roverStatus;
    }

    public void setRoverStatus(RoverStatus roverStatus) {
        this.roverStatus = roverStatus;
    }

    public Set<Coordinate> getScannedCoordinates() {
        return scannedCoordinates;
    }

    public void setScannedCoordinates(Set<Coordinate> scannedCoordinates) {
        this.scannedCoordinates = scannedCoordinates;
    }

    public java.util.Map<String, Set<Coordinate>> getObjectsPoints() {
        return objectsPoints;
    }

    public void setObjectsPoints(java.util.Map<String, Set<Coordinate>> objectsPoints) {
        this.objectsPoints = objectsPoints;
    }

    public void addScannedCoordinates() {
        List<Coordinate> coordinatesToAdd = CoordinateCalculatorService.getCoordinatesAround(position, sightRange, map.getDimension());
        scannedCoordinates.addAll(coordinatesToAdd);
    }

    public void checkForObjectsAround(String resource) {
        List<Coordinate> coordinatesToCheck = CoordinateCalculatorService.getCoordinatesAround(position, sightRange, map.getDimension());
        scannedCoordinates.addAll(coordinatesToCheck);
        coordinatesToCheck.forEach(coordinate -> {
            if (map.getByCoordinate(coordinate).equals(resource)) {
                saveObjectPoint(coordinate, resource);
            }
        });
    }

    public void saveObjectPoint(Coordinate coordinate, String resource) {
        Set<Coordinate> coordinateList;
        if (objectsPoints.containsKey(resource)) {
            coordinateList = objectsPoints.get(resource);
        } else {
            coordinateList = new HashSet<>() {
            };
        }
        coordinateList.add(coordinate);
        objectsPoints.put(resource, coordinateList);
    }

    public Coordinate findBestPositionForCommandCenter() {
        List<Coordinate> diamondPositions = getMineralPoints();

        Coordinate bestPosition = null;
        double minTotalDistance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < map.getRepresentation().length; i++) {
            for (int j = 0; j < map.getRepresentation().length; j++) {
                Coordinate currentPosition = new Coordinate(i, j);
                double totalDistance = 0;

                for (Coordinate diamond : diamondPositions) {
                    double distance = calculateDistance(currentPosition.X(), currentPosition.Y(), diamond.X(), diamond.Y());
                    totalDistance += distance;
                }

                if (totalDistance < minTotalDistance) {
                    minTotalDistance = totalDistance;
                    if (map.isEmpty(currentPosition)) {
                        bestPosition = currentPosition;
                    }
                }
            }
        }
        return bestPosition;
    }

    public double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public void addToPreviousPositionsList(Coordinate coordinate) {
        previousPositions.add(coordinate);
    }

    public void createMineralPoints() {
        mineralPoints = new ArrayList<>(objectsPoints.get(MINERAL.getSymbol()).stream().toList());
    }

    public List<Coordinate> getMineralPoints() {
        return mineralPoints;
    }

    public void setMineralPoints(List<Coordinate> mineralPoints) {
        this.mineralPoints = mineralPoints;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void addToResourceInventory(Coordinate randomMineralPoint) {
        resourceInventory[0] = map.getByCoordinate(randomMineralPoint);
        collectedResources++;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public int getCollectedResources() {
        return collectedResources;
    }

    public void setDestination(Coordinate destination) {
        this.destination = destination;
    }

    public void clearInventory() {
        resourceInventory = new String[1];
    }
}
