package search.grid;

import java.awt.*;
import java.util.ArrayList;

/**
 * A <code>Grid</code> represents an NxN matrix full of <code>Cells</code>. A <code>Grid</code> creates <code>Cells</code>
 * that can be manipulated by a <code>Searcher</code>
 */
public class Grid {

    /**
     * Preferred size of the <code>Grid</code>. Used to calculate the <code>width</code> and <code>height</code>
     * of a <code>JFrame</code>
     */
    public static final int GRID_SIZE = 900;

    /**
     * Initial number of <code>Grid</code> rows.
     * This must be equal to {@link #INIT_GRID_COLS}
     */
    public static final int INIT_GRID_ROWS = 25;

    /**
     * Initial number of <code>Grid</code> columns.
     * This must be equal to {@link #INIT_GRID_ROWS}
     */
    public static final int INIT_GRID_COLS = 25;

    /**
     * Minimum number of <code>Grid</code> rows/cols
     */
    public static final int MIN_GRID_ROWS = 5;

    /**
     * Maximum number of <code>Grid</code> rows/cols
     */
    public static final int MAX_GRID_ROWS = 100;

    /**
     * Enum used to show which corner a <code>Cell</code> is in
     */
    public enum Corner {
        TOP_LEFT,
        TOP_RIGHT,
        BOT_LEFT,
        BOT_RIGHT,
        NONE
    }

    /**
     * Enum used to show which wall a <code>Cell</code> is in
     */
    public enum Wall {
        TOP,
        BOT,
        LEFT,
        RIGHT,
        NONE
    }

    /**
     * 2D-Array of Cells representing a <code>Grid</code>
     */
    private Cell[][] grid;

    /**
     * Number of rows in this <code>Grid</code>
     */
    private int rows;

    /**
     * Number of columns in this <code>Grid</code>
     */
    private int cols;

    /**
     * Width of a cell
     */
    private int width;

    /**
     * Height of each individual cell
     */
    private int height;

    /**
     * Goal cell. Used by a <code>Searcher</code> which lets it know when to stop searching.
     */
    private Cell goal;

    /**
     * Start cell. Used by a <code>Searcher</code> which lets it know which cell to start in.
     */
    private Cell start;

    /**
     * The color that is selected when building a map
     */
    private Color selectedCellColor;

    private boolean startCellExists;
    private boolean goalCellExists;

    public Grid(int rows, int cols, int[][] colors, boolean createGraph) {
        grid = new Cell[rows][cols];

        this.rows = rows;
        this.cols = cols;

        width = GRID_SIZE / rows;
        height = GRID_SIZE / cols;

        selectedCellColor = Cell.UNSEARCHABLE_CELL_COLOR;

        createCells(colors);

        if (createGraph)
            setCellNeighbors();
    }

    private void createCells(int[][] colors) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Color c = (colors == null) ? Cell.CLEAR_CELL_COLOR : new Color(colors[i][j], true);
                String name = ((i * rows) + j) + "";

                grid[i][j] = new Cell(name, j * width, i * height, width, height, c);
                grid[i][j].setCorner(getCorner(i, j));
                grid[i][j].setWall(getWall(i, j));

