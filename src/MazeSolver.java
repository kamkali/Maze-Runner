import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MazeSolver{

    private Set<Cell> visitedNodes = new HashSet<>();
    private Map<Cell, Map<Cell, Integer>> dijkstraTable = new HashMap<>();
    private Maze maze;
    private Graph graph;

    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.graph = new Graph(maze);
    }

    /**
     * Maze Solver using Djikstra algorithm
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm
     */
    public void solveMaze() {
        // step 1
        Cell.clearPathMarkers(maze);
        Cell.clearVisitedMarkers(maze);
        Cell.clearVisitedVertices(maze);
        Set<Cell> unvisitedNodes = graph.findAllVertices();

        // step 2
        for (Cell[] vec : maze.getMazeGrid()) {
            for (Cell cell : vec) {
                cell.getPathMap().put(cell, Integer.MAX_VALUE);
                dijkstraTable.put(cell, cell.getPathMap());
            }
        }

        Cell initialNode = maze.getMazeGrid()[0][0];  // pick entry node
        Cell exitNode = maze.getMazeGrid()[maze.getMazeGrid().length - 1][maze.getMazeGrid()[0].length - 1];  // pick exit node
        initialNode.getPathMap().replace(initialNode, 0);    // set initial node path value to zero
        dijkstraTable.replace(initialNode, initialNode.getPathMap());
        Cell currentNode = initialNode;

        // step 3
        // starting vertex
        calculateForVertices(currentNode);

        for (Cell vertex : unvisitedNodes) {
            if (visitedNodes.contains(exitNode))
                break;
            else if (!visitedNodes.contains(vertex))
                calculateForVertices(vertex);
        }
    }


    private void calculateForVertices(Cell currentNode){
        graph.findVerticesRelation();
//        Map<Cell, Map<Cell, Integer>> nodesRelation = graph.getNodesRelation();
        Map<Cell, Integer> currentNodeNeighbors = graph.getNodesRelation().get(currentNode);

    }
//
//    private void checkDistance(Cell currentNode, Set<Cell> unvisitedNeighboringNodes) {
//        for (Cell node: unvisitedNeighboringNodes){
//            node.setVisited(false);
//            int currentNodePathCost = currentNode.getPathValue() + node.getPathValue();
//            if (currentNodePathCost < currentNode.getPathValue())
//                currentNode.setPathValue(currentNodePathCost);
//        }
//        currentNode.setVisited(true);
//        visitedNodes.add(currentNode);
//    }
}
