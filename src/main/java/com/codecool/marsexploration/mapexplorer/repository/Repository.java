package com.codecool.marsexploration.mapexplorer.repository;

public interface Repository {
    void createTableIfDoesNotExist();
    void saveInDatabase();

}
