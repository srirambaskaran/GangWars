package org.gangwars.agent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;

import org.gangwars.agent.models.Cell;
import org.gangwars.agent.models.Game;
import org.gangwars.agent.models.GameBoard;
import org.gangwars.agent.models.Move;

public class homework {

	private String inputFile;
	private String outputFile;
	private String algorithm;
	private String yourPlay;
	private String depthString;
	private int n;
	private Game game;
	private GameBoard currentState;

	public homework(String inputFile, String outputFile) {
		super();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public void readInput() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		n = Integer.parseInt(reader.readLine()); // Number of rows and columns
													// in the board
		algorithm = reader.readLine();
		yourPlay = reader.readLine();
		depthString = reader.readLine();
		int[][] utilityValues = new int[n][n];
		this.currentState = new GameBoard();
		for (int i = 0; i < n; i++) {
			String[] tokens = reader.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				utilityValues[i][j] = Integer.parseInt(tokens[j]);
			}
		}

		for (int i = 0; i < n; i++) {
			String[] tokens = reader.readLine().split("");
			for (int j = 0; j < n; j++) {
				Cell cell = new Cell(i, j);
				cell.utilityValue = utilityValues[i][j];
				cell.cellValue = tokens[j].charAt(0);
				switch (tokens[j]) {
				case ".":
					currentState.addToEmpty(cell);
					break;
				case "X":
					currentState.addToXs(cell);
					break;
				case "O":
					currentState.addToOs(cell);
					break;
				}
			}
		}
		currentState.setNextPlay(yourPlay.charAt(0));
		currentState.setMyPlay(yourPlay.charAt(0));
		currentState.setDepth(0);
		Move move = new Move();
		Cell cell = new Cell(-1, -1);
		move.cell = cell;
		move.moveType = "start";
		currentState.setMove(move);
		currentState.calculateUtilitiesOfPlayer();
		this.game = new Game(algorithm, currentState, depthString, yourPlay.charAt(0));
		reader.close();
	}

	public void writeOutput(GameBoard gameBoard) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		String output = gameBoard.getMove().cell.toFormattedString() + " " + gameBoard.getMove().moveType + "\n"
				+ gameBoard.toString();
		writer.write(output);
//		System.out.println(output);
		writer.close();

	}

	public static void main(String[] args) throws Exception {
		String inputFile = "homework2/input.txt";
		String outputFile = "homework2/output.txt";
		homework homework = new homework(inputFile, outputFile);
		homework.readInput();
		long startTime = Calendar.getInstance().getTimeInMillis();
		homework.game.play();
		homework.writeOutput(homework.game.getNextMove());
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("Time taken: " + ((double) (endTime - startTime) / 1000.0) + " sec");
	}
}
