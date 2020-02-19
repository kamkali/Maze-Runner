import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int option = 0;
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
                switch (option)
                {
                    case 1:
                        System.out.println("Please choose dimension of a maze(max 65): ");
                        int dim = scanner.nextInt();
                        if (dim > 30 && dim <= 65){
                            System.out.println("Warning! Dimension is large and solving maze can take a while.\nContinue? [y/n]");
                            String cont = scanner.next();

                            if (cont.equals("n")){
                                continue;
                            } else if (cont.equals("y")) {
                                myMaze = new MazeGenerator(dim);
                                myMaze.display();
                                System.out.println();
                            }
                        } else if (dim > 65){
                            throw new TooLargeDimensionException("Dimension too large!!!");
                        } else {
                            myMaze = new MazeGenerator(dim);
                            myMaze.display();
                            System.out.println();
                        }
                        break;
                    case 2:
                        if (myMaze != null) {
                            MazeSolver mazeSolver = new MazeSolver(myMaze);
                            mazeSolver.solveMaze();
                            myMaze.display();
                        } else {
                            throw new MazeNotGeneratedException("Maze is not generated!");
                        }
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + option);
                }



            } catch(IllegalArgumentException e){
                System.out.println("Wrong option!");
            } catch (TooLargeDimensionException | MazeNotGeneratedException e) {
                e.printStackTrace();
            }
        } while (option != 0);
    }
}
