import java.util.*;
import java.io.*;

public class Player_2 {

    public static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public static void findPlaceableLocations(char[][] board, char player, char opponent, HashSet<Point> placeablePositions) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == opponent) {
                    int I = i, J = j;
                    if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1] == '_') {
                        i = i + 1;
                        j = j + 1;
                        while (i < 7 && j < 7 && board[i][j] == opponent) {
                            i++;
                            j++;
                        }
                        if (i <= 7 && j <= 7 && board[i][j] == player) placeablePositions.add(new Point(I - 1, J - 1));
                    }
                    i = I;
                    j = J;
                    if (i - 1 >= 0 && board[i - 1][j] == '_') {
                        i = i + 1;
                        while (i < 7 && board[i][j] == opponent) i++;
                        if (i <= 7 && board[i][j] == player) placeablePositions.add(new Point(I - 1, J));
                    }
                    i = I;
                    if (i - 1 >= 0 && j + 1 <= 7 && board[i - 1][j + 1] == '_') {
                        i = i + 1;
                        j = j - 1;
                        while (i < 7 && j > 0 && board[i][j] == opponent) {
                            i++;
                            j--;
                        }
                        if (i <= 7 && j >= 0 && board[i][j] == player) placeablePositions.add(new Point(I - 1, J + 1));
                    }
                    i = I;
                    j = J;
                    if (j - 1 >= 0 && board[i][j - 1] == '_') {
                        j = j + 1;
                        while (j < 7 && board[i][j] == opponent) j++;
                        if (j <= 7 && board[i][j] == player) placeablePositions.add(new Point(I, J - 1));
                    }
                    j = J;
                    if (j + 1 <= 7 && board[i][j + 1] == '_') {
                        j = j - 1;
                        while (j > 0 && board[i][j] == opponent) j--;
                        if (j >= 0 && board[i][j] == player) placeablePositions.add(new Point(I, J + 1));
                    }
                    j = J;
                    if (i + 1 <= 7 && j - 1 >= 0 && board[i + 1][j - 1] == '_') {
                        i = i - 1;
                        j = j + 1;
                        while (i > 0 && j < 7 && board[i][j] == opponent) {
                            i--;
                            j++;
                        }
                        if (i >= 0 && j <= 7 && board[i][j] == player) placeablePositions.add(new Point(I + 1, J - 1));
                    }
                    i = I;
                    j = J;
                    if (i + 1 <= 7 && board[i + 1][j] == '_') {
                        i = i - 1;
                        while (i > 0 && board[i][j] == opponent) i--;
                        if (i >= 0 && board[i][j] == player) placeablePositions.add(new Point(I + 1, J));
                    }
                    i = I;
                    if (i + 1 <= 7 && j + 1 <= 7 && board[i + 1][j + 1] == '_') {
                        i = i - 1;
                        j = j - 1;
                        while (i > 0 && j > 0 && board[i][j] == opponent) {
                            i--;
                            j--;
                        }
                        if (i >= 0 && j >= 0 && board[i][j] == player) placeablePositions.add(new Point(I + 1, J + 1));
                    }
                    i = I;
                    j = J;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        char player = sc.next().charAt(0);
        char[][] a = new char[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                a[i][j] = sc.next().charAt(0);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(a[i][j] + " ");
            System.out.println();
        }

        char opponent = (player == 'B') ? 'W' : 'B';

        HashSet<Point> placeablePossitions = new HashSet<>();
        findPlaceableLocations(a, player, opponent, placeablePossitions);

        Random rand = new Random();

        Point move = new Point(-1, -1);

        char[] code = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

        int index = rand.nextInt(placeablePossitions.size());
        int i = 0;
        for(Point obj: placeablePossitions) {
            if (i == index) {
                move = obj;
                break;
            }
            i++;
        }

        move.x++;

        System.out.println(move.x + "" + code[move.y]);
    }
}