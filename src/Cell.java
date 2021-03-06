import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Cell {
    private int row;
    private int col;
    private boolean[] walls;
    private boolean visited;
    private boolean visitedVertex;
    // previous cell with shortest distance
    private Map<Cell, Integer> pathMap = new HashMap<>();
    private boolean onPath;
    private boolean savedNode;
    private Map<Cell, Integer> neighboringNodes = new HashMap<>();
    private Map<Cell,List<Cell>> nodesOnPath = new HashMap<>();

    public Map<Cell, List<Cell>> getNodesOnPath() {
        return nodesOnPath;
    }

    public boolean isSavedNode() {
        return savedNode;
    }

    public void setSavedNode(boolean savedNode) {
        this.savedNode = savedNode;
    }

    public boolean isVisitedVertex() {
        return visitedVertex;
    }

    public void setVisitedVertex(boolean visitedVertex) {
        this.visitedVertex = visitedVertex;
    }

    public Map<Cell, Integer> getNeighboringNodes() {
        return neighboringNodes;
    }

    public boolean isOnPath() {
        return onPath;
    }

    public void setOnPath(boolean onPath) {
        this.onPath = onPath;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public Map<Cell, Integer> getPathMap() {
        return pathMap;
    }


    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        /*
        Simple cell view:
                top
                ---
            left| |right
                ---
                bottom
         */
        // clock-wise order
        this.walls = new boolean[]{true, true, true, true}; // means that at the beginning every 'wall' is set
        this.visited = false;
        this.visitedVertex = false;
    }

    public enum Wall{
        TOP(0),
        RIGHT(1),
        BOTTOM(2),
        LEFT(3);

        private final int wall;
        Wall(int wall) {
            this.wall = wall;
        }

        public int getWall() {
            return wall;
        }
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean checkIndex(Cell[][] grid, int row, int col) {
        return !(row < 0 || row > grid.length - 1 || col < 0 || col > grid[0].length - 1);
    }

    public Cell checkNeighbors(Cell[][] grid, int row, int col){
        List<Cell> unvisitedNeighbors = new ArrayList<>();
        Cell top;
        Cell right;
        Cell bottom;
        Cell left;


        if (checkIndex(grid, row - 1, col)) {
            if (!grid[row - 1][col].isVisited()) {
                top = grid[row - 1][col];
                unvisitedNeighbors.add(top);
            }
        }
        if (checkIndex(grid, row, col + 1)) {
            if (!grid[row][col + 1].isVisited()) {
                right = grid[row][col + 1];
                unvisitedNeighbors.add(right);
            }
        }
        if (checkIndex(grid, row + 1, col)) {
            if (!grid[row + 1][col].isVisited()) {
                bottom = grid[row + 1][col];
                unvisitedNeighbors.add(bottom);
            }
        }
        if (checkIndex(grid, row, col - 1)) {
            if (!grid[row][col - 1].isVisited()) {
                left = grid[row][col - 1];
                unvisitedNeighbors.add(left);
            }
        }

        if (!unvisitedNeighbors.isEmpty()){
            int randomNum = ThreadLocalRandom.current().nextInt(0, unvisitedNeighbors.size());
            return unvisitedNeighbors.get(randomNum);
        } else {
            return null;
        }
    }

    public static void clearVisitedVertices(Maze maze) {
        for (Cell[] vec : maze.getMazeGrid()) {
            for (Cell el : vec) {
                el.setVisitedVertex(false);
            }
        }
    }

    public static void clearPathMarkers(Maze maze) {
        for (Cell[] vec : maze.getMazeGrid()) {
            for (Cell el : vec) {
                el.setOnPath(false);
            }
        }
    }


    public static void clearVisitedMarkers(Maze maze) {
        for (Cell[] vec : maze.getMazeGrid()) {
            for (Cell el : vec) {
                el.setVisited(false);
            }
        }
    }

    @Override
    public String toString() {
        return "{Cell(" + col + ","+ row +
//                "), walls=" + Arrays.toString(walls) +
//                " , cost=" + pathValue +
                '}';
    }
}
