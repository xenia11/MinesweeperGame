package minesweeper;
import java.util.Scanner;

//enum GuessState {
//	Ok, AlreadyGuessed, Mine, OutOfBound, ExcessData, PotentialMine, GameIsFinished, PotentialMineSyntaxIncorrect
//}

public class Main {

	public static void main(String[] args) {

		Minesweeper myGame = new Minesweeper();
		myGame.showInstructions();

		Scanner scanner = new Scanner(System.in);

		String input = "";

		while (!input.toUpperCase().equals("Y")) {

			System.out.println("Do you want to start a game? (Y/N)");
			input = scanner.nextLine();

			if (input.toUpperCase().equals("Y")) {

				myGame.play();

				while (true) {
					System.out.println("");
					System.out.println("**Type \033[93;1m'quit'\033[0m to exit and \033[93;1m'restart'\033[0m to restart the game**");
					myGame.printResult();
					String inputNext = scanner.nextLine();

					// String response = checker.validateInput(inputNext);

					String[] inputs = inputNext.split(" ");

					switch (inputs.length) {
					case 1:
						quitAndRestart(inputs, myGame);
						break;
					case 2:
						coordinatesInput(inputs, myGame);
						break;
					case 3:
						insertFlagInCoordinates(inputs, myGame);
						break;
					default:
						System.out.println("wrong input");
					}

				}
			}
		}
		scanner.close();
	}

	private static void quitAndRestart(String[] inputs, Minesweeper myGame) {
		if (inputs[0].toLowerCase().equals("quit")) {
			myGame.quit();
		} else if (inputs[0].toLowerCase().equals("restart")) {
			myGame.play();
		} else {
			System.out.println("wrong input");
		}
	}

	private static void coordinatesInput(String[] inputs, Minesweeper myGame) {
		try {
		if (!isNumber(inputs[0]) || !isNumber(inputs[1])) {
			System.out.println("Not a number");
			return;
		}else if(!inBorders(Integer.parseInt(inputs[0]) - 1, Integer.parseInt(inputs[1]) - 1)) {
			System.out.println("Not in borders");
			Minesweeper.displayShowArr(myGame.getDisplayMatrix());
			return;
		}
		
		myGame.checkIfMine(Integer.parseInt(inputs[0]) - 1, Integer.parseInt(inputs[1]) - 1);
	   }catch (NumberFormatException e) {
	        System.out.println("Invalid input format. Please enter integers for coordinates.");
		}
		
		}

	private static void insertFlagInCoordinates(String[] inputs, Minesweeper myGame) {
		
		try {
			if (!isNumber(inputs[0]) || !isNumber(inputs[1])) {
				System.out.println("Not a number");
			}
			if (!inputs[2].toLowerCase().equals("f")) {
				System.out.println("Does not contain Flag parameter");
			}
			if (!inBorders(Integer.parseInt(inputs[0]) - 1, Integer.parseInt(inputs[1]) - 1)) {
				System.out.println("Not in borders");
				return;
			}
			
			 myGame.addFlag(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[1]));
		}catch (NumberFormatException e) {
	        System.out.println("Invalid input format. Please enter integers for coordinates.");
		}
	
	}

	private static boolean inBorders(int x, int y) {
		if (x < 0 || y < 0 || x >= 10 || y >= 10)
			return false;
		return true;
	}

	private static boolean isNumber(String str) {
		boolean numeric = true;

		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			numeric = false;
		}

		return numeric;
	}
}