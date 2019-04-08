import java.util.*;
import java.io.*;

public class Satya
 {
    public static void main(String args[])
 {
        Scanner in= new Scanner(System.in);
       int k,j,i;
boolean flag=false;

        int a[][] = new int[10][10];
	int pos[] = new int[3];
        for(i=0;i<10;i++)
       {
		for(j=0;j<10;j++)
		{
			a[i][j] = in.nextInt();
		}
	}
	
for( i=0;i<10;i++)
	{
		for( j=0;j<10;j++)
		{
			System.out.print(a[i][j]+" ");
		}
		System.out.println("");
	}
//logic: to place the boxes in ascending size
while(true)
{
for( i=0;i<3;i++)
	pos[i] = in.nextInt();

	System.out.println();
if(flag==false)
{
for(i=0;i<3;i++)
	{
		if(pos[i]==0)
{
for(k=0;k<10;k++)
{
for(j=0;j<10;j++)
{
if(a[k][j]==0)
{
System.out.println(i+" "+k+" "+j);
flag=true;
}
}
}
}
                if(pos[i]==1)
{
for(k=0;k<9;k++)
{
for(j=0;j<10;j++)
{
if(a[k][j]==0)
{
System.out.println(i+" "+k+" "+j);
flag=true;
}
}
}
}
                if(pos[i]==2)	
{
for(k=0;k<8;k++)
{
for(j=0;j<10;j++)
{
if(a[k][j]==0)
{
System.out.println(i+" "+k+" "+j);
flag=true;
}	
}
}
}
                if(pos[i]==3)
{
for(k=0;k<10;k++)
{
for(j=0;j<9;j++)
{
if(a[k][j]==0)
{
System.out.println(i+" "+k+" "+j);
flag=true;
}
}
}
}
                if(pos[i]==4)
{
for(k=0;k<9;k++)
{
for(j=0;j<9;j++)
{
if(a[k][j]==0)
{
System.out.println(i+" "+k+" "+j);
flag=true;
}
}
}
}
                if(pos[i]==5)
{
for(k=0;k<10;k++)
{
for(j=0;j<8;j++)
{
if(a[k][j]==0)
{
System.out.println(i+" "+k+" "+j);
flag=true;
}
}
}
}
                if(pos[i]==6)	
{
for(k=0;k<8;k++)
{
for(j=0;j<8;j++)
{
if(a[k][j]==0)
{
System.out.println(i+" "+k+" "+j);
flag=true;
}			
		}
	}
    }
}
}	
}
}



