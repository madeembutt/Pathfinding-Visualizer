package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SearcherFileReader {

    private int gridRows;
    private int gridCols;

    private boolean startCellExists;
    private boolean goalCellExists;

    private int[][] colors;

    public SearcherFileReader(int gridRows, int gridCols, boolean startCellExists, boolean goalCellExists,
                              int[][] colors) {
        this.gridRows = gridRows;
        this.gridCols = gridCols;

        this.startCellExists = startCellExists;
        this.goalCellExists = goalCellExists;

        this.colors = colors;
    }

    public static SearcherFileReader readFile(File file) {
        if (file == null)
            return null;

        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert scan != null;

        int gridRows = scan.nextInt();
        int gridCols = scan.nextInt();

        if (gridRows != gridCols)
            return null;

        boolean startCellExists = scan.nextBoolean();
        boolean goalCellExists = scan.nextBoolean();

        int[][] colors = new int[gridRows][gridCols];
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                colors[i][j] = scan.nextInt();
            }
        }

        scan.close();

        return new SearcherFileReader(gridRows, gridCols, startCellExists, goalCellExists, colors);
    }

    public int getGridRows() {
        return gridRows;
    }

    public int getGridCols() {
        return gridCols;
    }

    public boolean getStartCellExists() {
        return startCellExists;
    }

    public boolean getGoalCellExists() {
        return goalCellExists;
    }

    public int[][] getColors() {
        return colors;
    }

}
