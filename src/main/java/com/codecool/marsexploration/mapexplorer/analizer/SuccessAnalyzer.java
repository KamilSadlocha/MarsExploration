package com.codecool.marsexploration.mapexplorer.analizer;

import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.exploration.Simulation;
import com.codecool.marsexploration.mapexplorer.maploader.model.Coordinate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SuccessAnalyzer implements OutcomeAnalyzer {

    private final int resourcesToSuccess;
    private final List<String> valuableResources;

    public SuccessAnalyzer(int resourcesToSuccess, List<String> valuableResources) {
        this.resourcesToSuccess = resourcesToSuccess;
        this.valuableResources = valuableResources;
    }

    @Override
    public ExplorationOutcome analyze(Simulation simulation) {
        Map<String, Set<Coordinate>> desirableResources = getDesirableResources(simulation);

        int desirableResourcesNumber = countDesirableResources(desirableResources);

        if (desirableResourcesNumber >= resourcesToSuccess) {
            return ExplorationOutcome.COLONIZABLE;
        } else return null;
    }

    private Map<String, Set<Coordinate>> getDesirableResources(Simulation simulation) {
        return simulation.getRovers().get(0).getObjectsPoints().entrySet().stream()
                .filter(entry -> valuableResources.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static int countDesirableResources(Map<String, Set<Coordinate>> desirableResources) {
        return desirableResources.values().stream().mapToInt(Set::size).sum();
    }


}
