#include<bits/stdc++.h>

using namespace std;

int main()
{
	int board[10][10];
	int zx;
	int pos[3];
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			cin>>board[i][j];
		}
	}
	for(int i=0;i<3;i++)
	cin>>pos[i];
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			cout<<board[i][j]<<" ";
		}
		cout<<"\n";
	}
	for(int i=0;i<3;i++)
	cout<<pos[i]<<" ";
	cout<<endl;
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
							{cout<<i<<" "<<k<<" "<<j;return 0;}
				}
				break;
			case 1:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<9;j++)
						if(board[k][j]==0 && board[k][j+1]==0)
							{cout<<i<<" "<<k<<" "<<j;return 0;}
				}
				break;
			case 2:
				for(int k=0;k<9;k++)
				{
					for(int j=0;j<10;j++)
						if(board[k][j]==0 && board[k+1][j]==0)
							{cout<<i<<" "<<k<<" "<<j;return 0;}
				}
				break;
			case 3:
				for(int k=0;k<9;k++)
				{
					for(int j=0;j<9;j++)
						if(board[k][j]==0 && board[k+1][j]==0 && board[k][j+1]==0 && board[k+1][j+1]==0)
							{cout<<i<<" "<<k<<" "<<j;return 0;}
				}
				break;
			case 4:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<8;j++)
						if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0 )
							{cout<<i<<" "<<k<<" "<<j;return 0;}
				}
				break;
			case 5:
				
				for(int k=0;k<8;k++)
				{
					for(int j=0;j<10;j++)
						if(board[k][j]==0 && board[k+1][j]==0 && board[k+2][j]==0 )
							{cout<<i<<" "<<k<<" "<<j;return 0;}
				}
				break;
			case 6:
				
				for(int k=0;k<8;k++)
				{
					for(int j=0;j<8;j++)
						if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0 
							&& board[k+1][j]==0 && board[k+1][j+1]==0 && board[k+1][j+2]==0 && 
								board[k+2][j]==0 && board[k+2][j+1]==0 && board[k+2][j+2]==0)
							{cout<<i<<" "<<k<<" "<<j;return 0;}
				}
				break;
				
		}
	}
	
	return 0;
}
