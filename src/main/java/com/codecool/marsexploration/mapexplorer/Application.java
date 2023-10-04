package com.codecool.marsexploration.mapexplorer;

import com.codecool.marsexploration.mapexplorer.analizer.*;
import com.codecool.marsexploration.mapexplorer.colonization.ColonizationSimulation;
import com.codecool.marsexploration.mapexplorer.colonization.MoveToCoordinateService;
import com.codecool.marsexploration.mapexplorer.colonization.SimpleMoveToCoordinateService;
import com.codecool.marsexploration.mapexplorer.configuration.*;
import com.codecool.marsexploration.mapexplorer.exploration.*;
import com.codecool.marsexploration.mapexplorer.logger.Logger;
import com.codecool.marsexploration.mapexplorer.logger.LoggerImpl;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoader;
import com.codecool.marsexploration.mapexplorer.maploader.MapLoaderImpl;
import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import com.codecool.marsexploration.mapexplorer.repository.*;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;
import com.codecool.marsexploration.mapexplorer.rovers.RoverPlacement;

import java.util.List;
import java.util.Set;

import static com.codecool.marsexploration.mapexplorer.maploader.model.Symbol.*;

public class Application {
    private static final String workDir = "src/main";
    private static final String JDBC_DATABASE_URL = "jdbc:sqlite:src/main/resources/exploration.db";

    public static void main(String[] args) {
        String mapFile = workDir + "/resources/exploration-2.map";
        Coordinate landingSpot = new Coordinate(6, 6);
        List<String> objectsToScan = List.of(MINERAL.getSymbol(), WATER.getSymbol(), MOUNTAIN.getSymbol(), PIT.getSymbol());
        int maxSteps = 1000;

        ConfigurationParameters configurationParameters = new ConfigurationParameters(mapFile, landingSpot, objectsToScan, maxSteps);

        Logger logger = new LoggerImpl();
        logger.clearFile();

        MapLoader mapLoader = new MapLoaderImpl();
        Map map = mapLoader.load(mapFile);

        Set<Validator> validators = Set.of(new EmptyLandingSpotValidator(), new FilePathValidator(), new AdjacentCoordinateValidator(), new ResourcesValidator(), new TimeoutValidator());
        ConfigurationValidator configurationValidator = new ConfigurationValidator(map, validators);

        List<String> valuableResources = List.of(MINERAL.getSymbol());

        Set<OutcomeAnalyzer> analyzers = Set.of(new SuccessAnalyzer(5, valuableResources), new TimeoutAnalyzer(), new LackOfResourcesAnalyzer(0.7));
        AllOutcomeAnalyzer allOutcomeAnalyzer = new AllOutcomeAnalyzer(analyzers);

        RoverPlacement roverPlacement = new RoverPlacement(map, landingSpot);

        Coordinate roverStartingSpot = roverPlacement.generateCoordinateForRover();
        int sightRange = 2;
        Rover rover = new Rover(roverStartingSpot, sightRange, map);
        List<Rover> rovers = List.of(rover);

        MovementService movementService = new RandomAvoidingRevisitingMovementService(rover, map);

        ExplorationResultDisplay explorationResultDisplay = new ExplorationResultDisplay(map.getDimension());

        ExplorationsRepository explorationsRepository = new ExplorationsRepository(JDBC_DATABASE_URL);
        ColonizationRepository colonizationRepository = new ColonizationRepository(JDBC_DATABASE_URL);
        RoversRepository roversRepository = new RoversRepository(JDBC_DATABASE_URL);
        CommandCenterRepository commandCenterRepository = new CommandCenterRepository(JDBC_DATABASE_URL);
        List<Repository> repositories = List.of(
                explorationsRepository, colonizationRepository, commandCenterRepository, roversRepository
        );
        createTablesIfDoesNotExist(repositories);

        ExplorationSimulator explorationSimulator = new ExplorationSimulator(explorationResultDisplay, mapLoader, movementService, allOutcomeAnalyzer, logger, explorationsRepository);

        if (configurationValidator.validate(configurationParameters)) {
            System.out.println("Configuration validation successful. Starting simulation.");
            Simulation simulation = explorationSimulator.runSimulation(configurationParameters, rovers);
            if (simulation.explorationOutcome() == ExplorationOutcome.COLONIZABLE) {
                MoveToCoordinateService moveToCoordinateService = new SimpleMoveToCoordinateService();
                ColonizationSimulation colonizationSimulation = new ColonizationSimulation(explorationResultDisplay, simulation, configurationParameters, moveToCoordinateService, logger, allOutcomeAnalyzer, commandCenterRepository, roversRepository, colonizationRepository);
                colonizationSimulation.runColonization();
            }
        } else {
            System.out.println("Configuration validation failed. Simulation will not run.");
        }
    }

    public static void createTablesIfDoesNotExist(List<Repository> repositories) {
        for (Repository repository : repositories) {
            repository.createTableIfDoesNotExist();
        }
    }
}

