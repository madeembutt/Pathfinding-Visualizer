package search.pathfinder;

import search.grid.Cell;
import search.grid.Grid;

import java.awt.*;

public abstract class Searcher {

    public static final int SEARCHER_DELAY = 5;
    public static final int PATH_TRACER_DELAY = 5;

    protected Grid grid;

    protected boolean showMultipleColors;
    protected boolean done;

    public Searcher(Grid grid, boolean showMultipleColors) {
        this.grid = grid;
        this.showMultipleColors = showMultipleColors;
    }

    /**
     * The searching algorithm used
     * ex. A*, DFS, Best-first, Breadth-first, etc
     *
     * @return the goal cell if path is found, otherwise null
     */
    public abstract Cell search();

    /**
     * Returns the path starting at Cell <code>c</code> by backtracking using the parent cells
     *
     * @param c cell to find path from by backtracking to starting cell
     * @return the path as a string
     */
    public String[] path(Cell c) {
        Cell curr = c;
        int cost = 0;
        StringBuilder path = new StringBuilder();

        while (curr != null) {
            if (!curr.isStart() && !curr.isGoal())
                curr.setColor(Color.GREEN);

            try {
                Thread.sleep(PATH_TRACER_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            path.append(curr.getName());
            curr = curr.getParent();

            if (curr != null) {
                path.append(" <- ");
                cost++;
            }
        }

        System.out.println(cost);
        done = true;
        return new String[]{path.toString(), "" + cost};
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public boolean isDone() {
        return done;
    }

}
