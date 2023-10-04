package com.codecool.marsexploration.mapexplorer.repository;

import com.codecool.marsexploration.mapexplorer.rovers.Rover;

import java.sql.*;

public class RoversRepository implements Repository {
    private static final String CREATE_TABLE_COMMAND = """
            CREATE TABLE Rovers (
            colonization_id INTEGER NOT NULL,
            rover_id INTEGER NOT NULL,
            rover_resources INTEGER NOT NULL
            )
            """;
    private static final String INSERT_COMMAND = "INSERT INTO Rovers (colonization_id, rover_id, rover_resources) VALUES (?, ?, ?)";
    private final String databaseUrl;
    private RoversDto roversDto;
    public RoversRepository(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.roversDto = null;
    }

    public void setRoversDto(RoversDto roversDto) {
        this.roversDto = roversDto;
    }

    @Override
    public void createTableIfDoesNotExist() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            if (!tableExists(connection, "Rovers")) {
                Statement statement = connection.createStatement();

                statement.executeUpdate(CREATE_TABLE_COMMAND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveInDatabase() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            for(Rover rover : roversDto.rovers()){
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMAND);
                preparedStatement.setString(1, String.valueOf(roversDto.colonizationId()));
                preparedStatement.setString(2, rover.getId());
                preparedStatement.setInt(3, rover.getCollectedResources());
                preparedStatement.executeUpdate();
            }
            System.out.println("Rovers data added to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
        return resultSet.next();
    }

}
