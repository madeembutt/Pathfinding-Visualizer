package search.grid;

import java.awt.*;
import java.util.ArrayList;

/**
 * A <code>Cell</code> is a square unit on a <code>Grid</code>> that can be used by a <code>Searcher</code> to search for a path from a starting
 * <code>Cell</code> to a goal <code>Cell</code>.
 *
 * <p>A <code>Cell</code> contains many attributes such as a <code>Color</code>, <code>(x, y)</code> coordinates, neighboring cells, various costs for searching, and more</p>
 */
public class Cell {

    /**
     * Color denoting a starting <code>Cell</code>
     */
    public static final Color START_CELL_COLOR = Color.BLUE;

    /**
     * Color denoting a goal <code>Cell</code>
     */
    public static final Color GOAL_CELL_COLOR = Color.RED;

    /**
     * Color denoting a clear/searchable <code>Cell</code>
     */
    public static final Color CLEAR_CELL_COLOR = Color.WHITE;

    /**
     * Color denoting an unsearchable <code>Cell</code> (aka a wall)
     */
    public static final Color UNSEARCHABLE_CELL_COLOR = Color.BLACK;

    /**
     * Maximum number of neighbors a corner <code>Cell</code> could have
     */
    public static final int NUM_CORNER_CELL_NEIGHBORS = 2;

    /**
     * Maximum number of neighbors a wall <code>Cell</code> could have
     */
    public static final int NUM_WALL_CELL_NEIGHBORS = 3;

    /**
     * Maximum number of neighbors an inner <code>Cell</code> could have
     */
    public static final int NUM_INNER_CELL_NEIGHBORS = 4;

    /**
     * Cost of going from one <code>Cell</code> to another
     */
    public static final int COST_TO_CELL = 1;

    /**
     * Name of the <code>Cell</code>
     */
    private String name;

    /**
     * <code>ArrayList</code>> of neighbors
     */
    private ArrayList<Cell> neighbors;

    /**
     * Color of this cell
     */
    private Color color;

    /**
     * <code>x</code> position of the <code>Cell</code>
     */
    private int x;

    /**
     * <code>y</code> position of the <code>Cell</code>
     */
    private int y;

    /**
     * Width of the <code>Cell</code>
     */
    private int width;

    /**
     * Height of the <code>Cell</code>
     */
    private int height;

    /**
     * Corner this <code>Cell</code> is in
     *
     * <p>TOP_LEFT, TOP_RIGHT, BOT_LEFT, BOT_RIGHT</p>
     * <p>NONE if cell isn't in a corner</p>
     */
    private Grid.Corner corner;

    /**
     * Wall this <code>Cell</code> is a part of
     *
     * <p>TOP, BOT, RIGHT, LEFT</p>
     * <p>NONE if cell isn't a part of a wall</p>
     */
    private Grid.Wall wall;

    /**
     * Parent <code>Cell</code>
     *
     * <p>Used for tracing a path</p>
     */
    private Cell parent;

    /**
     * f-cost of the <code>Cell</code>
     *
     * <p>f = g + h</p>
     */
    private double fCost;

    /**
     * g-cost of the <code>Cell</code>
     *
     * <p>g = cost of getting to this <code>Cell</code> from the initial <code>Cell</code> when searching</p>
     */
    private double gCost;

    /**
     * h-cost of the <code>Cell</code>
     *
     * <p>h = heuristic cost. Depends on the heuristic function used</p>
     */
    private double hCost;

    /**
     * Whether or not this <code>Cell</code> is a part of the path the <code>Searcher</code> finds
     */
    private boolean alongPath;

    /**
     * Number of times this <code>Cell</code> was reached when searching
     *
     * <p>Used for changing the color of the <code>Cell</code> if the <code>Searcher</code> looks at it again</p>
     */
    private int reachedCount;

    /**
     * Constructs a new <code>Cell</code> at <code>(x, y)</code>
     *
     * @param name Name of the cell
     * @param x <code>x</code>-position of the cell
     * @param y <code>y</code>-position of the cell
     * @param width width of the cell
     * @param height height of the cell
     * @param color color of the cell
     */
    public Cell(String name, int x, int y, int width, int height, Color color) {
        this.name = name;

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.color = color;
    }

    /**
     * Renders a <code>Cell</code> using the specified graphics context
     *
     * @param g2d graphics context to render with
     */
    public void render(Graphics2D g2d) {
        // fill cell with cell's color
        g2d.setColor(color);
        g2d.fill(getCellBounds());

        // color cell border
        g2d.setColor(Color.BLACK);
        g2d.draw(getCellBounds());
    }

    /**
     * @return <code>Rectangle</code> that is the <code>Cell</code>'s bounds
     */
    public Rectangle getCellBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * @return <code>x</code>-position of the <code>Cell</code>
     */
    public int getX() {
        return x;
    }

    /**
     * @return <code>y</code>-position of the <code>Cell</code>
     */
    public int getY() {
        return y;
    }

    /**
     * @return color of the <code>Cell</code>
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the current color to a new color
     *
     * @param color new color of the <code>Cell</code>
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return name of the <code>Cell</code>
     */
    public String getName() {
        return name;
    }

