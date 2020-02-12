import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<Cell> vertices = new HashSet<>();

    public Set<Cell> getVertices() {
        return vertices;
    }

    public void setVertices(Set<Cell> vertices) {
        this.vertices = vertices;
    }

    public Set<Cell> findVertices(Maze maze){
        Set<Cell> unvisitedVertices = new HashSet<>();
        Cell[][] mazeGrid = maze.getMazeGrid();

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

    public void findDistance(Cell initialNode, Set<Cell> vertices){
        for (Cell vert: vertices){
            double cost = (vert.getRow() - initialNode.getRow()) + (vert.getCol() - initialNode.getCol());
            vert.setPathValue(cost);
        }
    }

    private int checkWalls(Cell cell) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            if (!cell.walls[i])
                result++;
        }
        return result;
    }
}
