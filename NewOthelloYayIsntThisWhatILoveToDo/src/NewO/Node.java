package NewO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Node {
	public int score;
	public int level ;
	public int[][] boardState;
	public  ArrayList<Integer> aviable = new ArrayList<Integer>();
	public ArrayList<Node> child = new ArrayList<Node>();
	public boolean turn= true;
	public static boolean avaiable1 ;
	public static boolean avaiable2 ;
	public static Integer[] arr= {0,3,4,5};
	public static Set<Integer> empty = new HashSet<Integer>(Arrays.asList( arr)) ;

	public Node(int [][] board)
	{
		//this function will never be call;
		
		this.score = 0;
		this.level = 0;
		this.boardState = board;
		avaiableUpdate(this.boardState);
		this.turn = true;
		System.out.print("");
	}

	
	
	
	
	
	
	
	
	
	
	//from this point on, old stuff
	public  Integer[] avaiableUpdate(int[][] board) {
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
						move.add(i*10+o);
					}
				} else if (board[i][o] == 4) {
					ava2++;
					if(!this.turn)
					{
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
		
		Integer [] result = new Integer[move.size()];
		move.toArray(result);
		
		return result;
	}

	public static int check(int row, int col,int[][] board) {
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

	public static int horCheck(int row, int col,int[][] board) {
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
	
	
	public static int checkLeft(int row, int col,int[][] board) {
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

	public static int checkRight(int row, int col,int[][] board) {
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

	
	
	
	
	
	
	public static int possibleSet(Set<Integer> possible) {
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

	
	public static int to34(int input) {

		// to check avaiable place's avaiable number
		if (input == 1) {
			return 3;
		} else if (input == 2) {
			return 4;
		} else {

			return 0;
		}
	}

	
	
	
}
