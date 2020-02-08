public class Main {
    public static void main(String[] args) throws InterruptedException {
        Maze myMaze = new MazeGenerator();

        myMaze.generate(10, 10);

        myMaze.draw();

    }
}
