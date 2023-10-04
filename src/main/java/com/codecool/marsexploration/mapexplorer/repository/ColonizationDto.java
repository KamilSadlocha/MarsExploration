package com.codecool.marsexploration.mapexplorer.repository;

import com.codecool.marsexploration.mapexplorer.commandCenter.CommandCenter;

import java.util.List;
import java.util.UUID;

public record ColonizationDto (UUID colonizationId, List<CommandCenter> commandCenters) {
}
