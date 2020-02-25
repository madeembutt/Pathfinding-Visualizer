package search.pathfinder.heuristic;

import search.grid.Cell;
import search.grid.Grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

public class HeuristicDepthFirstSearcher extends HeuristicSearcher {

    private Stack<Cell> open;
    private ArrayList<Cell> closed;

    public HeuristicDepthFirstSearcher(Grid grid, boolean showMultipleColors) {
        super(grid, showMultipleColors);

        open = new Stack<>();
        closed = new ArrayList<>();
    }

    @Override
    public Cell search() {
        Color first = new Color(245, 11, 255);
        Color second = new Color(80, 242, 255);
        Color third = new Color(255, 144, 4);
        Color fourth = new Color(255, 61, 146);
        Color fifth = new Color(159, 2, 255);
        Color[] seenColors = {first, second, third, fourth, fifth};

        calculateHeuristics();
        open.add(grid.getStartCell());

        while (!open.isEmpty()) {
            try {
                Thread.sleep(SEARCHER_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Cell c = open.pop();

            if (c != null) {
                c.setAlongPath(true);

                if (c.isGoal())
                    return c;

                // cycle pruning
                if (closed.indexOf(c) >= 0) {
                    if (showMultipleColors) {
                        if (!c.isStart() && !c.isGoal()) {
                            if (c.getColor().equals(seenColors[c.getReachedCount()]))
                                c.setReachedCount(c.getReachedCount() + 1);

                            c.setColor(seenColors[c.getReachedCount()]);
                        }
                    }

                    continue;
                }

                closed.add(c);

                if (!c.isStart() && !c.isGoal())
                    c.setColor(new Color(157, 255, 26));

                ArrayList<Cell> neighbors = c.getNeighbors();
                neighbors.sort(new HDFSComparator());

                for (Cell n : neighbors) {
                    if (closed.indexOf(n) < 0) {
                        if (!n.isAlongPath() && (!n.isStart() && !n.isGoal()))
                            n.setColor(Color.YELLOW);

                        open.add(n);
                        n.setParent(c);
                        n.setAlongPath(true);
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected void calculateHeuristics() {
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                Cell c = grid.getCell(i, j);

                double h = diagonalDistance(i, j);
                c.setHCost(h);
            }
        }
    }

    @Override
    protected Cell lowestFCost() {
        // don't need an implementation because there's no need for the f cost

        return null;
    }

    private static class HDFSComparator implements Comparator<Cell> {

        @Override
        public int compare(Cell o1, Cell o2) {
            return (int) (o1.getHCost() - o2.getHCost());
        }

    }

}