                if (grid[i][j].isStart())
                    start = grid[i][j];
                else if (grid[i][j].isGoal())
                    goal = grid[i][j];
            }
        }
    }

    private void setCellNeighbors() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = getCell(i, j);

                if (cell.isSearchable()) {
                    if (isCorner(i, j))
                        setCornerCellNeighbors(i, j);
                    else if (cell.getWall() != Wall.NONE)
                        setWallCellNeighbors(i, j);
                    else if (cell.isInnerCell())
                        setInnerCellNeighbors(i, j);
                }
            }
        }
    }

    private Corner getCorner(int row, int col) {
        if (row == 0 && col == 0)
            return Corner.TOP_LEFT;

        if (row == 0 && col == cols - 1)
            return Corner.TOP_RIGHT;

        if (row == rows - 1 && col == 0)
            return Corner.BOT_LEFT;

        if (row == rows - 1 && col == cols - 1)
            return Corner.BOT_RIGHT;

        return Corner.NONE;
    }

    private boolean isCorner(int row, int col) {
        return getCorner(row, col) != Corner.NONE;
    }

    private Wall getWall(int row, int col) {
        if (isCorner(row, col))
            return Wall.NONE;

        if (row == 0)
            return Wall.TOP;

        if (row == rows - 1)
            return Wall.BOT;

        if (col == 0)
            return Wall.LEFT;

        if (col == cols - 1)
            return Wall.RIGHT;

        return Wall.NONE;
    }

    private Cell getTopCell(int row, int col) {
        int topRow = row - 1;
        if ((topRow >= rows || topRow < 0) || (col >= cols | col < 0))
            return null;

        return getCell(topRow, col);
    }

    private Cell getBotCell(int row, int col) {
        int botRow = row + 1;
        if ((botRow >= rows || botRow < 0) || (col >= cols | col < 0))
            return null;

        return getCell(botRow, col);
    }

    private Cell getRightCell(int row, int col) {
        int rightCol = col + 1;
        if ((row >= rows || row < 0) || (rightCol >= cols | rightCol < 0))
            return null;

        return getCell(row, rightCol);
    }

    private Cell getLeftCell(int row, int col) {
        int leftCol = col - 1;
        if ((row >= rows || row < 0) || (leftCol >= cols | leftCol < 0))
            return null;

        return getCell(row, leftCol);
    }

    private void setCornerCellNeighbors(int i, int j) {
        ArrayList<Cell> neighbors = new ArrayList<>(Cell.NUM_CORNER_CELL_NEIGHBORS);
        Corner corner = getCell(i, j).getCorner();

        Cell top = getTopCell(i, j);
        Cell bot = getBotCell(i, j);
        Cell right = getRightCell(i, j);
        Cell left = getLeftCell(i, j);

        boolean addTop = top != null && top.isSearchable();
        boolean addBot = bot != null && bot.isSearchable();
        boolean addRight = right != null && right.isSearchable();
        boolean addLeft = left != null && left.isSearchable();

        if (corner == Corner.TOP_LEFT) {
            if (addBot)
                neighbors.add(bot);

            if (addRight)
                neighbors.add(right);
        } else if (corner == Corner.TOP_RIGHT) {
            if (addBot)
                neighbors.add(bot);

            if (addLeft)
                neighbors.add(left);
        } else if (corner == Corner.BOT_LEFT) {
            if (addTop)
                neighbors.add(top);

            if (addRight)
                neighbors.add(right);
        } else if (corner == Corner.BOT_RIGHT) {
            if (addTop)
                neighbors.add(top);

            if (addLeft)
                neighbors.add(left);
        }

        getCell(i, j).setNeighbors(neighbors);
    }

    private void setWallCellNeighbors(int i, int j) {
        ArrayList<Cell> neighbors = new ArrayList<>(Cell.NUM_WALL_CELL_NEIGHBORS);
        Wall wall = getCell(i, j).getWall();

        Cell top = getTopCell(i, j);
        Cell bot = getBotCell(i, j);
        Cell right = getRightCell(i, j);
        Cell left = getLeftCell(i, j);

        boolean addTop = top != null && top.isSearchable();
        boolean addBot = bot != null && bot.isSearchable();
        boolean addRight = right != null && right.isSearchable();
        boolean addLeft = left != null && left.isSearchable();

        if (wall == Wall.TOP) {
            if (addBot)
                neighbors.add(bot);

            if (addLeft)
                neighbors.add(left);

            if (addRight)
                neighbors.add(right);
        } else if (wall == Wall.BOT) {
            if (addTop)
                neighbors.add(top);

            if (addLeft)
                neighbors.add(left);

            if (addRight)
                neighbors.add(right);
        } else if (wall == Wall.RIGHT) {
            if (addTop)
                neighbors.add(top);

            if (addBot)
                neighbors.add(bot);

            if (addLeft)
                neighbors.add(left);
        } else if (wall == Wall.LEFT) {
            if (addTop)
                neighbors.add(top);

            if (addBot)
                neighbors.add(bot);

            if (addRight)
                neighbors.add(right);
        }

        getCell(i, j).setNeighbors(neighbors);
    }

    private void setInnerCellNeighbors(int i, int j) {
        ArrayList<Cell> neighbors = new ArrayList<>(Cell.NUM_INNER_CELL_NEIGHBORS);

        Cell top = getTopCell(i, j);
        Cell bot = getBotCell(i, j);
        Cell right = getRightCell(i, j);
        Cell left = getLeftCell(i, j);

        boolean addTop = top != null && top.isSearchable();
        boolean addBot = bot != null && bot.isSearchable();
        boolean addRight = right != null && right.isSearchable();
        boolean addLeft = left != null && left.isSearchable();

        if (addTop)
            neighbors.add(top);

        if (addBot)
            neighbors.add(bot);

        if (addRight)
            neighbors.add(right);

        if (addLeft)
            neighbors.add(left);

        getCell(i, j).setNeighbors(neighbors);
    }

    public void render(Graphics2D g2d) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].render(g2d);
            }
        }
    }

    public void clear() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].setColor(Cell.CLEAR_CELL_COLOR);
            }
        }

        startCellExists = false;
        goalCellExists = false;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public Cell getStartCell() {
        return start;
    }

    public Cell getGoalCell() {
        return goal;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getSelectedCellColor() {
        return selectedCellColor;
    }

    public void setSelectedCellColor(Color selectedCellColor) {
        this.selectedCellColor = selectedCellColor;
    }

    public boolean getStartCellExists() {
        return startCellExists;
    }

    public void setStartCellExists(boolean startCellExists) {
        this.startCellExists = startCellExists;
    }

    public boolean getGoalCellExists() {
        return goalCellExists;
    }

    public void setGoalCellExists(boolean goalCellExists) {
        this.goalCellExists = goalCellExists;
    }

}
