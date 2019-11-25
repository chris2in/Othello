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
					pvp.printBoard(pvp.boardState);
					for(int i = 0 ; i <pvp.avaiableUpdate(pvp.boardState).length;i++) {
						System.out.print(pvp.avaiableUpdate(pvp.boardState)[i]+"\t");
					}
					System.out.println();
					while(true)
					{
						System.out.println();
					}
				}
				else if(titleResult ==2)
				{
					
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