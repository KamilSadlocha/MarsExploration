package com.codecool.marsexploration.mapexplorer.repository;

import com.codecool.marsexploration.mapexplorer.rovers.Rover;

import java.util.List;
import java.util.UUID;

public record RoversDto(UUID colonizationId, List<Rover> rovers) {
}
