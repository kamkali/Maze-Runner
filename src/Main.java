public class Main {
    public static void main(String[] args) {

        Maze myMaze = new MazeGenerator(4);
        myMaze.display();

        MazeSolver mazeSolver = new MazeSolver(myMaze);
        mazeSolver.solveMaze();
        Graph graph = new Graph(myMaze);

        graph.findVerticesRelation();

        for (Cell cell: graph.getNodesRelation().keySet()) {
            System.out.println(cell + " : " + graph.getNodesRelation().get(cell));
        }
    }
}
