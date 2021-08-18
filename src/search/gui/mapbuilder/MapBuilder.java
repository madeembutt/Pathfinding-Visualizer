package search.gui.mapbuilder;

import search.SearcherFileReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MapBuilder {

    /* Commenting to test git stuff 21230390 */
    public MapBuilder(String title, int rows, int cols, File file) {
        SearcherFileReader fileReader = SearcherFileReader.readFile(file);

        int gridRows = rows;
        int gridCols = cols;

        boolean startCellExists = false;
        boolean goalCellExists = false;

        int[][] colors = null;

        if (fileReader != null) {
            gridRows = fileReader.getGridRows();
            gridCols = fileReader.getGridCols();

            startCellExists = fileReader.getStartCellExists();
            goalCellExists = fileReader.getGoalCellExists();

            colors = fileReader.getColors();
        }

        MapBuilderGrid grid = new MapBuilderGrid(gridRows, gridCols, colors, startCellExists, goalCellExists);

        int gridWidth = grid.getWidth();
        int gridHeight = grid.getHeight();

        String fileName = (file != null) ? file.getName().substring(0, file.getName().length() - ".txt".length()) : null;
        MapBuilderLegend legend = new MapBuilderLegend(grid.getGrid(), grid, MapBuilderLegend.WIDTH, gridHeight, fileName);

        int frameWidth = gridWidth + MapBuilderLegend.WIDTH;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(new Dimension(frameWidth, gridHeight));
        mainPanel.add(grid);
        mainPanel.add(legend);

        JFrame frame = new JFrame(title);
        frame.setSize(frameWidth, gridHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.pack();
    }

}
