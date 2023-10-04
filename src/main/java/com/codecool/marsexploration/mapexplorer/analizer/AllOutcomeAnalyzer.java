package com.codecool.marsexploration.mapexplorer.analizer;

import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;
import com.codecool.marsexploration.mapexplorer.exploration.Simulation;

import java.util.Set;

public class AllOutcomeAnalyzer {
    private final Set<OutcomeAnalyzer> analyzers;

    public AllOutcomeAnalyzer(Set<OutcomeAnalyzer> analyzers) {
        this.analyzers = analyzers;
    }

    public ExplorationOutcome analyze(Simulation simulation) {
        for (OutcomeAnalyzer analyzer : analyzers) {
            ExplorationOutcome outcome = analyzer.analyze(simulation);
            if (outcome != null) {
                return outcome;
            }
        }
        return null;
    }
}
