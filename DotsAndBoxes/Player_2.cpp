#include <bits/stdc++.h>

using namespace std;

#define pi pair<int, pair<int,int> >

int main(int arg, char **args) {

	
	int board[36][36];
	priority_queue<  pi ,vector< pi > ,greater<pi> > pq;
	
	while(!pq.empty()){
		pq.pop();
	}	

	for(int i=0;i<36;i++)
	{
		for(int j=0;j<36;j++)
		{
			cin>>board[i][j];
		}
	}
	
	int sqarr[26]={0};
	
	for(int i=0;i<25;i++){
		int row_no=i/5;
		
		int lt=row_no*6+(i%5);
		int rt=lt+1,lb=lt+6,rb=lt+7;
		
		int cnt=0;
		
		if(board[lt][rt]==1)
			cnt++;
		if(board[lt][lb]==1)
			cnt++;
		if(board[rt][rb]==1)
			cnt++;
		if(board[lb][rb]==1)
			cnt++;
		
		cnt=4-cnt;
	
		sqarr[i]=cnt;
		
		/*
		if(cnt!=0){
			if(cnt==2)
				cnt=cnt*4;
			if(cnt==3)
				cnt=cnt*2;
			pq.push({cnt,i});
		}
		*/
		
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
		
		for(int i=0;i<36;i++){
			for(int j=0;j<i;j++){
				if(board[i][j]!=1 && mat[i][j]==1){
					if(abs(i-j)==6){
						if(j%6==0){
							int sqr=(j/6)*5+j%6;
							int prio=sqarr[sqr]-1;
							if(prio==1){
								prio=prio*4;
							}
							pq.push({prio,{i,j}});
							
						}
						else if(j%6==5){
							int j1=j-1;
							int sqr=(j1/6)*5+j1%6;
							int prio=sqarr[sqr]-1;
							if(prio==1){
								prio=prio*4;
							}
							pq.push({prio,{i,j}});
						}
						else{
							int sqr=(j/6)*5+j%6;
							int j1=j-1;
							int sqr1=(j1/6)*5+j1%6;
							int prio0=sqarr[sqr]-1;
							int pri1=sqarr[sqr1]-1;
							int prio;
							if(prio0==0 && pri1==0)
								prio=-3;
							else if((prio0==0 && pri1==1) || (prio0==1 && pri1==0))
								prio=-2;
							else if((prio0==0 && pri1==2) || (prio0==2 && pri1==0))
								prio=0;
							else if((prio0==0 && pri1==3) || (prio0==3 && pri1==0))
								prio=0;
							else if(prio0==1 || pri1==1)
								prio=10;
							else
								prio=max(prio0,pri1);
							pq.push({prio,{i,j}});
						}
						
					}
					else{
						if(j<7){
							int sqr=(j/6)*5+j%6;
							int prio=sqarr[sqr]-1;
							if(prio==1){
								prio=prio*4;
							}
							pq.push({prio,{i,j}});
							
						}
						else if(j>29){
							int j1=j-6;
							int sqr=(j1/6)*5+j1%6;
							int prio=sqarr[sqr]-1;
							if(prio==1){
								prio=prio*4;
							}
							pq.push({prio,{i,j}});
						}
						else{
							int sqr=(j/6)*5+j%6;
							int j1=j-6;
							int sqr1=(j1/6)*5+j1%6;
							int prio0=sqarr[sqr]-1;
							int pri1=sqarr[sqr1]-1;
							int prio;
							if(prio0==0 && pri1==0)
								prio=-3;
							else if((prio0==0 && pri1==1) || (prio0==1 && pri1==0))
								prio=-2;
							else if((prio0==0 && pri1==2) || (prio0==2 && pri1==0))
								prio=0;
							else if((prio0==0 && pri1==3) || (prio0==3 && pri1==0))
								prio=0;
							else if(prio0==1 || pri1==1)
								prio=10;
							else
								prio=max(prio0,pri1);
							pq.push({prio,{i,j}});
						}
					}
				}
			}
		}
		
	pi temp=pq.top();
	
	cout<<temp.second.first<<" "<<temp.second.second<<endl;
	
	
	
	/*
	for(int i=35;i>=0;i--)
	{
		for(int j=0;j<i;j++)
		{
			if(mat[i][j]==1 && board[i][j]!=1)
			{
				cout<<i<<" "<<j<<endl;
				//cout<<1<<endl;
				return 0;
			}
		}
	}
	*/

	

    return 0;
}
//Illuminati