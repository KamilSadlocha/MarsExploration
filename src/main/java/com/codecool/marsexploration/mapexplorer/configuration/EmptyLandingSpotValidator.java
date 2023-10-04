package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Map;

public class EmptyLandingSpotValidator implements Validator{

    @Override
    public boolean isValid(ConfigurationParameters configurationParameters, Map map) {
        return map.isEmpty(configurationParameters.spaceshipLandingPoint());
    }
}
