import java.util.*;
import java.util.concurrent.TimeUnit;


public class Graph {
    private  Maze maze;
    private Map<Cell, Map<Cell, Integer>> nodesRelation = new HashMap<>();

    public Graph(Maze maze) {
        this.maze = maze;
    }

    private void bfs(Cell startingNode) throws InterruptedException {
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visitedNodes = new HashSet<>();
        Set<Cell> vertices = findAllVertices();
        int pathCost = 0;
        Stack<Cell> neighboringPaths;
        Deque<Cell> savedPaths = new ArrayDeque<>();

        neighboringPaths = getPathNeighbors(startingNode);
        for (int i = 0; i < neighboringPaths.size(); i++) {
            Cell savedCell = neighboringPaths.pop();
            savedCell.setSavedNode(true);
            savedPaths.push(savedCell);
        }


        queue.add(startingNode);

        while (!queue.isEmpty()){
            Cell currentNode = queue.poll();

            if (!visitedNodes.contains(currentNode)){
                visitedNodes.add(currentNode);
                currentNode.setOnPath(true);

//                TimeUnit.MILLISECONDS.sleep(750);
//                maze.display();
//                System.out.println();

                if (vertices.contains(currentNode) && startingNode != currentNode){
                    startingNode.getNeighboringNodes().put(currentNode, pathCost);
                    nodesRelation.put(startingNode, startingNode.getNeighboringNodes());
                    pathCost = 0;
                    currentNode.setVisitedVertex(true);
                }
            }
            pathCost++;

            Stack<Cell> neighbors = getPathNeighbors(currentNode);
            for (Cell adjacent : neighbors) {
                if (!visitedNodes.contains(adjacent) && !adjacent.isVisitedVertex() && !adjacent.isSavedNode()) {
                    queue.add(adjacent);
                }
            }
            if (queue.isEmpty() && !savedPaths.isEmpty()){
                Cell nextPathCell = savedPaths.pop();
                nextPathCell.setSavedNode(false);
                queue.add(nextPathCell);
            }
        }
    }


    public void findVerticesRelation() throws InterruptedException {
        Set<Cell> vertices = findAllVertices();

        for (Cell vertex: vertices){
            Cell.clearPathMarkers(maze);
            Cell.clearVisitedMarkers(maze);
            Cell.clearVisitedVertices(maze);
            bfs(vertex);
        }
    }


    public Stack<Cell> getPathNeighbors(Cell currentNode) {
        Stack<Cell> adjacentCells = new Stack<>();
        if (currentNode.isVisitedVertex())
            return adjacentCells;

        Cell.clearVisitedMarkers(maze);

        Set<Cell> unvisitedNeighboringNodes = new HashSet<>();
        Cell nextNode = currentNode.checkNeighbors(this.maze.getMazeGrid(), currentNode.getRow(), currentNode.getCol());

        while (nextNode != null) {
            nextNode.setVisited(true);
            unvisitedNeighboringNodes.add(nextNode);
            nextNode = currentNode.checkNeighbors(this.maze.getMazeGrid(), currentNode.getRow(), currentNode.getCol());
        }

        for (Cell cell : unvisitedNeighboringNodes) {
            // 4 cases
            // 1) current node below
            if (currentNode.getCol() > cell.getCol() &&
                    (!currentNode.getWalls()[Cell.Wall.TOP.getWall()] && !cell.getWalls()[Cell.Wall.BOTTOM.getWall()])) {
                adjacentCells.push(cell);
            }
            // 2) current node above
            if (currentNode.getCol() < cell.getCol() &&
                    (!currentNode.getWalls()[Cell.Wall.BOTTOM.getWall()] && !cell.getWalls()[Cell.Wall.TOP.getWall()])) {
                adjacentCells.push(cell);
            }
            // 3) current node on right side
            if (currentNode.getRow() > cell.getRow() &&
                    (!currentNode.getWalls()[Cell.Wall.LEFT.getWall()] && !cell.getWalls()[Cell.Wall.RIGHT.getWall()])) {
                adjacentCells.push(cell);
            }
            // 4) current node on left side
            if (currentNode.getRow() < cell.getRow() &&
                    (!currentNode.getWalls()[Cell.Wall.RIGHT.getWall()] && !cell.getWalls()[Cell.Wall.LEFT.getWall()])) {
                adjacentCells.push(cell);
            }
        }
        return adjacentCells;
    }

    public Set<Cell> findAllVertices(){
        Set<Cell> unvisitedVertices = new HashSet<>();

        // add initial node
        unvisitedVertices.add(maze.getMazeGrid()[0][0]);
        // add exit node
        unvisitedVertices.add(maze.getMazeGrid()[maze.getMazeGrid().length - 1][maze.getMazeGrid()[0].length - 1]);

        /* for every cell check if it is a vertex --> if it has 3 ways that means
           that it is a road intersection; if only one way to go, this is a dead-end
         */
        for (Cell[] vec: maze.getMazeGrid()){
            for (Cell el: vec){
                if (checkWalls(el) != 2){
                    unvisitedVertices.add(el);
                }
            }
        }
        return unvisitedVertices;
    }

    private int checkWalls(Cell cell) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            if (!cell.getWalls()[i])
                result++;
        }
        return result;
    }

    public Map<Cell, Map<Cell, Integer>> getNodesRelation() {
        return nodesRelation;
    }
}
