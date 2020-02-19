import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int option = Integer.MAX_VALUE;
        int dim = 5;
        Maze myMaze = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! This is maze-runner fun project.\nPlease choose option:");
        do {
            try {
                if (myMaze != null)
                    System.out.println("1) Generate new maze\n2) Solve a maze\n0) Exit");
                else
                    System.out.println("1) Generate new maze\n0) Exit");

                String input = scanner.next();
                option = Integer.parseInt(input);
                if (option == 2 && myMaze == null)
                    throw new IllegalStateException(Messages.UNEXPECTED_VALUE + option);
                switch (option)
                {
                    case 1:
                        System.out.println("Please choose dimension of a maze(dim<2,65>): ");
                        input = scanner.next();
                        dim = Integer.parseInt(input);
                        if (dim > 40 && dim <= 65) {
                            System.out.println("Warning! Dimension is large and solving maze can take a while.\nContinue? [y/n]");
                            String cont = scanner.next();

                            if (cont.equals("n"))
                                continue;
                            else if (cont.equals("y")) {
                                myMaze = new MazeGenerator(dim);
                                myMaze.display();
                                System.out.println();
                            } else
                                throw new IllegalStateException(Messages.UNEXPECTED_VALUE + option);
                        } else if (dim > 65 || dim < 2) {
                            throw new TooLargeDimensionException(Messages.DIMENSION_ERROR);
                        } else {
                            myMaze = new MazeGenerator(dim);
                            myMaze.display();
                            System.out.println();
                        }
                        break;
                    case 2:
                        if (dim > 40)
                            System.out.println("Solving maze...");

                        MazeSolver mazeSolver = new MazeSolver(myMaze);
                        mazeSolver.solveMaze();
                        myMaze.display();
                        System.out.println("Maze solved!");
                        mazeSolver.displayShortestPath();
                        break;
                    case 0:
                        option = 0;
                        break;

                    default:
                        throw new IllegalStateException(Messages.UNEXPECTED_VALUE + option);
                }
            } catch (NumberFormatException e) {
                System.out.println("Not an integer!");
                e.printStackTrace();
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Wrong option!");
                e.printStackTrace();
            } catch (TooLargeDimensionException e) {
                System.out.println("Maze Error!");
                e.printStackTrace();
            }
        } while (option != 0);
    }
}
