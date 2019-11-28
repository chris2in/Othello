package NewO;

import java.util.Scanner;

public class RUNME {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		while(true)
		{
			System.out.println("1.pvp");
			System.out.println("2.pve");
			System.out.println("3.tbd");
			System.out.println("0.quit");
			
			try
			{
				int titleResult = Integer.parseInt(scan.next());
				if(titleResult ==1)
				{
					Node pvp = new Node();
					pvp.availableUpdate(pvp.boardState);
					pvp.printBoard(pvp.boardState);
					for(int i = 0 ; i <pvp.availableUpdate(pvp.boardState).length;i++) {
						System.out.print(pvp.availableUpdate(pvp.boardState)[i]+"\t");
					}
					System.out.println();
					while(true&&pvp.count < 64 &&(pvp.avaiable1 || pvp.avaiable2))
					{
						System.out.println("enter your choice, or -1 to quit ");
						try
						{
							int move = Integer.parseInt(scan.next());
							if(move == -1)
							{
								break;
							}
							int row = move /10;
							int col = move %10;
							
							pvp.place(row,col,pvp.boardState,pvp.turn);
							
						}catch(NumberFormatException e)
						{
							System.out.println("Enter a number ");
						}
						
						
						
						for (int i = 0 ; i  <pvp.availableUpdate(pvp.boardState).length;i++)
						{
							System.out.print(pvp.availableUpdate(pvp.boardState)[i]+"\t");
						}
						pvp.printBoard(pvp.boardState);
					}
				}
				else if(titleResult ==2)
				{
					//good luck with the pve mode 
					Node root = new Node();
//					System.out.println(root.availableUpdate(root.boardState).toString());
					Integer[] avilable = root.availableUpdate(root.boardState);
					Node[] child =new Node[avilable.length];
					for(int i = 0 ; i <avilable.length;i++) {
						System.out.print(avilable[i]+"\t");
						child[i] = new Node(avilable[i],root.boardState,root);
								
					}
					System.out.println("THE ROOT");
					root.printBoard(root.boardState);
					
					System.out.println("ROOT OVER");
					
					
					
					for (int i = 0 ; i < child.length; i++)
					{
						System.out.println("board with result "+avilable[i]);
						child[i].printBoard(child[i].boardState);
					}
					System.out.println();
					
				}
				else if(titleResult ==3)
				{
					
				}
				else if(titleResult ==0)
				{
					break;
				}
			}catch (NumberFormatException e)
			{
				System.out.println("Please enter an number that can be parse");
				
			}

		}
		
	}

}