package com.codecool.marsexploration.mapexplorer.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerImpl implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/exploration_log.txt", true))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void clearFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/exploration_log.txt"))) {
            writer.write("");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
