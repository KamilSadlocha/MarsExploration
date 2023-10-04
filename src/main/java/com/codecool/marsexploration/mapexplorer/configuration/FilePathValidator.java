package com.codecool.marsexploration.mapexplorer.configuration;

import com.codecool.marsexploration.mapexplorer.maploader.model.Map;

import java.io.File;

public class FilePathValidator implements Validator{
    @Override
    public boolean isValid(ConfigurationParameters configurationParameters, Map map) {
        File file = new File(configurationParameters.mapPath());
        return file.isFile();
    }
}
