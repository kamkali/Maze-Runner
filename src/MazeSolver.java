import java.util.*;
import java.util.Map.Entry;

public class MazeSolver{

    private Set<Cell> visitedNodes = new HashSet<>();
    private Map<Cell, Map<Cell, Integer>> dijkstraTable = new HashMap<>();
    private Maze maze;
    private Graph graph;
    private Set<Cell> unvisitedNodes;
    private Cell initialNode;


    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.graph = new Graph(maze);
        this.initialNode = maze.getMazeGrid()[0][0];  // pick entry node
        this.unvisitedNodes = graph.findAllVertices();
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

        // step 2
        for (Cell node : unvisitedNodes) {
            node.getPathMap().put(node, Integer.MAX_VALUE);
            dijkstraTable.put(node, node.getPathMap());
            }


        Cell exitNode = maze.getMazeGrid()[maze.getMazeGrid().length - 1][maze.getMazeGrid()[0].length - 1];  // pick exit node
        initialNode.getPathMap().replace(initialNode, 0);    // set initial node path value to zero
        dijkstraTable.replace(initialNode, initialNode.getPathMap());
        Cell currentNode = initialNode;

        // step 3
        // starting vertex
        calculateForVertices(currentNode);


        while(!visitedNodes.contains(exitNode)){
            Cell lowestEntry = findLowestEntry(dijkstraTable);
            if (lowestEntry != null)
                calculateForVertices(lowestEntry);
        }

        System.out.println(dijkstraTable);
    }

    private Cell findLowestEntry(Map<Cell, Map<Cell, Integer>> table){
        Entry<Cell, Integer> min = null;
        Cell nextCell = null;
        for (Cell key : table.keySet()) {
            if (visitedNodes.contains(key))
                continue;
            for(Map.Entry<Cell,Integer> entry: table.get(key).entrySet())
            {
                if (min == null || min.getValue() > entry.getValue()) {
                    min = entry;
                    nextCell = key;
                }
            }
        }
        return nextCell;
    }


    private void calculateForVertices(Cell currentNode){
        graph.findVerticesRelation();
        Map<Cell, Integer> currentNodeNeighbors = graph.getNodesRelation().get(currentNode);

        for (Entry<Cell,Integer> entry : currentNodeNeighbors.entrySet()){
            int currentNodePathCost;
            if (currentNode.equals(initialNode)){
                currentNodePathCost = dijkstraTable.get(currentNode).get(currentNode);
            } else
            {
                currentNodePathCost = dijkstraTable.get(currentNode).entrySet().stream()
                .iterator().next().getValue();
            }
            int sumOfPathCosts = currentNodePathCost + entry.getValue();
            int neighbourPathCost = dijkstraTable.get(entry.getKey()).entrySet().stream()
                    .iterator().next().getValue();

            if (sumOfPathCosts < neighbourPathCost){
                dijkstraTable.remove(entry.getKey());
                entry.getKey().getPathMap().remove(entry.getKey());
                entry.getKey().getPathMap().put(currentNode, sumOfPathCosts);
                dijkstraTable.put(entry.getKey(), entry.getKey().getPathMap());
            }
        }
        visitedNodes.add(currentNode);
        unvisitedNodes.remove(currentNode);
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
