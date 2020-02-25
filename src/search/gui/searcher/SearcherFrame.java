package search.gui.searcher;

import search.SearcherFileReader;
import search.grid.Grid;
import search.pathfinder.Searcher;

import javax.swing.*;
import java.io.File;

public class SearcherFrame extends JFrame {

    /* Padding for the frame width and frame height */
    private static final int WIDTH_PADDING = 16;
    private static final int HEIGHT_PADDING = 39;

    public SearcherFrame(String title, String filename, Searcher searcher) {
        super(title);

        SearcherFileReader fileReader = SearcherFileReader.readFile(new File("./maps/" + filename));

        int gridRows = fileReader.getGridRows();
        int gridCols = fileReader.getGridCols();

        int[][] colors = fileReader.getColors();

        int cellSize = Grid.GRID_SIZE / gridRows;
        int frameWidth = cellSize * gridCols;
        int frameHeight = cellSize * gridRows;

        Grid grid = new Grid(gridRows, gridCols, colors, true);
        searcher.setGrid(grid);
        SearcherPanel asPanel = new SearcherPanel(grid, frameWidth, frameHeight, searcher);

        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setFocusable(true);
        setLocationRelativeTo(null);
        add(asPanel);
        pack();
        setVisible(true);
    }

}
