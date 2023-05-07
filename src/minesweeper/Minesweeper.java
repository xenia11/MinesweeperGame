package minesweeper;
import java.util.Arrays;
import java.util.Random;
import java.util.*;

public class Minesweeper implements Game {
	protected static Integer[][] arr = new Integer[10][10];
	protected static Object[][] showArr = new Object[10][10];
	// up, right, down, left, downleft, downright, upright, upleft
	private static int dRow[] = { -1, 0, 1, 0, 1, 1, -1, -1 };
	private static int dCol[] = { 0, 1, 0, -1, -1, 1, 1, -1 };
	private int won = 0;
	private int lose = 0;

	public void play() {
		createMines();
		displayShowArr(showArr);
	};

	public void quit() {
		System.out.println("You quit the game");
		won = 0;
		lose = 0;
	};
	
	public void printResult() {
		System.out.println("So far you won: " + won + " times, and you lose: " + lose + " times.");
	}

	public void showInstructions() {

		System.out.println("\033[93;1mWelcome to Minesweeper!\n\nGame instructions:\n\nThe game board "
				+ "is a 10x10 grid of squares, represented by \"X\".\nSome squares contain "
				+ "a mine, represented by \"*\".\nTo win, uncover all squares that don't "
				+ "contain a mine.\n\n\nIf that square "
				+ "contains a mine, the game is over and you lose.\nIf it does not contain a mine, "
				+ "it will be uncovered and you can continue playing.\nEnter the coordinates of the"
				+ " square to uncover.\n\nThe first coordinate is the row number, and the second"
				+ "coordinate is the column number.\nFor example, to uncover the square in the "
				+ "top-left corner of the board, you would enter \"1 1\".\nIf it contains a number, "
				+ "it shows adjacent mines.\n\nMark potential mines with \"f\" after coordinates, for example, 5 5 f."
				+ "\n\nGood luck!\n\033[0m");
	};

	public void wonGame() {
		int count = 0;
		for (int i = 0; i < showArr.length; i++) {
		    for (int j = 0; j < showArr[i].length; j++) {
		        if ( showArr[i][j] == "F" || showArr[i][j] == "X") {
		            count++;
		        }
		    }
		}
		
		if(count == 15) {
			won++;
		}
	}

	public void createMines() {

		for (Integer[] row : arr) {
			Arrays.fill(row, 0);
		}

		Random random = new Random();
		int numMines = 15;
		int countMines = 0;

		while (countMines < numMines) {
			int row = random.nextInt(10);
			int col = random.nextInt(10);

			if (arr[row][col] != -1) {
				arr[row][col] = -1;
				countMines++;
			}
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] == -1) {
					if (i - 1 >= 0 && j - 1 >= 0 && arr[i - 1][j - 1] != -1)
						arr[i - 1][j - 1]++;
					if (i - 1 >= 0 && arr[i - 1][j] != -1)
						arr[i - 1][j]++;
					if (i - 1 >= 0 && j + 1 < arr.length && arr[i - 1][j + 1] != -1)
						arr[i - 1][j + 1]++;
					if (j - 1 >= 0 && arr[i][j - 1] != -1)
						arr[i][j - 1]++;
					if (j + 1 < arr.length && arr[i][j + 1] != -1)
						arr[i][j + 1]++;
					if (i + 1 < arr.length && j - 1 >= 0 && arr[i + 1][j - 1] != -1)
						arr[i + 1][j - 1]++;
					if (i + 1 < arr.length && arr[i + 1][j] != -1)
						arr[i + 1][j]++;
					if (i + 1 < arr.length && j + 1 < arr.length && arr[i + 1][j + 1] != -1)
						arr[i + 1][j + 1]++;
				}
			}
		}

		for (int i = 0; i < showArr.length; i++) {
			for (int j = 0; j < showArr[i].length; j++) {
				showArr[i][j] = "X";
			}
		}
	}

	public static <T> void displayShowArr(T[][] arr) {
		for (T[] row : arr) {
			for (T element : row) {
				if (element == "X")
					System.out.print(element + " ");
				else if (element == "F") {
					System.out.print("\033[94m" + element + " " + "\033[0m");
				} else {
					if (arr.getClass() == Integer[][].class && (Integer)element == -1) {
						System.out.print("\033[93;1m* \033[0m");	
					}else {
						System.out.print("\033[93;1m" + element + " " + "\033[0m");	
					}
					
				}
			}
			System.out.println();
		}
	}

	public void addFlag(int x, int y) {
		if(showArr[x - 1][y - 1] == "X") {
			showArr[x - 1][y - 1] = "F";
			displayShowArr(showArr);
		}
		displayShowArr(showArr);
		
		
	}

	public void checkIfMine(int coordinateOne, int coordinateTwo) {

		int current = arr[coordinateOne][coordinateTwo];
		showArr[coordinateOne][coordinateTwo] = current;
		if (current == -1) {
			System.out.println("You stepped onto mine. You lose. Maybe next time!");
			displayShowArr(arr);
			lose++;
			return;
		} else if (current == 0) {
			bfs(coordinateOne, coordinateTwo);
		}
		displayShowArr(showArr);
	}

	private void bfs(int row, int col) {
		boolean visited[][] = new boolean[10][10];

		Queue<Coordinate> q = new LinkedList<>();

		q.add(new Coordinate(row, col));
		visited[row][col] = true;

		while (!q.isEmpty()) {
			Coordinate cell = q.peek();
			int x = cell.first;
			int y = cell.second;

			showArr[x][y] = arr[x][y];

			q.remove();

			// Go to the adjacent cells
			for (int i = 0; i < 8; i++) {
				int adjx = x + dRow[i];
				int adjy = y + dCol[i];

				if (isValid(visited, arr, adjx, adjy)) {
					q.add(new Coordinate(adjx, adjy));
					visited[adjx][adjy] = true;
				}
			}
		}

	}

	private static boolean isValid(boolean[][] visited, Integer[][] arr, Integer row, Integer col) {

		// If cell lies out of bounds
		if (row < 0 || col < 0 || row >= visited.length || col >= visited[0].length)
			return false;

		// If cell is already visited
		if (visited[row][col])
			return false;

		if (arr[row][col] != 0 && arr[row][col] != -1) {
			showArr[row][col] = arr[row][col];
			return false;
		}

		// Otherwise
		return true;
	}

	static class Coordinate {
		int first, second;

		public Coordinate(int first, int second) {
			this.first = first;
			this.second = second;
		}
	}

	public Integer[][] getMatrix() {
		return Minesweeper.arr;
	}

	public Object[][] getDisplayMatrix() {
		return Minesweeper.showArr;
	}
}
