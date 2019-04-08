import java.util.*;
import java.io.*;

public class Player_1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] board = new int[36][36];

        for (int i = 0; i < 36; i++)
            for (int j = 0; j < 36; j++)
                board[i][j] = sc.nextInt();

	int[][] mat = new int[36][36];
        	for(int i=0;i<6;i++)
        {
            for(int j=0;j<6;j++)
            {
                if(i==0)
                {
                    if(j==0)
                    {
                        mat[0][1] = 1;
                        mat[1][0] = 1;
                        mat[0][6] = 1;
                        mat[6][0] = 1;
                    }
                    else if(j==5)
                    {
                        mat[4][5] = 1;
                        mat[5][4] = 1;
                        mat[5][11] = 1;
                        mat[11][5] = 1;
                    }
                    else
                    {
                        int val = i*6+j;
                        mat[val][val+1] = mat[val+1][val] = 1;
                        mat[val][val-1] = mat[val-1][val] = 1;
                        mat[val][val+6] = mat[val+6][val] = 1;
                    }
                }
                else if(i==5)
                {
                    if(j==0)
                    {
                        mat[30][24] = mat[24][30] = 1;
                        mat[30][31] = mat[31][30] = 1;
                    }
                    else if(j==5)
                    {
                        mat[35][29] = mat[29][35] = 1;
                        mat[35][34] = mat[34][35] = 1;
                    }
                    else
                    {
                        int val = i*6+j;
                        mat[val][val+1] = mat[val+1][val] = 1;
                        mat[val][val-1] = mat[val-1][val] = 1;
                        mat[val][val-6] = mat[val-6][val] = 1;
                    }
                }
                else if(j==0)
                {
                    int val = i*6+j;
                    mat[val][val+1] = mat[val+1][val] = 1;
                    mat[val][val+6] = mat[val+6][val] = 1;
                    mat[val][val-6] = mat[val-6][val] = 1;
                }
                else if(j==5)
                {
                    int val = i*6+j;
                    mat[val][val-1] = mat[val-1][val] = 1;
                    mat[val][val+6] = mat[val+6][val] = 1;
                    mat[val][val-6] = mat[val-6][val] = 1;
                }
                else
                {
                    int val = i*6+j;
                    mat[val][val+1] = mat[val+1][val] = 1;
                    mat[val][val-1] = mat[val-1][val] = 1;
                    mat[val][val+6] = mat[val+6][val] = 1;
                    mat[val][val-6] = mat[val-6][val] = 1;
                }
            }
        }
	for(int i=35;i>=0;i--)
	{
		for(int j=0;j<i;j++)
		{
			if(mat[i][j]==1 && board[i][j]!=1)
			{
				System.out.println(i+" "+j);
				//cout<<1<<endl;
				return ;
			}
		}
	}


    }
}
