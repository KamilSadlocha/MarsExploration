package com.codecool.marsexploration.mapexplorer.repository;

import com.codecool.marsexploration.mapexplorer.commandCenter.CommandCenter;

import java.sql.*;

public class ColonizationRepository implements Repository {

    private static final String CREATE_TABLE_COMMAND = """
            CREATE TABLE Colonizations (
            colonization_id INTEGER NOT NULL,
            total_extracted_resources INTEGER NOT NULL
            )
            """;
    private static final String INSERT_COMMAND = "INSERT INTO Colonizations (colonization_id, total_extracted_resources) VALUES (?, ?)";
    private final String databaseUrl;
    private ColonizationDto colonizationDto;

    public ColonizationRepository(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        colonizationDto = null;
    }

    public void setColonizationDto(ColonizationDto colonizationDto) {
        this.colonizationDto = colonizationDto;
    }

    @Override
    public void createTableIfDoesNotExist() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            if (!tableExists(connection, "Colonizations")) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(CREATE_TABLE_COMMAND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveInDatabase() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMAND);
            int totalResources = 0;
            for (CommandCenter center : colonizationDto.commandCenters()) {
                totalResources += center.getTotalMineralsCollected();
            }
            preparedStatement.setString(1, String.valueOf(colonizationDto.colonizationId()));
            preparedStatement.setInt(2, totalResources);
            preparedStatement.executeUpdate();
            System.out.println("Colonization data added to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
        return resultSet.next();
    }

}
