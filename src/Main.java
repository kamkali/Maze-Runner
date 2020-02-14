import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Maze myMaze = new MazeGenerator(5);
        myMaze.display();

        MazeSolver mazeSolver = new MazeSolver(myMaze.getMazeGrid());
        mazeSolver.solveMaze();
        Graph graph = new Graph(myMaze);

        graph.bfs(myMaze.getMazeGrid()[0][0]);

        myMaze.display();




        Set<Cell> vertices;
        vertices = graph.findVertices(myMaze);
        graph.findDistance(myMaze.getMazeGrid()[0][0], vertices);   // wrong

        System.out.println(vertices);
        System.out.println(vertices.size());
    }
}
