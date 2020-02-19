import java.util.*;
import java.util.Map.Entry;

public class MazeSolver{

    private Set<Cell> visitedNodes = new HashSet<>();
    private Map<Cell, Map<Cell, Integer>> dijkstraTable = new HashMap<>();
    private Maze maze;
    private Graph graph;
    private Set<Cell> unvisitedNodes;
    private Cell initialNode;
    private Cell exitNode;


    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.graph = new Graph(maze);
        this.initialNode = maze.getMazeGrid()[0][0];  // pick entry node
        this.unvisitedNodes = graph.findAllVertices();
        this.exitNode = maze.getMazeGrid()[maze.getMazeGrid().length - 1][maze.getMazeGrid()[0].length - 1];  // pick exit node
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

        displayShortestPath(dijkstraTable);
    }

    private void markPath(List<Cell> pathList) {
        for (int i = 0; i < pathList.size() - 1; i++) {
            List<Cell> pathCells = pathList.get(i).getNodesOnPath().get(pathList.get(i + 1));
            for (Cell cell : pathCells){
                cell.setOnPath(true);
            }
        }
    }

    private void displayShortestPath(Map<Cell, Map<Cell, Integer>> dijkstraTable) {
        List<Cell> pathList = new ArrayList<>();
        System.out.println("The shortest path is: ");
        Cell currentNode = exitNode;
        pathList.add(currentNode);
        while(currentNode != initialNode){
            Cell nextNode = dijkstraTable.get(currentNode).entrySet().iterator().next().getKey();
            pathList.add(nextNode);
            currentNode = nextNode;
        }
        Collections.reverse(pathList);
        System.out.print(pathList.get(0));
        for (int i = 1; i < pathList.size(); i++){
            System.out.print(" --> " + pathList.get(i));
        }
        System.out.println();
        markPath(pathList);
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
}
