package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Map;

import java.util.Set;

public class ConfigurationValidator {
    private final Map map;
    private final Set<Validator> validators;

    public ConfigurationValidator(Map map, Set<Validator> validators) {
        this.map = map;
        this.validators = validators;
    }

    public boolean validate(ConfigurationParameters configurationParameters) {
        return validators.stream().allMatch(validator -> validator.isValid(configurationParameters, map));
    }
}
