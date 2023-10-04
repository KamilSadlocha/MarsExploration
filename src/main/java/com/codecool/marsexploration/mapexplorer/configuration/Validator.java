package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Map;

public interface Validator {
    boolean isValid(ConfigurationParameters configurationParameters, Map map);
}
