package com.codecool.marsexploration.mapexplorer.maploader;

import com.codecool.marsexploration.mapexplorer.maploader.model.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoaderImpl implements MapLoader {
    @Override
    public Map load(String mapFile) {
        File file = new File(mapFile);

        int dimension = getMapDimension(file);

        String[][] representation = createRepresentation(file, dimension);

        return new Map(representation, true);
    }

    private static int getMapDimension(File file) {
        int length = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                length++;
                scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return length;
    }

    private static String[][] createRepresentation(File file, int dimension) {
        String[][] representation = new String[dimension][dimension];

        try (Scanner scanner = new Scanner(file)) {
            int i = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    representation[i][j] = String.valueOf(line.charAt(j));
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return representation;
    }
}
