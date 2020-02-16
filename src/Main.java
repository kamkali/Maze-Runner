public class Main {
    public static void main(String[] args) throws InterruptedException {

        Maze myMaze = new MazeGenerator(5);
        myMaze.display();

        MazeSolver mazeSolver = new MazeSolver(myMaze.getMazeGrid());
        mazeSolver.solveMaze();
        Graph graph = new Graph(myMaze);

        graph.findVerticesRelation();

        for (Cell cell: graph.getNodesRelation().keySet()) {
            System.out.println(cell + " : " + graph.getNodesRelation().get(cell));
        }
    }
}
