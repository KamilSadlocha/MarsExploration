package com.codecool.marsexploration.mapexplorer.repository;

import com.codecool.marsexploration.mapexplorer.commandCenter.CommandCenter;

import java.sql.*;

public class CommandCenterRepository implements Repository {
    private static final String CREATE_TABLE_COMMAND = """
            CREATE TABLE Centers (
            colonization_id INTEGER NOT NULL,
            center_id INTEGER NOT NULL,
            used_resources INTEGER NOT NULL
            )
            """;
    private static final String INSERT_COMMAND = "INSERT INTO Centers (colonization_id, center_id, used_resources) VALUES (?, ?, ?)";
    private final String databaseUrl;
private CommandCenterDto commandCenterDto;
    public CommandCenterRepository(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.commandCenterDto = null;
    }

    public void setCommandCenterDto(CommandCenterDto commandCenterDto) {
        this.commandCenterDto = commandCenterDto;
    }

    @Override
    public void createTableIfDoesNotExist() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            if (!tableExists(connection, "Centers")) {
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
            for(CommandCenter commandCenter : commandCenterDto.commandCenters()){
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMAND);
                preparedStatement.setString(1, String.valueOf(commandCenterDto.colonizationId()));
                preparedStatement.setString(2, commandCenter.getId());
                preparedStatement.setInt(3, commandCenter.getCreatedRovers() * commandCenterDto.mineralsNeededForNewRover());
                preparedStatement.executeUpdate();
            }
            System.out.println("Centers data added to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
        return resultSet.next();
    }

}
