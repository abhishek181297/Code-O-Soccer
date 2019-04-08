#include <bits/stdc++.h>


using namespace std;

int main(int arg, char **args) {


	int board[36][36];

	for(int i=0;i<36;i++)
	{
		for(int j=0;j<36;j++)
		{
			cin>>board[i][j];
		}
	}
	int mat[36][36] = {0};
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
    int x=0,y=0;

    for(int i=35;i>=0;i--)
	{
		for(int j=0;j<i;j++)
		{
			if(mat[i][j]==1 && board[i][j]!=1)
			{
			    if(j+1==i){
                    if(i-6>=0&&j-6>=0&&board[j-6][i-6]&&board[j][j-6]&&board[i][i-6]){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
                    if(i+6<=35&&j+6<=35&&board[j+6][i+6]&&board[j][j+6]&&board[i][i+6]){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
			    }
			    else{
                    if(i-1>=0&&j-1>=0&&board[j-1][i-1]&&board[j][j-1]&&board[i][i-1]){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
                    if(i+1<=35&&j+1<=35&&board[j+1][i+1]&&board[j][j+1]&&board[i][i+1]){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
			    }
			}
        }
    }


    x=0,y=0;

    for(int i=35;i>=0;i--)
	{
		for(int j=0;j<i;j++)
		{
			if(mat[i][j]==1 && board[i][j]!=1)
			{
			    if(j+1==i){
                    if(i-6>=0&&j-6>=0&&board[j-6][i-6]==0&&board[j][j-6]==0&&board[i][i-6]==0){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
                    if(i+6<=35&&j+6<=35&&board[j+6][i+6]==0&&board[j][j+6]==0&&board[i][i+6]==0){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
			    }
			    else{
                    if(i-1>=0&&j-1>=0&&board[j-1][i-1]==0&&board[j][j-1]==0&&board[i][i-1]==0){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
                    if(i+1<=35&&j+1<=35&&board[j+1][i+1]==0&&board[j][j+1]==0&&board[i][i+1]==0){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
			    }
			}
        }
    }

    x=0,y=0;

    for(int i=35;i>=0;i--)
	{
		for(int j=0;j<i;j++)
		{
			if(mat[i][j]==1 && board[i][j]!=1)
			{
			    if(j+1==i){
                    if(i-6>=0&&j-6>=0&&(board[j-6][i-6]&&board[j][j-6]==0&&board[i][i-6]==0)||(board[j-6][i-6]==0&&board[j][j-6]&&board[i][i-6]==0)||(board[j-6][i-6]==0&&board[j][j-6]==0&&board[i][i-6])){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
                    if(i+6<=35&&j+6<=35&&(board[j+6][i+6]&&board[j][j+6]==0&&board[i][i+6]==0)||(board[j+6][i+6]==0&&board[j][j+6]&&board[i][i+6]==0)||(board[j+6][i+6]==0&&board[j][j+6]==0&&board[i][i+6])){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
			    }
			    else{
                    if(i-1>=0&&j-1>=0&&(board[j-1][i-1]&&board[j][j-1]==0&&board[i][i-1]==0)||(board[j-1][i-1]==0&&board[j][j-1]&&board[i][i-1]==0)||(board[j-1][i-1]==0&&board[j][j-1]==0&&board[i][i-1])){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
                    if(i+1<=35&&j+1<=35&&(board[j+1][i+1]&&board[j][j+1]==0&&board[i][i+1]==0)||(board[j+1][i+1]==0&&board[j][j+1]&&board[i][i+1]==0)||(board[j+1][i+1]==0&&board[j][j+1]==0&&board[i][i+1])){
                        cout<<i<<" "<<j<<endl;
                        return 0;
                    }
			    }
			}
        }
    }

    x=0,y=0;
    int sum=INT_MAX;
    for(int i=35;i>=0;i--)
	{
		for(int j=0;j<i;j++)
		{
			if(mat[i][j]==1 && board[i][j]!=1)
			{
			    int c=0;
			    board[i][j]=1;
			    for(int k=0;k<25;k++){
                    if(k+1<=35&&k+6<=35&&k+7<=35&&board[k][k+1]&&board[k][k+6]&&board[k+1][k+7]&&board[k+6][k+7]){
                        c++;
                    }
			    }
			    board[i][j]=0;
			    if(c<sum){
                    sum=c;
                    x=i,y=j;
			    }
			    
			}
        }
    }
    cout<<x<<" "<<y<<endl;
			    return 0;




    return 0;
}
//WHIPLASH