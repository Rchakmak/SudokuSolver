import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class SudokuSolver {

    public final static int SIZE = 9;

    public static void main(String[] args) throws FileNotFoundException {
        int[][] board = importBoard();
        long time = System.nanoTime();
        solveBoard(board)   ;
        System.out.println((System.nanoTime()-time)/1e6);
    }


    public static boolean solveBoard (int[][] board){

        // Lose condition
        if(checkIfFailed(board)) {
            return false;
        }

        // Win condition
        if(checkIfSolved(board)) {
            for (int i = 0; i < board.length; i++){
                for (int j = 0; j < board[i].length; j++){
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            return true;
        }

        // Recursive solver
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++){
                if(board[i][j] == 0) {
                    for (int number = 1; number <= 9; number++) {
                        board[i][j] = number;
                        if(solveBoard(board)) {
                            return true;
                        }
                    }
                    board[i][j] = 0;
                    return false;
                }

            }
        }
        return false;
    }

    // Considers 0s spaces
    public static int[][] importBoard() throws FileNotFoundException{
        Scanner input = new Scanner(new File("src/data.txt"));
        int[][] board = new int[SIZE][SIZE];
        int countRow = 0;
        while(input.hasNext()) {
            int countColumn = 0;
            while(countColumn < SIZE) {
                board[countRow][countColumn] = input.nextInt();
                countColumn++;
            }
            countRow++;
        }
        return board;
    }

    public static boolean checkIfSolved(int[][] board) {
        HashSet<Integer> numberBundleColumn = new HashSet<>();
        HashSet<Integer> numberBundleRow = new HashSet<>();

        // Returns false if column is not solved
        for (int i = 0; i < board.length; i++) {

            // Resets HashSet
            numberBundleColumn.clear();
            numberBundleRow.clear();

            // Imports row and column to HashSets
            for (int j = 0; j < board[i].length; j++) {
                numberBundleColumn.add(board[i][j]);
                numberBundleRow.add(board[j][i]);
            }

            // Checks if column and row each have numbers 1-9
            for (int j = 1; j <= board[i].length; j++) {
                if (!numberBundleColumn.contains(j) || !numberBundleRow.contains(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkIfFailed(int[][] board) {
        HashSet<Integer> numberBundleColumn = new HashSet<>();
        HashSet<Integer> numberBundleRow = new HashSet<>();
        HashSet<Integer> numberBundleSquare = new HashSet<>();

        // Returns true if column or row contains repeats
        for (int i = 0; i < board.length; i++) {

            // Resets HashSet
            numberBundleColumn.clear();
            numberBundleRow.clear();

            // Imports row and column to HashSets
            for (int j = 0; j < board[i].length; j++) {
                if(numberBundleColumn.contains(board[i][j]) && board[i][j] != 0) {
                    return true;
                }
                numberBundleColumn.add(board[i][j]);

                if(numberBundleRow.contains(board[j][i]) && board[j][i] != 0) {
                    return true;
                }
                numberBundleRow.add(board[j][i]);
            }
        }

        // Returns true if a 3x3 block contains repeats
        for (int whichSquare = 0; whichSquare < board.length; whichSquare = whichSquare + (int) Math.sqrt(board.length)){
            numberBundleSquare.clear();
            for (int i = whichSquare; i < Math.sqrt(board.length) + whichSquare; i++) {
                for (int j = whichSquare; j < Math.sqrt(board[i].length) + whichSquare; j++){
                    if(numberBundleSquare.contains(board[i][j]) && board[i][j] !=0) {
                        return true;
                    }
                    numberBundleSquare.add(board[i][j]);
                }
        }


        }
        return false;
    }
}



