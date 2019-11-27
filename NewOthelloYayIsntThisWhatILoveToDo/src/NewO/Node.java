package NewO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Node {
	public int score;
	public int level ;
	public int[][] boardState= new int[8][8];
	public  ArrayList<Integer> aviable = new ArrayList<Integer>();
	public ArrayList<Node> child = new ArrayList<Node>();
	public boolean turn= true;
	public int count;
	public  boolean avaiable1 ;
	public  boolean avaiable2 ;
	public static Integer[] arr= {0,3,4,5};
	public static Set<Integer> empty = new HashSet<Integer>(Arrays.asList( arr)) ;
	
	
	public Node(int [][] board)
	{
		//this function will never be call;
		
		this.score = 0;
		this.level = 0;
		this.boardState = board;
		availableUpdate(this.boardState);
		this.turn = true;
		System.out.print("");
	}

	
	public Node()
	{
		//pvp only 
		gameStart(this.boardState);
		this.count = 4;
		
		
	}
	
	
	
	
	
	
	
	
	//from this point on, old stuff
	public  Integer[] availableUpdate(int[][] board) {
		Set<Integer> move = new HashSet<Integer>();
		int ava1 = 0;
		int ava2 = 0;
		for (int i = 0; i < board.length; i++) {
			for (int o = 0; o < board[0].length; o++) {
				if (board[i][o] != 1 && board[i][o] != 2) {

					board[i][o] = check(i, o,board);
				}
				if (board[i][o] == 3) {
					ava1++;
					
					if(this.turn)
					{
//						System.out.println("1 s turn");
						move.add(i*10+o);
					}
				} else if (board[i][o] == 4) {
					ava2++;
					if(!this.turn)
					{ 
//						System.out.println("2 s turn");
						move.add(i*10+o);
					}
				} else if (board[i][o] == 5) {
					ava1++;
					ava2++;
				}

			}

		}
		if (ava1 == 0) {
			this.avaiable1 = false;
		} else {
			this.avaiable1 = true;
		}

		if (ava2 == 0) {
			this.avaiable2 = false;
		} else {
			this.avaiable2 = true;
		}
		if (this.turn) {
			if (!avaiable1) {
				this.turn = false;
			}
		} else {
			if (!avaiable2) {
				this.turn = true;
			}
		}
//		System.out.println("move has "+move);
		Integer [] result = new Integer[move.size()];
		move.toArray(result);
		
		return result;
	}

	public  int check(int row, int col,int[][] board) {
		// a set that contains the possible move for one grid
		Set<Integer> collection = new HashSet<Integer>();
		if (board[row][col] != 1 || board[row][col] != 2) {

//			a check if any return 5 could saves time, but for now, this is fine

			// check horizontally
			collection.add(horCheck(row, col,board));

			// check vertically
			collection.add(verCheck(row, col,board));

			// check diagonially
			collection.add(diaCheck(row, col,board));

		}

		// possibleSet will return the number that is best describe this possible
		// for the gird we are checking
		return possibleSet(collection);

	}

	public  int horCheck(int row, int col,int[][] board) {
		Set<Integer> possible = new HashSet<Integer>();

		// if on the left most col, check right
		if (col == 0) {
			possible.add(checkRight(row, col,board));
		} else if (col == board.length - 1) {
			// if on the right most col, check left
			possible.add(checkLeft(row, col,board));
		} else {

			// else, check both side
			possible.add(checkLeft(row, col,board));
			possible.add(checkRight(row, col,board));
		}
		return possibleSet(possible);
	}
	
	
	public  int checkLeft(int row, int col,int[][] board) {
		int iter = 0;
		// if the one on left is empty or same to current, then there is no possible
		// move for it
		if (empty.contains(board[row][col - 1]) || (board[row][col] == board[row][col - 1])) {
			return 0;
		} else {
			int temp = board[row][col - 1];
			iter = 0;
			// traversing leftward
			while (col - 1 - iter >= 0) {
				if (temp != board[row][col - 1 - iter]) {
					if (empty.contains(board[row][col - 1 - iter])) {
						return 0;
					} else {
						return to34(board[row][col - 1 - iter]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	public  int checkRight(int row, int col,int[][] board) {
		int iter = 0;
		// if the one on right is empty or same to current, then there is no possible
		// move for it
		if (empty.contains(board[row][col + 1]) || (board[row][col] == board[row][col + 1])) {
			return 0;
		} else {
//			initial a temp value 
			int temp = board[row][col + 1];
			iter = 0;
			// traversing rightward
			while (col + 1 + iter < board.length) {
				// if next one is different
				if (temp != board[row][col + 1 + iter]) {
					if (empty.contains(board[row][col + 1 + iter])) {
						// return 0 if next one is empty
						return 0;
					} else {
						// else return whatever next one is avaiable
//						to34 will convert them into correct state 
						return to34(board[row][col + 1 + iter]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	public static int verCheck(int row, int col,int[][] board) {
		Set<Integer> possible = new HashSet<Integer>();

//		if on the top row, check down
		if (row == 0) {

			possible.add(checkDown(row, col,board));
		} else if (row == board.length - 1) {
//			if on the bottom row, check up
			possible.add(checkUp(row, col,board));
		} else {
//			if on the middle, check both side 
			possible.add(checkDown(row, col,board));
			possible.add(checkUp(row, col,board));
		}
		return possibleSet(possible);
	}

	public static int checkUp(int row, int col,int[][] board) {
		int iter = 0;
		// if the one on up is empty or same to current, then there is no possible move
		// for it
		if (empty.contains(board[row - 1][col]) || (board[row][col] == board[row - 1][col])) {
			return 0;
		} else {
			int temp = board[row - 1][col];
			iter = 0;
			// traversing upward
			while (row - 1 - iter >= 0) {
				if (temp != board[row - 1 - iter][col]) {
					if (empty.contains(board[row - 1 - iter][col])) {
						return 0;
					} else {
						return to34(board[row - 1 - iter][col]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	
	public static int checkDown(int row, int col,int[][] board) {
		int iter = 0;
		// if the one on down is empty or same to current, then there is no possible
		// move for it
		if (empty.contains(board[row + 1][col]) || (board[row][col] == board[row + 1][col])) {
			return 0;
		} else {
			int temp = board[row + 1][col];
			iter = 0;
			// traversing downward
			while (row + 1 + iter < board.length) {

				if (temp != board[row + 1 + iter][col]) {
					if (empty.contains(board[row + 1 + iter][col])) {
						return 0;
					} else {
						return to34(board[row + 1 + iter][col]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	public static int diaCheck(int row, int col,int[][] board) {
		Set<Integer> possible = new HashSet<Integer>();
		if (col == 0) {
//			if on the left most col
			if (row == 0) {
//				if on the top left corner
				possible.add(checkDownRight(row, col,board));
			} else if (row == board.length - 1) {
//				or if on the bottom left corner 
				possible.add(checkUpRight(row, col,board));
			} else {
//				else just in middle of left most col
				possible.add(checkUpRight(row, col,board));
				possible.add(checkDownRight(row, col,board));
			}

		} else if (col == board.length - 1) {
//			 if in right most col
			if (row == 0) {
//				if on top right corner
				possible.add(checkDownLeft(row, col,board));
			} else if (row == board.length - 1) {
//				if on bottom right corner				
				possible.add(checkUpLeft(row, col,board));
			} else {
//				else just in middle of right most col 				
				possible.add(checkUpLeft(row, col,board));
				possible.add(checkDownLeft(row, col,board));
			}
		} else if (row == 0) {
//			then if in the top row
//			 no need to check for corners anymore
			possible.add(checkDownLeft(row, col,board));
			possible.add(checkDownRight(row, col,board));
		} else if (row == board.length - 1) {
//			if on the bottom row
			possible.add(checkUpLeft(row, col,board));
			possible.add(checkUpRight(row, col,board));
		} else {

//			else checking all direction
			possible.add(checkDownRight(row, col,board));
			possible.add(checkDownLeft(row, col,board));
			possible.add(checkUpRight(row, col,board));
			possible.add(checkUpLeft(row, col,board));
		}
		return possibleSet(possible);
	}

	
	
	public static int checkUpLeft(int row, int col,int[][] board) {
		int iter = 0;
		if (empty.contains(board[row - 1][col - 1]) || (board[row][col] == board[row - 1][col - 1])) {
			return 0;
		} else {
			// traversing upleftward
			int temp = board[row - 1][col - 1];
			iter = 0;
			while (row - 1 - iter >= 0 && col - 1 - iter >= 0) {
				if (temp != board[row - 1 - iter][col - 1 - iter]) {
					if (empty.contains(board[row - 1 - iter][col - 1 - iter])) {
						return 0;
					} else {
						return to34(board[row - 1 - iter][col - 1 - iter]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	
	public static int checkUpRight(int row, int col,int[][] board) {
		int iter = 0;
		if (empty.contains(board[row - 1][col + 1]) || (board[row][col] == board[row - 1][col + 1])) {
			return 0;
		} else {
			int temp = board[row - 1][col + 1];
			iter = 0;
			// traversing uprightward
			while (row - 1 - iter >= 0 && col + 1 + iter < board.length) {
				if (temp != board[row - 1 - iter][col + 1 + iter]) {
					if (empty.contains(board[row - 1 - iter][col + 1 + iter])) {
						return 0;
					} else {
						return to34(board[row - 1 - iter][col + 1 + iter]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	
	public static int checkDownLeft(int row, int col,int[][] board) {
		int iter = 0;
		if (empty.contains(board[row + 1][col - 1]) || (board[row][col] == board[row + 1][col - 1])) {
			return 0;
		} else {
			int temp = board[row + 1][col - 1];
			iter = 0;
			// traversing downleftward
			while (row + 1 + iter < board.length && col - 1 - iter >= 0) {
				if (temp != board[row + 1 + iter][col - 1 - iter]) {
					if (empty.contains(board[row + 1 + iter][col - 1 - iter])) {
						return 0;
					} else {
						return to34(board[row + 1 + iter][col - 1 - iter]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	public static int checkDownRight(int row, int col,int[][] board) {
		int iter = 0;
		if (empty.contains(board[row + 1][col + 1]) || (board[row][col] == board[row + 1][col + 1])) {
			return 0;
		} else {
			int temp = board[row + 1][col + 1];
			iter = 0;
			while (row + 1 + iter < board.length && col + 1 + iter < board.length) {
				if (temp != board[row + 1 + iter][col + 1 + iter]) {
					if (empty.contains(board[row + 1 + iter][col + 1 + iter])) {
						return 0;
					} else {
						return to34(board[row + 1 + iter][col + 1 + iter]);
					}
				}
				iter++;
			}
			return 0;
		}

	}

	
	
	
	
	
	
	public static  int possibleSet(Set<Integer> possible) {
		if (possible.contains(5) || (possible.contains(3) && possible.contains(4))) {
			// where both 1 and 2 is aviable
			return 5;
		} else if (possible.contains(4)) {
			// only 2 is aviable in this grid
			return 4;
		} else if (possible.contains(3)) {
			// only 1 is avialbe in this grid
			return 3;
		} else {
			// none
			return 0;
		}
	}

	
	public static  int to34(int input) {

		// to check avaiable place's avaiable number
		if (input == 1) {
			return 3;
		} else if (input == 2) {
			return 4;
		} else {

			return 0;
		}
	}

	public  void gameStart(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int o = 0; o < board[0].length; o++) {
				board[i][o] = 0;
			}
		}

		// standard beginning

		board[3][3] = 2;
		board[4][4] = 2;
		board[3][4] = 1;
		board[4][3] = 1;
	}
	
	public  void printBoard(int[][] board)
	{
		for(int i = 0 ; i <board.length;i++) {
			for(int o = 0 ; o <board[0].length;o++) {
				System.out.print(board[i][o]+"\t");
			}
			System.out.println("\n");
		}
	}
	
	public int place (int row, int col , int[][] board ,boolean turn)
	{
		int numOfFlipMade= 0;
		if (row < board.length && row >= 0 && col < board.length && col >= 0) {

			if (empty.contains(board[row][col])) {

				if (this.turn) {

					if (board[row][col] == 5 || board[row][col] == 3) {
						board[row][col] = 1;
//						System.out.println(TheBoard[row][col]);
						this.turn = false;
						count++;
					} else {
						System.out.println("You cant put it here1");
					}

				} else {
					if (board[row][col] == 5 || board[row][col] == 4) {
						board[row][col] = 2;
						this.turn = true;
						count++;
					} else {
						System.out.println("You cant put it here2");
					}
					
				}

				numOfFlipMade = flip(row, col,board);

			} else {
				System.out.println("invalid Move");
			}

		} else {

			System.out.println("The TheBoard size is " + board.length
					+ ". Please input a postive integer that is less than it and ensure it is in the aviable move");
		}
		return numOfFlipMade;
	}
	
	public static int flip(int row, int col,int[][] TheBoard) {
		int iter = 0;
		int count = 0;
		// checking leftward
		while (col - iter >= 0) {
			if (TheBoard[row][col - iter] != TheBoard[row][col] && !empty.contains(TheBoard[row][col - iter])) {

			} else if (TheBoard[row][col - iter] == TheBoard[row][col] && iter >= 1) {
				for (int i = 1; i < iter; i++) {
					TheBoard[row][col - i] = TheBoard[row][col];
					count++;
				}
				break;
			} else if (empty.contains(TheBoard[row][col - iter])) {
				break;
			}
			iter++;
		}

		// checking rightward
		iter = 0;
		while (col + iter < TheBoard.length) {

			if (TheBoard[row][col + iter] != TheBoard[row][col] && !empty.contains(TheBoard[row][col + iter])) {

			} else if (TheBoard[row][col + iter] == TheBoard[row][col] && iter >= 1) {

				for (int i = 1; i < iter; i++) {

					TheBoard[row][col + i] = TheBoard[row][col];
					count++;
				}
				break;
			} else if (empty.contains(TheBoard[row][col + iter])) {
				break;
			}
			iter++;
		}

		// checking downward
		iter = 0;
		while (row + iter < TheBoard.length) {

			if (TheBoard[row + iter][col] != TheBoard[row][col] && !empty.contains(TheBoard[row + iter][col])) {

			} else if (TheBoard[row + iter][col] == TheBoard[row][col] && iter >= 1) {

				for (int i = 1; i < iter; i++) {

					TheBoard[row + i][col] = TheBoard[row][col];
					count++;
				}
				break;
			} else if (empty.contains(TheBoard[row + iter][col])) {
				break;
			}
			iter++;
		}

		// checking uptward
		iter = 0;
		while (row - iter >= 0) {

			if (TheBoard[row - iter][col] != TheBoard[row][col] && !empty.contains(TheBoard[row - iter][col])) {

			} else if (TheBoard[row - iter][col] == TheBoard[row][col] && iter >= 1) {

				for (int i = 1; i < iter; i++) {

					TheBoard[row - i][col] = TheBoard[row][col];
					count++;
				}
				break;
			} else if (empty.contains(TheBoard[row - iter][col])) {
				break;
			}
			iter++;
		}

		// continue with upleft, upright, down left, down right
		// upleft
		iter = 0;
		while (row - iter >= 0 && col - iter >= 0) {
			if (TheBoard[row - iter][col - iter] != TheBoard[row][col] && !empty.contains(TheBoard[row - iter][col - iter])) {

			} else if (TheBoard[row - iter][col - iter] == TheBoard[row][col] && iter >= 1) {
				for (int i = 1; i < iter; i++) {
					TheBoard[row - i][col - i] = TheBoard[row][col];
					count++;
				}
				break;
			} else if (empty.contains(TheBoard[row - +iter][col - +iter])) {
				break;
			}

			iter++;
		}

		// upright
		iter = 0;
		while (row - iter >= 0 && col + iter < TheBoard.length) {
			if (TheBoard[row - iter][col + iter] != TheBoard[row][col] && !empty.contains(TheBoard[row - iter][col + iter])) {

			} else if (TheBoard[row - iter][col + iter] == TheBoard[row][col] && iter >= 1) {
				for (int i = 1; i < iter; i++) {
					TheBoard[row - i][col + i] = TheBoard[row][col];
					count++;
				}
				break;
			} else if (empty.contains(TheBoard[row - +iter][col + iter])) {
				break;
			}

			iter++;
		}

		// leftdown
		iter = 0;
		while (row + iter < TheBoard.length && col - iter >= 0) {
			if (TheBoard[row + iter][col - iter] != TheBoard[row][col] && !empty.contains(TheBoard[row + iter][col - iter])) {

			} else if (TheBoard[row + iter][col - iter] == TheBoard[row][col] && iter >= 1) {
				for (int i = 1; i < iter; i++) {
					TheBoard[row + i][col - i] = TheBoard[row][col];
					count++;
				}
				break;
			} else if (empty.contains(TheBoard[row + iter][col - iter])) {
				break;
			}

			iter++;
		}

		// rightdown
		iter = 0;
		while (row + iter < TheBoard.length && col + iter < TheBoard.length) {
			if (TheBoard[row + iter][col + iter] != TheBoard[row][col] && !empty.contains(TheBoard[row + iter][col + iter])) {

			} else if (TheBoard[row + iter][col + iter] == TheBoard[row][col] && iter >= 1) {
				for (int i = 1; i < iter; i++) {
					TheBoard[row + i][col + i] = TheBoard[row][col];
					count++;
				}

				break;
			} else if (empty.contains(TheBoard[row + iter][col + iter])) {
				break;
			}

			iter++;
		}
		if(row==2 && col ==2)
		{
			System.out.println(count);
		}
		return count;

	}
	
}
