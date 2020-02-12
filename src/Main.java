import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Maze myMaze = new MazeGenerator(4);

        myMaze.display();

        MazeSolver mazeSolver = new MazeSolver(myMaze.getMazeGrid());
        mazeSolver.solveMaze();
        Graph graph = new Graph();

        Set<Cell> vertices;
        vertices = graph.findVertices(myMaze);
        System.out.println(vertices);
        System.out.println(vertices.size());
    }
}
