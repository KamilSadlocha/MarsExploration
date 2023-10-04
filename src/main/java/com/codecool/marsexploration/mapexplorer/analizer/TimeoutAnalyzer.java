package com.codecool.marsexploration.mapexplorer.analizer;

import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.exploration.Simulation;

public class TimeoutAnalyzer implements OutcomeAnalyzer {
    @Override
    public ExplorationOutcome analyze(Simulation simulation) {
        if (simulation.numberOfSteps() >= simulation.stepsToTimeout()) {
            return ExplorationOutcome.TIMEOUT;
        } else return null;
    }
}
