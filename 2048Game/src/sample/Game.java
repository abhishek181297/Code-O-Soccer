package sample;

import java.security.SecureRandom;
import java.util.Arrays;

public class Game {

    public final int SIZE = 4;
    public int[][] board = new int[SIZE][SIZE];
    public long score;

    public Game() {

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = 0;

        addRandom();
        addRandom();

        score = 0;
    }

    public void makeMove(String move) {

        move = move.toLowerCase();

        int [][] temp = new int[board.length][];
        for(int i = 0; i < board.length; i++) {
            int[] aMatrix = board[i];
            int aLength = aMatrix.length;
            temp[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, temp[i], 0, aLength);
        }

        switch (move) {
            case "up":
                moveUp();
                break;
            case "left":
                moveRight();
                break;
            case "down":
                moveDown();
                break;
            case "right":
                moveLeft();
                break;
            default:
                throw new IllegalArgumentException("Invalid Output");
        }

        boolean check1 = true;
        for (int i = 0; check1 && i < board.length; i++) {
            check1 = Arrays.equals(board[i], temp[i]);
        }

        if (!check1)
            addRandom();
    }

    int findTarget(int[] array, int x, int stop) {
        int t;
        // if the position is already on the first, don't evaluate
        if (x == 0) {
            return x;
        }
        for (t = x - 1; t >= 0; t--) {
            if (array[t] != 0) {
                if (array[t] != array[x]) {
                    // merge is not possible, take next position
                    return t + 1;
                }
                return t;
            } else {
                // we should not slide further, return this one
                if (t == stop) {
                    return t;
                }
            }
        }
        // we did not find a
        return x;
    }

    void slideArray(int[] array) {
        int x, t, stop = 0;

        for (x = 0; x < SIZE; x++) {
            if (array[x] != 0) {
                t = findTarget(array, x, stop);
                // if target is not original position, then move or merge
                if (t != x) {
                    // if target is zero, this is a move
                    if (array[t] == 0) {
                        array[t] = array[x];
                    } else if (array[t] == array[x]) {
                        // merge (increase power of two)
                        array[t] *= 2;
                        // increase score
                        score += array[t];
                        // set stop to avoid double merge
                        stop = t + 1;
                    }
                    array[x] = 0;
                }
            }
        }
    }

    private void moveUp() {
        for (int i = 0; i < SIZE; i++) {
            slideArray(board[i]);
        }
    }

    private void moveLeft() {
        rotateBoard();
        moveUp();
        rotateBoard();
        rotateBoard();
        rotateBoard();
    }

    private void moveRight() {
        rotateBoard();
        rotateBoard();
        rotateBoard();
        moveUp();
        rotateBoard();
    }

    private void moveDown() {
        rotateBoard();
        rotateBoard();
        moveUp();
        rotateBoard();
        rotateBoard();
    }

    private boolean findPairDown() {

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE - 1; j++)
                if (board[i][j] == board[i][j + 1])
                    return true;

        return false;
    }

    private int countEmpty() {
        int count = 0;

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == 0)
                    count++;

        return count;
    }

    public boolean isMovePossible() {
        boolean possible = false;
        if (countEmpty() > 0) return true;
        if (findPairDown()) return true;
        rotateBoard();
        if (findPairDown()) possible = true;
        rotateBoard();
        rotateBoard();
        rotateBoard();
        return possible;
    }

    private void rotateBoard() {
        // transpose
        for (int i = 0; i < SIZE; i++)
            for (int j = i; j < SIZE; j++) {
                int temp = board[j][i];
                board[j][i] = board[i][j];
                board[i][j] = temp;
            }

        // reversing row
        for (int i = 0; i < SIZE; i++)
            for (int j = 0, k = SIZE - 1; j < k; j++, k--) {
                int temp = board[i][j];
                board[i][j] = board[i][k];
                board[i][k] = temp;
            }
    }

    private void addRandom() {

        SecureRandom random = new SecureRandom();

        int[][] list = new int[SIZE * SIZE][2];
        int len = 0;

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == 0) {
                    list[len][0] = i;
                    list[len][1] = j;
                    len++;
                }

        if (len > 0) {
            int r = random.nextInt(len);
            int x = list[r][0];
            int y = list[r][1];
            board[x][y] = random.nextDouble() < 0.9 ? 2 : 4;
        }
    }
}
