package search.pathfinder.heuristic;

import search.grid.Cell;
import search.grid.Grid;

import java.awt.*;
import java.util.ArrayList;

public class GreedyBestFirstSearcher extends HeuristicSearcher {

    private ArrayList<Cell> open;
    private ArrayList<Cell> closed;

    public GreedyBestFirstSearcher(Grid grid, boolean showMultipleColors) {
        super(grid, showMultipleColors);

        open = new ArrayList<>();
        closed = new ArrayList<>();
    }

    @Override
    public Cell search() {
        Cell goal = grid.getGoalCell();
        goalRow = goal.getY() / grid.getHeight();
        goalCol = goal.getX() / grid.getWidth();

        calculateHeuristics();
        open.add(grid.getStartCell());

        Color first = new Color(245, 11, 255);
        Color second = new Color(80, 242, 255);
        Color third = new Color(255, 144, 4);
        Color fourth = new Color(255, 61, 146);
        Color fifth = new Color(159, 2, 255);
        Color[] seenColors = {first, second, third, fourth, fifth};

        while (!open.isEmpty()) {
            Cell min = lowestFCost();

            if (min.isGoal())
                return min;

            open.remove(min);
            min.setAlongPath(true);

            if (!min.isStart() && !min.isGoal())
                min.setColor(new Color(157, 255, 26));

            closed.add(min);

            ArrayList<Cell> neighbors = min.getNeighbors();
            for (int i = 0; i < neighbors.size(); i++) {
                try {
                    Thread.sleep(SEARCHER_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Cell n = neighbors.get(i);

                int closedIndex = closed.indexOf(n);
                if (closedIndex >= 0) {
                    if (showMultipleColors) {
                        if (!n.isStart() && !n.isGoal()) {
                            if (n.getColor().equals(seenColors[n.getReachedCount()]))
                                n.setReachedCount(n.getReachedCount() + 1);

                            n.setColor(seenColors[n.getReachedCount()]);
                        }
                    }

                    continue;
                }


                if (n.equals(min.getParent()))
                    continue;

                if (!n.isAlongPath() && (!n.isStart() && !n.isGoal()))
                    n.setColor(Color.YELLOW);

                n.setParent(min);
                n.setFCost(n.getHCost());

                int openIndex = open.indexOf(n);
                if (openIndex >= 0) {
                    Cell c = open.get(openIndex);

                    if (c.getFCost() <= n.getFCost())
                        continue;
                }

                closedIndex = closed.indexOf(n);
                if (closedIndex >= 0) {
                    Cell c = closed.get(closedIndex);

                    if (c.getFCost() <= n.getFCost())
                        continue;
                }

                if (openIndex >= 0)
                    open.remove(openIndex);

                if (closedIndex >= 0)
                    closed.remove(closedIndex);

                open.add(n);


            }
        }

        return null;
    }


    /**
     * @see <a href="http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7">Heuristic functions
     * pulled from here</a>
     */
    protected void calculateHeuristics() {
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                Cell c = grid.getCell(i, j);

                double h = diagonalDistance(i, j);
                c.setHCost(h);
            }
        }
    }

    protected Cell lowestFCost() {
        double min = 0;
        Cell minCell = null;

        for (int i = 0; i < open.size(); i++) {
            Cell curCell = open.get(i);
            double curFCost = curCell.getFCost();

            if (i == 0 || min > curFCost) {
                min = curFCost;
                minCell = curCell;
            }
        }

        return minCell;
    }

}
