package com.codecool.marsexploration.mapexplorer.exploration;


import com.codecool.marsexploration.mapexplorer.commandCenter.CommandCenter;
import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;
import com.codecool.marsexploration.mapexplorer.maploader.model.Map;
import com.codecool.marsexploration.mapexplorer.rovers.Rover;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final int stepsToTimeout;
    private final List<Rover> rovers;
    private final Coordinate spaceshipCoordinate;
    private final Map map;
    private final List<String> resourcesToMonitor;
    private int numberOfSteps;
    private ExplorationOutcome explorationOutcome;
    private CommandCenter commandCenter;

    public Simulation(int stepsToTimeout,
                      List<Rover> rovers,
                      Coordinate spaceshipCoordinate,
                      Map map,
                      List<String> resourcesToMonitor) {
        this.stepsToTimeout = stepsToTimeout;
        this.rovers = new ArrayList<>(rovers);
        this.spaceshipCoordinate = spaceshipCoordinate;
        this.map = map;
        this.resourcesToMonitor = resourcesToMonitor;
        this.numberOfSteps = 0;
        this.explorationOutcome = null;
        this.commandCenter = null;
    }

    public int numberOfSteps() {
        return numberOfSteps;
    }

    public int stepsToTimeout() {
        return stepsToTimeout;
    }

    public List<Rover> getRovers() {
        return rovers;
    }

    public void addRover(Rover rover) {
        rovers.add(rover);
    }

    public Map getMap() {
        return map;
    }

    public ExplorationOutcome explorationOutcome() {
        return explorationOutcome;
    }

    @Override
    public String toString() {
        return "Simulation[\n" +
                "numberOfSteps=" + numberOfSteps + ",\n " +
                "stepsToTimeout=" + stepsToTimeout + ",\n " +
                "rover=" + rovers + ", " +
                "spaceshipCoordinate=" + spaceshipCoordinate + ",\n " +
                "map=" + map + ",\n " +
                "resourcesToMonitor=" + resourcesToMonitor + ",\n " +
                "explorationOutcome=" + explorationOutcome + ']';
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public void setExplorationOutcome(ExplorationOutcome explorationOutcome) {
        this.explorationOutcome = explorationOutcome;
    }

    public CommandCenter getCommandCenter() {
        return commandCenter;
    }

    public void setCommandCenter(CommandCenter commandCenter) {
        this.commandCenter = commandCenter;
    }
}
