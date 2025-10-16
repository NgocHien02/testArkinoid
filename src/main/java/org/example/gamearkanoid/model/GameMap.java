package org.example.gamearkanoid.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int[][] layout;
    private int mapWidth;
    private int mapHeight;

    public void loadMap(String mapPath) {
        try (InputStream is = getClass().getResourceAsStream(mapPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            List<List<Integer>> mapData = new ArrayList<>();
            String line;
            int maxRowLength = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                List<Integer> row = new ArrayList<>();
                String[] brickTypes = line.trim().split("\\s+");
                for (String typeStr : brickTypes) {
                    row.add(Integer.parseInt(typeStr));
                }
                mapData.add(row);

                if (row.size() > maxRowLength) {
                    maxRowLength = row.size();
                }
            }

            mapHeight = mapData.size();
            mapWidth = maxRowLength;
            layout = new int[mapHeight][mapWidth];

            for (int i = 0;i < mapHeight; i++) {
                List<Integer> row = mapData.get(i);
                for (int j = 0; j < row.size(); j++) {
                    layout[i][j] = row.get(j);
                }
            }
        } catch (Exception e) {
            System.err.printf("Error loading map from path:" + mapPath);
            e.printStackTrace();
            layout = new int[0][0];
        }
    }

    public GameMap(String mapPath) {
        loadMap(mapPath);
    }

    public int[][] getLayout() {
        return layout;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}
