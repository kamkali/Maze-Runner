import java.util.*;

public class MazeSolver{
    private Cell[][] mazeGrid;

    // step 0
    private Set<Cell> visitedNodes = new HashSet<>();
    private Set<Cell> unvisitedNodes = new HashSet<>();

    public MazeSolver(Cell[][] mazeGrid) {
        this.mazeGrid = mazeGrid;
    }

    /**
     * Maze Solver using Djikstra algorithm
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm
     */
    public void solveMaze(){
        // step 1
        for (Cell[] vec: mazeGrid){
            for (Cell el: vec){
                el.setVisited(false);
                el.setPathValue(Double.POSITIVE_INFINITY);
                unvisitedNodes.add(el);
            }
        }

        // step 2
        Cell initialNode = mazeGrid[0][0];  // pick entry node
        Cell exitNode = mazeGrid[mazeGrid.length - 1][mazeGrid[0].length - 1];
        initialNode.setPathValue(0);    // set its path value to zero
        checkNeighboringNodes(initialNode);


        for (Cell node: unvisitedNodes) {
            if (!visitedNodes.contains(node))
                checkNeighboringNodes(node);
            if (exitNode.isVisited())
                break;
        }
    }

    private void checkNeighboringNodes(Cell currentNode) {
        Set<Cell> unvisitedNeighboringNodes = new HashSet<>();

        Cell nextNode = currentNode.checkNeighbors(this.mazeGrid, currentNode.getRow(), currentNode.getCol());
        while (nextNode != null){
            unvisitedNeighboringNodes.add(nextNode);
            nextNode.setVisited(true);
            nextNode = currentNode.checkNeighbors(this.mazeGrid, currentNode.getRow(), currentNode.getCol());
        }
        calculateDistance(currentNode, unvisitedNeighboringNodes);
    }

    private void calculateDistance(Cell currentNode, Set<Cell> unvisitedNeighboringNodes) {
        for (Cell node: unvisitedNeighboringNodes){
            double currentNodePathCost = currentNode.getPathValue() + node.getPathValue();
            if (currentNodePathCost < currentNode.getPathValue())
                currentNode.setPathValue(currentNodePathCost);
        }
        visitedNodes.add(currentNode);
    }
}
