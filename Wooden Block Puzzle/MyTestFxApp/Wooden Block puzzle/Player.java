import java.util.*;
import java.io.*;

public class Player {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       
        int[][] board = new int[10][10];
	int[] pos = new int[3];
        for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			board[i][j] = sc.nextInt();
		}
	}
	for(int i=0;i<3;i++)
	pos[i] = sc.nextInt();
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			System.out.print(board[i][j]+" ");
		}
		System.out.println("");
	}
	for(int i=0;i<3;i++)
	System.out.print(pos[i]+" ");
	System.out.println("");
	for(int i=0;i<3;i++)
	{
		int block = pos[i];
		switch(block)
		{
			case 0:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<10;j++)
						if(board[k][j]==0)
							{System.out.println(i+" "+k+" "+j);return;}
				}
				break;
			case 1:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<9;j++)
						if(board[k][j]==0 && board[k][j+1]==0)
							{System.out.println(i+" "+k+" "+j);return;}
				}
				break;
			case 2:
				for(int k=0;k<9;k++)
				{
					for(int j=0;j<10;j++)
						if(board[k][j]==0 && board[k+1][j]==0)
							{System.out.println(i+" "+k+" "+j);return;}
				}
				break;
			case 3:
				for(int k=0;k<9;k++)
				{
					for(int j=0;j<9;j++)
						if(board[k][j]==0 && board[k+1][j]==0 && board[k][j+1]==0 && board[k+1][j+1]==0)
							{System.out.println(i+" "+k+" "+j);return;}
				}
				break;
			case 4:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<8;j++)
						if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0 )
							{System.out.println(i+" "+k+" "+j);return;}
				}
				break;
			case 5:
				
				for(int k=0;k<8;k++)
				{
					for(int j=0;j<10;j++)
						if(board[k][j]==0 && board[k+1][j]==0 && board[k+2][j]==0 )
							{System.out.println(i+" "+k+" "+j);return;}
				}
				break;
			case 6:
				
				for(int k=0;k<8;k++)
				{
					for(int j=0;j<8;j++)
						if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0 
							&& board[k+1][j]==0 && board[k+1][j+1]==0 && board[k+1][j+2]==0 && 
								board[k+2][j]==0 && board[k+2][j+1]==0 && board[k+2][j+2]==0)
							{System.out.println(i+" "+k+" "+j);return;}
				}
				break;
				
		}
	}
    }
}
