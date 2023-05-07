package test;

import minesweeper.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MinesweeperTest {

	@Test
	public void addFlagShouldAddFlagToCoordinates() {
		Minesweeper game = new Minesweeper();

		// add a flag to a cell
		game.addFlag(1, 1);
		assertEquals("F", game.getDisplayMatrix()[0][0]);

		// add another flag to a different cell
		game.addFlag(5, 5);
		assertEquals("F", game.getDisplayMatrix()[4][4]);

		// try to add a flag to a cell that has already been flagged
		game.addFlag(1, 1);
		assertEquals("F", game.getDisplayMatrix()[0][0]);

	}

	@Test
	public void playShouldSetUpMatrixCorrectly() {
		Minesweeper game = new Minesweeper();
		game.play();

		var res = game.getMatrix();

		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) {
				assertNotNull(res[i][j]);
			}
		}
	}

	@Test
	public void numberOfMinesShouldReturnFifteen() {
		Minesweeper game = new Minesweeper();
		game.createMines();
		var matrix = game.getMatrix();
		int numMines = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == -1) {
					numMines++;
				}
			}
		}
		assertEquals(15, numMines);

	}
}