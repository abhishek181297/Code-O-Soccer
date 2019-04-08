import java.util.*;
import java.io.*;

public class Player {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                a[i][j] = sc.nextInt();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(a[i][j] + " ");
            System.out.println();
        }

        Random rand = new Random();

        switch (rand.nextInt(4)) {
            case 0:
                System.out.println("up");
                break;
            case 1:
                System.out.println("down");
                break;
            case 2:
                System.out.println("left");
                break;
            case 3:
                System.out.println("right");
                break;
            default:
                break;
        }
    }
}