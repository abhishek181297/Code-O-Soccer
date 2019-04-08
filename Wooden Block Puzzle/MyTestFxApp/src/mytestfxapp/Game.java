/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytestfxapp;

/**
 *
 * @author abhishek
 */
public class Game {
    public int[][]board = new int[10][10];
    public long score;
    public int[] randomArray = new int[3];
    public Game(){
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                board[i][j]=0;
            }
        }
        score=0;
        for(int i=0;i<3;i++)
        {
            randomArray[i] = i;
        }
    }

    boolean isMovePossible() {
        
        for(int i=0;i<3;i++)
        {
            int block = randomArray[i];
            
            switch(block)
            {
                case 0:
                    for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<10;j++)
                        {
                            if(board[k][j]==0)
                            return true;
                        }
                        
                    }
                    break;
                case 1:
                    for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<9;j++)
                        {
                            if(board[k][j]==0 && board[k][j+1]==0)
                            return true;
                        }
                        
                    }
                    break;
                case 2:
                    for(int k=0;k<9;k++)
                    {
                        for(int j=0;j<10;j++)
                        {
                            if(board[k][j]==0 && board[k+1][j]==0)
                            return true;
                        }
                        
                    }
                    break;
                case 3:
                    for(int k=0;k<9;k++)
                    {
                        for(int j=0;j<9;j++)
                        {
                            if(board[k][j]==0 && board[k+1][j]==0 &&
                                    board[k][j+1]==0 && board[k+1][j+1]==0)
                            return true;
                        }
                        
                    }
                    break;
                case 4:
                    for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<8;j++)
                        {
                            if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0)
                            return true;
                        }
                        
                    }
                    break;
                case 5:
                    for(int k=0;k<8;k++)
                    {
                        for(int j=0;j<10;j++)
                        {
                            if(board[k][j]==0 && board[k+1][j]==0 && board[k+2][j]==0)
                            return true;
                        }
                       
                    }
                    break;
                case 6:
                    for(int k=0;k<8;k++)
                    {
                        for(int j=0;j<8;j++)
                        {
                            if(board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0 &&
                            board[k+1][j]==0 && board[k+1][j+1]==0 && board[k+1][j+2]==0 &&
                            board[k+2][j]==0 && board[k+2][j+1]==0 && board[k+2][j+2]==0 )
                            return true;
                        }
                        
                    }
                    break;
            }
            
        }
        return false;
    }

    void makeMove(String out) {
         out = out.trim();
         String splitStr[] = out.split("\\s+");
         if(splitStr.length!=3)
         {
             throw new IllegalArgumentException("Invalid Output Format.\nGame Ends.\n");
         }
         int pos = Integer.parseInt(splitStr[0]);
         int cord_x = Integer.parseInt(splitStr[1]);
         int cord_y = Integer.parseInt(splitStr[2]);
         int block = randomArray[pos];
         if(pos<0 || pos>2)
             throw new IllegalArgumentException("Invalid Position of Block Selected.\nGame Ends.\n");
         if(cord_x<0 || cord_x>9 || cord_y<0 || cord_y>9)
             throw new IllegalArgumentException("Invalid Cordinates Selected.\nGame Ends.\n");
         if(isThisMovePossible(block,cord_x,cord_y))
         {
             makeThisMove(block,cord_x,cord_y);
             /*switch(block)
             {
                 case 0:score+=1;
                 break;
                 case 1:score+=2;
                 break;
                 case 2:score+=2;
                 break;
                 case 3:score+=4;
                 break;
                 case 4:score+=3;
                 break;
                 case 5:score+=3;
                 break;
                 case 6:score+=9;
                 break;
                 
             }*/
             
             randomArray[pos] = (randomArray[pos]+5)%7;
             
         }
         else
         {
             throw new IllegalArgumentException("Move Not Possible. \nGame Ends.\n");
         }
         
    }

    private boolean isThisMovePossible(int block, int k, int j) {
        switch(block)
        {
            case 0:if(board[k][j]==0)
                    return true;
                    else
                        return false;
                
            case 1:if(j+1<10 && board[k][j]==0 && board[k][j+1]==0)
                    return true;
                    else
                    return false;
                
            case 2:if(k+1<10 && board[k][j]==0 && board[k+1][j]==0)
                    return true;
                    else
                    return false;
            case 3:if(k+1<10 && j+1<10 &&board[k][j]==0 && board[k+1][j]==0 &&
                                    board[k][j+1]==0 && board[k+1][j+1]==0)
                    return true;
                    else
                    return false;
            case 4:if(j+2<10 && board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0)
                    return true;
                    else
                    return false;
            case 5:if(k+2<10 && board[k][j]==0 && board[k+1][j]==0 && board[k+2][j]==0)
                    return true;
                    else
                    return false;
            case 6:if(k+2<10 && j+2<10 &&board[k][j]==0 && board[k][j+1]==0 && board[k][j+2]==0 &&
                        board[k+1][j]==0 && board[k+1][j+1]==0 && board[k+1][j+2]==0 &&
                        board[k+2][j]==0 && board[k+2][j+1]==0 && board[k+2][j+2]==0 )
                    return true;
                    else
                    return false;
            
        }
        return false;
    }

    private void makeThisMove(int block, int k, int j) {
        switch(block)
        {
            case 0:board[k][j]=1;
                    break;
            case 1:board[k][j]=1;board[k][j+1]=1;
                    break;
            case 2:board[k][j]=1;board[k+1][j]=1;
                    break;
            case 3:board[k][j]=1;board[k][j+1]=1;board[k+1][j]=1;board[k+1][j+1]=1;
                    break;
            case 4:board[k][j]=1;board[k][j+1]=1;board[k][j+2]=1;
                    break;
            case 5:board[k][j]=1;board[k+1][j]=1;board[k+2][j]=1;
                    break;
            case 6:board[k][j]=1;board[k][j+1]=1;board[k][j+2]=1;
                    board[k+1][j]=1;board[k+1][j+1]=1;board[k+1][j+2]=1;
                    board[k+2][j]=1;board[k+2][j+1]=1;board[k+2][j+2]=1;
                    break;
        }
    }
    
}
