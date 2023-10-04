package com.codecool.marsexploration.mapexplorer.repository;

import com.codecool.marsexploration.mapexplorer.exploration.ExplorationOutcome;

public record ExplorationsDto(int steps, int numberOfResources, ExplorationOutcome explorationOutcome) {
}
