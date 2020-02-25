package search.pathfinder.heuristic;

import search.grid.Cell;
import search.grid.Grid;
import search.pathfinder.Searcher;

public abstract class HeuristicSearcher extends Searcher {

    protected int goalRow;
    protected int goalCol;

    public HeuristicSearcher(Grid grid, boolean showMultipleColors) {
        super(grid, showMultipleColors);
    }

    /**
     * @see <a href="http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7">Heuristic functions
     * pulled from here</a>
     */
    protected abstract void calculateHeuristics();
    protected abstract Cell lowestFCost();

    protected double manhattanDistance(int cellRow, int cellCol) {
        int dx = Math.abs(goalRow - cellRow);
        int dy = Math.abs(goalCol - cellCol);

        int d = 1;

        return d * (dx + dy);
    }

    protected double diagonalDistance(int cellRow, int cellCol) {
        int dx = Math.abs(goalRow - cellRow);
        int dy = Math.abs(goalCol - cellCol);

        int d = 1;
        double d2 = Math.sqrt(2);

        return d * (dx + dy) + (d2 - 2 * d) * Math.min(dx, dy);
    }

    protected double euclideanDistance(int cellRow, int cellCol) {
        int dx = Math.abs(goalRow - cellRow);
        int dy = Math.abs(goalCol - cellCol);

        int d = 1;

        return d * Math.sqrt(dx * dx + dy * dy);
    }

}
