package com.codecool.marsexploration.mapexplorer.repository;

import java.sql.*;

public class ExplorationsRepository implements Repository {
    private static final String CREATE_TABLE_COMMAND = """
            CREATE TABLE Explorations (
            id INTEGER NOT NULL UNIQUE,
            steps INTEGER NOT NULL,
            resources INTEGER NOT NULL,
            outcome TEXT NOT NULL,
            PRIMARY KEY(id AUTOINCREMENT))
            """;
    private static final String INSERT_COMMAND = "INSERT INTO Explorations (steps, resources, outcome) VALUES (?, ?, ?)";
    private final String databaseUrl;
    private ExplorationsDto explorationsDto;
    public ExplorationsRepository(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.explorationsDto = null;
    }

    public void setExplorationsDto(ExplorationsDto explorationsDto) {
        this.explorationsDto = explorationsDto;
    }

    @Override
    public void createTableIfDoesNotExist() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            if (!tableExists(connection, "Explorations")) {
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

            preparedStatement.setInt(1, explorationsDto.steps());
            preparedStatement.setInt(2, explorationsDto.numberOfResources());
            preparedStatement.setString(3, String.valueOf(explorationsDto.explorationOutcome()));
            preparedStatement.executeUpdate();
            System.out.println("Exploration data added to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
        return resultSet.next();
    }
}
