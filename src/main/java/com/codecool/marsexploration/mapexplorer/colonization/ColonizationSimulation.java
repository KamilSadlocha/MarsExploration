package com.codecool.marsexploration.mapexplorer.colonization;

import com.codecool.marsexploration.mapexplorer.analizer.AllOutcomeAnalyzer;
import com.codecool.marsexploration.mapexplorer.configuration.ConfigurationParameters;
import com.codecool.marsexploration.mapexplorer.exploration.ExplorationResultDisplay;
import com.codecool.marsexploration.mapexplorer.exploration.Simulation;
import com.codecool.marsexploration.mapexplorer.exploration.SimulationStepsLogging;
import com.codecool.marsexploration.mapexplorer.logger.Logger;
import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.repository.*;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;
import com.codecool.marsexploration.mapexplorer.rovers.RoverStatus;
import com.codecool.marsexploration.mapexplorer.service.CoordinateCalculatorService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.codecool.marsexploration.mapexplorer.rovers.RoverStatus.GO_TO_RESOURCE;

public class ColonizationSimulation {

    public static final int MINERALS_NEEDED_FOR_NEW_ROVER = 5;
    public static final int ROVERS_REQUIRED = 5;
    private final ExplorationResultDisplay explorationResultDisplay;
    private final Simulation simulation;
    private final ConfigurationParameters configurationParameters;
    private final Random random;
    private final MoveToCoordinateService moveToCoordinateService;
    private final Logger logger;
    private final AllOutcomeAnalyzer allOutcomeAnalyzer;
    private final ColonizationRepository colonizationRepository;
    private final RoversRepository roversRepository;
    private final CommandCenterRepository commandCenterRepository;
    private final UUID id;

    public ColonizationSimulation(ExplorationResultDisplay explorationResultDisplay, Simulation simulation, ConfigurationParameters configurationParameters, MoveToCoordinateService moveToCoordinateService, Logger logger, AllOutcomeAnalyzer allOutcomeAnalyzer, CommandCenterRepository commandCenterRepository, RoversRepository roversRepository, ColonizationRepository colonizationRepository) {
        this.explorationResultDisplay = explorationResultDisplay;
        this.simulation = simulation;
        this.configurationParameters = configurationParameters;
        this.moveToCoordinateService = moveToCoordinateService;
        this.logger = logger;
        this.id = UUID.randomUUID();
        this.allOutcomeAnalyzer = allOutcomeAnalyzer;
        this.random = new Random();
        this.commandCenterRepository = commandCenterRepository;
        this.roversRepository = roversRepository;
        this.colonizationRepository = colonizationRepository;
    }

    public void runColonization() {
        boolean isRunning = true;
        List<Rover> rovers = simulation.getRovers();
        SimulationStepsLogging simulationStepsLogging = new SimulationStepsLogging(simulation, logger, allOutcomeAnalyzer);
        prepareFirstRover(rovers.get(0));
        while (isRunning) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            rovers.forEach(rover -> {
                RoverStatusManagement roverStatusManagement = new RoverStatusManagement(rover, moveToCoordinateService, configurationParameters, simulation);
                switch (rover.getRoverStatus()) {
                    case GO_TO_RESOURCE -> roverStatusManagement.goToResource();
                    case EXTRACT -> roverStatusManagement.extract();
                    case GO_TO_NEW_BASE_LOCATION -> roverStatusManagement.goToNewBaseLocation();
                    case BUILD_BASE -> roverStatusManagement.buildBase();
                    case GO_TO_BASE -> roverStatusManagement.goToBase();
                    case DEPOSIT_RESOURCE -> roverStatusManagement.depositResource();
                }

            });

            explorationResultDisplay.displayExploredMap(simulation);
            simulation.setNumberOfSteps(simulation.numberOfSteps() + 1);

            if (isPossibleToBuildNewRover()) {
                Rover newRover = createNewRover();
                simulation.getCommandCenter().decreaseMineralStock(MINERALS_NEEDED_FOR_NEW_ROVER);
                setupNewRower(newRover);
                simulation.addRover(newRover);
                simulation.getCommandCenter().addCreatedRovers();
            }
            simulationStepsLogging.logSteps();

            if (colonizationEndCondition(rovers)) {
                manageDatabase();
                isRunning = false;
            }
        }
    }

    private boolean colonizationEndCondition(List<Rover> rovers) {
        return simulation.getCommandCenter() != null && rovers.size() >= ROVERS_REQUIRED;
    }

    public void manageDatabase() {
        roversRepository.setRoversDto(new RoversDto(id, simulation.getRovers()));
        roversRepository.saveInDatabase();
        colonizationRepository.setColonizationDto(new ColonizationDto(id, List.of(simulation.getCommandCenter())));
        colonizationRepository.saveInDatabase();
        commandCenterRepository.setCommandCenterDto(new CommandCenterDto(id, List.of(simulation.getCommandCenter()), MINERALS_NEEDED_FOR_NEW_ROVER));
        commandCenterRepository.saveInDatabase();
    }

    private boolean isPossibleToBuildNewRover() {
        return simulation.getCommandCenter() != null && simulation.getCommandCenter().getMineralsOnStock() >= MINERALS_NEEDED_FOR_NEW_ROVER;
    }

    private void setupNewRower(Rover newRover) {
        newRover.setRoverStatus(GO_TO_RESOURCE);
        newRover.setMineralPoints(simulation.getCommandCenter().getMineralPoints());
        newRover.setScannedCoordinates(simulation.getCommandCenter().getScannedCoordinates());
        newRover.setObjectsPoints(simulation.getCommandCenter().getObjectsPoints());
        Coordinate randomMineralPoint = newRover.getMineralPoints().get(new Random().nextInt(newRover.getMineralPoints().size()));
        simulation.getCommandCenter().getMineralPoints().remove(randomMineralPoint);
        newRover.setDestination(randomMineralPoint);
    }

    private Rover createNewRover() {
        Coordinate newRoverCoordinate = getNewRoverCoordinate();
        return new Rover(newRoverCoordinate, 2, simulation.getMap());
    }

    private Coordinate getNewRoverCoordinate() {
        List<Coordinate> baseAdjacentCoordinates = CoordinateCalculatorService.getAdjacentCoordinates(simulation.getCommandCenter().getCommandCenterPosition(), simulation.getMap().getDimension());
        List<Coordinate> baseFreeAdjacentCoordinates = baseAdjacentCoordinates.stream()
                .filter(coordinate -> simulation.getMap().isEmpty(coordinate))
                .toList();
        return baseFreeAdjacentCoordinates.get(random.nextInt(baseFreeAdjacentCoordinates.size()));
    }

    private void prepareFirstRover(Rover rover) {
        rover.setRoverStatus(RoverStatus.GO_TO_RESOURCE);
        rover.createMineralPoints();
        Coordinate randomMineralPoint = rover.getMineralPoints().get(random.nextInt(rover.getMineralPoints().size()));
        rover.setDestination(randomMineralPoint);
    }
}