    /**
     * @return f-cost of the <code>Cell</code>
     */
    public double getFCost() {
        return fCost;
    }

    /**
     * Sets the current f-cost to a new f-cost
     *
     * @param fCost new f-cost
     */
    public void setFCost(double fCost) {
        this.fCost = fCost;
    }

    /**
     * @return g cost of the <code>Cell</code>
     */
    public double getGCost() {
        return gCost;
    }

    /**
     * Sets the current g-cost to a new g-cost
     *
     * @param gCost new g-cost
     */
    public void setGCost(double gCost) {
        this.gCost = gCost;
    }

    /**
     * @return h-cost of the <code>Cell</code>
     */
    public double getHCost() {
        return hCost;
    }

    /**
     * Sets the current h-cost to a new h-cost
     *
     * @param hCost new h-cost
     */
    public void setHCost(double hCost) {
        this.hCost = hCost;
    }

    /**
     * @return parent <code>Cell</code> of this <code>Cell</code>
     */
    public Cell getParent() {
        return parent;
    }

    /**
     * Sets the current parent to a new parent
     *
     * @param parent new parent of the <code>Cell</code>
     */
    public void setParent(Cell parent) {
        this.parent = parent;
    }

    /**
     * @return <code>ArrayList</code>> of neighbors
     */
    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }

    /**
     * Sets the current neighbors to a new list of neighbors
     *
     * @param neighbors new list of neighbors
     */
    public void setNeighbors(ArrayList<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * A <code>Cell</code> is a start <code>Cell</code> if it's color is {@link #START_CELL_COLOR}
     *
     * @return whether or not this <code>Cell</code> is a start <code>Cell</code>
     */
    public boolean isStart() {
        return color.equals(START_CELL_COLOR);
    }

    /**
     * A <code>Cell</code> is a goal <code>Cell</code> if it's color is {@link #GOAL_CELL_COLOR}
     *
     * @return whether or not this <code>Cell</code> is a goal <code>Cell</code>
     */
    public boolean isGoal() {
        return color.equals(GOAL_CELL_COLOR);
    }

    /**
     * A <code>Cell</code> is a searchable <code>Cell</code> if it's color is {@link #CLEAR_CELL_COLOR} or it's a start cell or it's a goal cell
     *
     * @see #isStart()
     * @see #isGoal()
     *
     * @return whether or not this <code>Cell</code> is a searchable <code>Cell</code>
     */
    public boolean isSearchable() {
        return color.equals(CLEAR_CELL_COLOR) || isStart() || isGoal();
    }

    /**
     * A <code>Cell</code> is along a path if a <code>Searcher</code> chooses to to go to it
     *
     * @return whether or not this <code>Cell</code> is along a path a <code>Searcher</code> finds
     */
    public boolean isAlongPath() {
        return alongPath;
    }

    /**
     * Sets the current {@link #alongPath} to a new {@link #alongPath}
     *
     * @param alongPath new {@link #alongPath}
     */
    public void setAlongPath(boolean alongPath) {
        this.alongPath = alongPath;
    }

    /**
     * @return which <code>Corner</code> this <code>Cell</code> is in
     */
    public Grid.Corner getCorner() {
        return corner;
    }

    /**
     * Sets the <code>Corner</code> of this <code>Cell</code>
     *
     * @param corner new <code>Corner</code>
     */
    public void setCorner(Grid.Corner corner) {
        this.corner = corner;
    }

    /**
     * @return The <code>Wall</code> this <code>Cell</code> is a part of
     */
    public Grid.Wall getWall() {
        return wall;
    }

    /**
     * Sets the <code>Wall</code> of this <code>Cell</code>
     *
     * @param wall new <code>Wall</code>
     */
    public void setWall(Grid.Wall wall) {
        this.wall = wall;
    }

    /**
     * A <code>Cell</code> is an inner <code>Cell</code> if it is not in a corner and if it is not a part of a wall
     *
     * @return whether or not this <code>Cell</code> is an inner <code>Cell</code> or not
     */
    public boolean isInnerCell() {
        return corner == Grid.Corner.NONE && wall == Grid.Wall.NONE;
    }

    /**
     * @return number of times this <code>Cell</code> has been reached
     */
    public int getReachedCount() {
        return reachedCount;
    }

    /**
     * Sets the current {@link #reachedCount} to a new {@link #reachedCount}
     *
     * @param reachedCount new {@link #reachedCount}
     */
    public void setReachedCount(int reachedCount) {
        this.reachedCount = reachedCount;
    }

    /**
     * Checks if two <code>Cell</code> objects are equal to each others.
     * <code>Cell</code>'s are equal if their <code>(x, y)</code> coordinates
     * are equal
     *
     * @param other <code>Cell</code> object to test equality with
     *
     * @return whether or not <code>Cell other</code> is equal to <code>this</code>
     */
    public boolean equals(Object other) {
        if (other == null)
            return false;

        if (!(other instanceof Cell))
            return false;

        Cell o = (Cell) other;
        return this.x == o.x && this.y == o.y;
    }

    /**
     * Hash code of a <code>Cell</code> is equal to (x * y) + (x - y)
     *
     * @return hash code of this <code>Cell</code>
     */
    public int hashCode() {
        return (x * y) + (x - y);
    }


}
