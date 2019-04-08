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

    int s=0,x=0,y=0;
    int s1=0,x1=0,y1=0,i1=0;
	for(int i=0;i<3;i++)
	{
	    s=0;x=-1;y=-1;
		int block = pos[i];
		switch(block)
		{
			case 0:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<10;j++){
                        int sumr=0,sumc=0;
                        if(board[k][j]==0){
                            for(int l=0;l<10;l++){
                                sumr+=board[k][l];
                            }
                            for(int l=0;l<10;l++){
                                    sumc+=board[l][j];
                            }
                        }
                        if(s<max(sumr,sumc)){
                            s=max(sumr,sumc);
                            x=k;y=j;
                        }

					}
				}

				break;
			case 1:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<9;j++){
                        int sumr=0,sumc1=0,sumc2=0;
						if(board[k][j]==0 && board[k][j+1]==0){
                                for(int l=0;l<10;l++){
                                    sumr+=board[k][l];
                                }
                                for(int l=0;l<10;l++){
                                        sumc1+=board[l][j];
                                }
                                for(int l=0;l<10;l++){
                                        sumc2+=board[l][j+1];
                                }
                            if(s<max(sumr,max(sumc1,sumc2))){
                                s=max(sumr,max(sumc1,sumc2));
                                x=k;y=j;
                            }
						}
					}
				}

				break;
			case 2:
				for(int k=0;k<9;k++)
				{
					for(int j=0;j<10;j++){
                            int sumr1=0,sumr2=0,sumc=0;
                        if(board[k][j]==0 && board[k+1][j]==0){
                             for(int l=0;l<10;l++){
                                    sumr1+=board[k][l];
                                }
                                for(int l=0;l<10;l++){
                                        sumc+=board[l][j];
                                }
                                for(int l=0;l<10;l++){
                                        sumr2+=board[k+1][l];
                                }
                            if(s<max(sumr1,max(sumr2,sumc))){
                                s=max(sumr1,max(sumr2,sumc));
                                x=k;y=j;
                            }
                        }
					}

				}

				break;
			case 3:
				for(int k=0;k<9;k++)
				{
					for(int j=0;j<9;j++){
                        int sumr1=0,sumr2=0,sumc1=0,sumc2=0;
                        if(board[k][j]==0 && board[k+1][j]==0 && board[k][j+1]==0 && board[k+1][j+1]==0){
                            for(int l=0;l<10;l++){
                                    sumr1+=board[k][l];
                                }
                                for(int l=0;l<10;l++){
                                    sumr2+=board[k+1][l];
                                }
                                for(int l=0;l<10;l++){
                                        sumc1+=board[l][j];
                                }
                                for(int l=0;l<10;l++){
                                        sumc2+=board[l][j+1];
                                }
                            if(s<max(max(sumr1,sumr2),max(sumc1,sumc2))){
                                s=max(max(sumr1,sumr2),max(sumc1,sumc2));
                                x=k;y=j;
                            }
                        }
					}

				}

				break;
			case 4:
				for(int k=0;k<10;k++)
				{
					for(int j=0;j<8;j++){
                            int sumr=0,sumc2=0,sumc1=0,sumc3=0;
                        if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0 ){
                            for(int l=0;l<10;l++){
                                    sumr+=board[k][l];
                                }
                                for(int l=0;l<10;l++){
                                        sumc1+=board[l][j];
                                }
                                for(int l=0;l<10;l++){
                                        sumc2+=board[l][j+1];
                                }
                                for(int l=0;l<10;l++){
                                        sumc3+=board[l][j+2];
                                }
                            if(s<max(max(sumr,sumc1),max(sumc2,sumc3))){
                                s=max(max(sumr,sumc1),max(sumc2,sumc3));
                                x=k;y=j;
                            }
						}
					}

				}

				break;
			case 5:

				for(int k=0;k<8;k++)
				{
					for(int j=0;j<10;j++){
                        int sumr1=0,sumr2=0,sumr3=0,sumc=0;
                        if(board[k][j]==0 && board[k+1][j]==0 && board[k+2][j]==0 ){
                            for(int l=0;l<10;l++){
                                    sumr1+=board[k][l];
                                }
                                for(int l=0;l<10;l++){
                                        sumc+=board[l][j];
                                }
                                for(int l=0;l<10;l++){
                                        sumr2+=board[k+1][l];
                                }
                                for(int l=0;l<10;l++){
                                        sumr3+=board[k+2][l];
                                }
                            if(s<max(max(sumr1,sumr2),max(sumr3,sumc))){
                                s=max(max(sumr1,sumr2),max(sumr3,sumc));
                                x=k;y=j;
                            }
                        }
					}
				}

				break;
			case 6:

				for(int k=0;k<8;k++)
				{
					for(int j=0;j<8;j++){
                            int sumr1=0,sumr2=0,sumr3=0,sumc1=0,sumc2=0,sumc3=0;
                        if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0
							&& board[k+1][j]==0 && board[k+1][j+1]==0 && board[k+1][j+2]==0 &&
								board[k+2][j]==0 && board[k+2][j+1]==0 && board[k+2][j+2]==0){

								  for(int l=0;l<10;l++){
                                    sumr1+=board[k][l];
                                }
                                for(int l=0;l<10;l++){
                                    sumr2+=board[k+1][l];
                                }
                                for(int l=0;l<10;l++){
                                    sumr2+=board[k+2][l];
                                }
                                for(int l=0;l<10;l++){
                                        sumc1+=board[l][j];
                                }
                                for(int l=0;l<10;l++){
                                        sumc2+=board[l][j+1];
                                }
                                for(int l=0;l<10;l++){
                                        sumc3+=board[l][j+2];
                                }
                            if(s<max(max(sumr1,sumr2),max(max(sumc1,sumc2),max(sumr3,sumc3)))){
                                s=max(max(sumr1,sumr2),max(max(sumc1,sumc2),max(sumr3,sumc3)));
                                x=k;y=j;
                            }


                        }

					}

				}

				break;

		}
		if(s1<s){
            s1=s;
            x1=x;
            y1=y;
            i1=i;
		}
	}

	cout<<i1<<" "<<x1<<" "<<y1<<endl;
	return 0;
}