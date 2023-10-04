package com.codecool.marsexploration.mapexplorer.colonization;

import com.codecool.marsexploration.mapexplorer.commandCenter.CommandCenter;
import com.codecool.marsexploration.mapexplorer.configuration.ConfigurationParameters;
import com.codecool.marsexploration.mapexplorer.exploration.Simulation;
import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Symbol;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;
import com.codecool.marsexploration.mapexplorer.service.CoordinateCalculatorService;

import java.util.List;
import java.util.Random;

import static com.codecool.marsexploration.mapexplorer.rovers.RoverStatus.*;

public class RoverStatusManagement {

    private final Rover rover;
    private final MoveToCoordinateService moveToCoordinateService;
    private final ConfigurationParameters configurationParameters;
    private final Simulation simulation;

    public RoverStatusManagement(Rover rover,
                                 MoveToCoordinateService moveToCoordinateService,
                                 ConfigurationParameters configurationParameters,
                                 Simulation simulation) {
        this.rover = rover;
        this.moveToCoordinateService = moveToCoordinateService;
        this.configurationParameters = configurationParameters;
        this.simulation = simulation;
    }

    public void goToResource() {
        List<Coordinate> randomMineralAdjacentCoordinates = CoordinateCalculatorService
                .getAdjacentCoordinates(rover.getDestination(), simulation.getMap().getDimension());

        if (randomMineralAdjacentCoordinates.contains(rover.getPosition())) {
            rover.setRoverStatus(EXTRACT);
        } else {
            moveToCoordinateService.moveToCoordinate(rover.getDestination(), rover);
            configurationParameters.symbols().forEach(rover::checkForObjectsAround);
            rover.addScannedCoordinates();
        }
    }

    public void extract() {
        extractMineral(rover, rover.getDestination());
        if (simulation.getCommandCenter() == null) {
            Coordinate newBaseCoordinate = rover.findBestPositionForCommandCenter();
            rover.setDestination(newBaseCoordinate);
            rover.setRoverStatus(GO_TO_NEW_BASE_LOCATION);
        } else {
            rover.setRoverStatus(GO_TO_BASE);
        }
    }

    public void goToNewBaseLocation() {
        if (CoordinateCalculatorService.getAdjacentCoordinates(rover.getDestination(), simulation.getMap().getDimension()).contains(rover.getPosition())) {
            rover.setRoverStatus(BUILD_BASE);
        } else {
            moveToCoordinateService.moveToCoordinate(rover.getDestination(), rover);
            configurationParameters.symbols().forEach(rover::checkForObjectsAround);
            rover.addScannedCoordinates();
        }
    }

    public void buildBase() {
        simulation.setCommandCenter(new CommandCenter(rover.getDestination(), rover.getMineralPoints(), rover.getObjectsPoints(), rover.getScannedCoordinates()));
        rover.clearInventory();
        rover.saveObjectPoint(simulation.getCommandCenter().getCommandCenterPosition(), Symbol.BASE.getSymbol());
        Coordinate randomMineralPoint = rover.getMineralPoints().get(new Random().nextInt(rover.getMineralPoints().size()));
        simulation.getCommandCenter().getMineralPoints().remove(randomMineralPoint);
        rover.setDestination(randomMineralPoint);
        rover.setRoverStatus(GO_TO_RESOURCE);
    }

    public void goToBase() {
        List<Coordinate> baseAdjacentCoordinates = CoordinateCalculatorService
                .getAdjacentCoordinates(simulation.getCommandCenter().getCommandCenterPosition(), simulation.getMap().getDimension());

        if (baseAdjacentCoordinates.contains(rover.getPosition())) {
            rover.setRoverStatus(DEPOSIT_RESOURCE);
        } else {
            moveToCoordinateService.moveToCoordinate(simulation.getCommandCenter().getCommandCenterPosition(), rover);
            configurationParameters.symbols().forEach(rover::checkForObjectsAround);
            rover.addScannedCoordinates();
        }
    }

    public void depositResource() {
        simulation.getCommandCenter().addMineral();
        setupRoverToExtract(rover);
    }

    private void setupRoverToExtract(Rover rover) {
        rover.clearInventory();
        rover.setRoverStatus(GO_TO_RESOURCE);
    }

    private void extractMineral(Rover rover, Coordinate mineralPoint) {
        rover.addToResourceInventory(mineralPoint);
    }
}
