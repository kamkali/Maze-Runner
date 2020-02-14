import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// DONE: implement Breadth-First Search algorithm to find relationships with known Vertices
// TODO: Find relationships with Verticies
// TODO: Find path costs

public class Graph {
    private Set<Cell> vertices = new HashSet<>();

    private Cell[][] mazeGrid;
    private Maze maze;

    public Graph(Maze maze) {
        this.maze = maze;
        this.mazeGrid = maze.getMazeGrid();
    }

    public void bfs(Cell startingNode) throws InterruptedException {
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visitedNodes = new HashSet<>();

        queue.add(startingNode);

        while (!queue.isEmpty()){
            Cell currentNode = queue.poll();

            if (!visitedNodes.contains(currentNode)){
                visitedNodes.add(currentNode);
                currentNode.setOnPath(true);
//                TimeUnit.MILLISECONDS.sleep(500);
//                maze.display();
//                System.out.println();
            }
            for (Cell adjacent: getPathNeighbors(currentNode)){
                if (!visitedNodes.contains(adjacent)){
                    queue.add(adjacent);
                }
            }
        }
    }

    private Set<Cell> getPathNeighbors(Cell currentNode) {

        for (Cell[] vec: mazeGrid){
            for (Cell el: vec){
                el.setVisited(false);
            }
        }

        Set<Cell> adjacentCells = new HashSet<>();
        Set<Cell> unvisitedNeighboringNodes = new HashSet<>();
        Cell nextNode = currentNode.checkNeighbors(this.mazeGrid, currentNode.getRow(), currentNode.getCol());

        while (nextNode != null) {
            nextNode.setVisited(true);
            unvisitedNeighboringNodes.add(nextNode);
            nextNode = currentNode.checkNeighbors(this.mazeGrid, currentNode.getRow(), currentNode.getCol());
        }

        for (Cell cell : unvisitedNeighboringNodes) {
            // 4 cases
            // 1) current node below
            if (currentNode.getCol() > cell.getCol() &&
                    (!currentNode.getWalls()[Cell.Wall.TOP.getWall()] && !cell.getWalls()[Cell.Wall.BOTTOM.getWall()])) {
                    adjacentCells.add(cell);
                }
            // 2) current node above
            if (currentNode.getCol() < cell.getCol() &&
                    (!currentNode.getWalls()[Cell.Wall.BOTTOM.getWall()] && !cell.getWalls()[Cell.Wall.TOP.getWall()])) {
                    adjacentCells.add(cell);
                }
            // 3) current node on right side
            if (currentNode.getRow() > cell.getRow() &&
                    (!currentNode.getWalls()[Cell.Wall.LEFT.getWall()] && !cell.getWalls()[Cell.Wall.RIGHT.getWall()])) {
                    adjacentCells.add(cell);
                }
            // 3) current node on left side
            if (currentNode.getRow() < cell.getRow() &&
                    (!currentNode.getWalls()[Cell.Wall.RIGHT.getWall()] && !cell.getWalls()[Cell.Wall.LEFT.getWall()])) {
                    adjacentCells.add(cell);
                }
        }
        return adjacentCells;
    }

    public Set<Cell> findVertices(Maze maze){
        Set<Cell> unvisitedVertices = new HashSet<>();

        // add initial node
        unvisitedVertices.add(mazeGrid[0][0]);
        // add exit node
        unvisitedVertices.add(mazeGrid[mazeGrid.length - 1][mazeGrid[0].length - 1]);

        /* for every cell check if it is a vertex --> if it has 3 ways that means
           that it is a road intersection; if only one way to go, this is a dead-end
         */
        for (Cell[] vec: mazeGrid){
            for (Cell el: vec){
                if (checkWalls(el) != 2){
                    unvisitedVertices.add(el);
                }
            }
        }
        return unvisitedVertices;
    }

    // fix it
    public void findDistance(Cell initialNode, Set<Cell> vertices){
        for (Cell vert: vertices){
            int cost = (vert.getRow() - initialNode.getRow()) + (vert.getCol() - initialNode.getCol());
            vert.setPathValue(cost);
        }
    }

    private int checkWalls(Cell cell) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            if (!cell.getWalls()[i])
                result++;
        }
        return result;
    }
}
