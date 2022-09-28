import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TicTacToe extends Application {

	boolean currTurn;
	boolean gameCont;
	boolean inGame = true;
	Text winNote;
	Button reset;
	Button close;
	Line line = new Line();
	Tile[][] borad = new Tile[3][3];
	List<Combo> combos = new ArrayList<>();
	Pane scene = new Pane();
	//TextField tField = new TextField("random");// Two players//hard//random
	ComboBox<String> cobo = new ComboBox<>();

	Text text1 = new Text("Choose the type of game : ");

	public static void main(String[] args) {
		launch(args);
	}

	public void checkState() {
		for (Combo com : combos) {
			if (com.isCompleate()) {
				playWin(com);
				break;
			}
		}
	}

	public void playWin(Combo com) {
		line.setStartX(com.tiles[0].getCenterX());
		line.setStartY(com.tiles[0].getCenterY());
		line.setEndX(com.tiles[0].getCenterX());
		line.setEndY(com.tiles[0].getCenterY());
		line.setStroke(Color.BLUE);
		line.setStrokeWidth(5);
		scene.getChildren().add(line);

		Timeline timeline = new Timeline();
		timeline.getKeyFrames()
				.add(new KeyFrame(Duration.seconds(1), new KeyValue(line.endXProperty(), com.tiles[2].getCenterX()),
						new KeyValue(line.endYProperty(), com.tiles[2].getCenterY())));
		timeline.play();

	}

	public class Combo {
		Tile[] tiles;

		public Combo(Tile... tiles) {
			this.tiles = tiles;
		}

		public boolean isCompleate() {
			if (tiles[0].getValue().isEmpty()) {
				return false;
			}
			return tiles[0].getValue().equals(tiles[1].getValue()) && tiles[0].getValue().equals(tiles[2].getValue());
		}
	}

	public void start(Stage primaryStage) {
		Label l = new Label("TicTacToe");
		l.setFont(new Font(50));
		l.setLayoutX(600);
		l.setLayoutY(0);
		scene.getChildren().add(l);

		HBox box = new HBox();
		
		
		cobo.getItems().addAll("1. Two players", "2. random", "3. hard");
		cobo.setValue("1. Two players");

		box.getChildren().addAll(text1, cobo);
		box.setSpacing(20);
		box.setLayoutX(900);
		box.setLayoutY(80);
		scene.getChildren().add(box);

		gameCont = true;
		currTurn = false;

		winNote = new Text("");
		winNote.setLayoutX(970);
		winNote.setLayoutY(145);
		
		

		reset = new Button("Reset");
		reset.setLayoutX(900);
		reset.setLayoutY(130);
		reset.setOnAction(e4 -> {
				winNote.setText("New Game Started");
				//System.out.println(cobo.getSelectionModel().select(0));
				//System.out.println(cobo.getValue());

				gameCont = true;
				currTurn = false;
				borad[0][0].text.setText("");
				borad[0][1].text.setText("");
				borad[0][2].text.setText("");
				borad[1][0].text.setText("");
				borad[1][1].text.setText("");
				borad[1][2].text.setText("");
				borad[2][0].text.setText("");
				borad[2][1].text.setText("");
				borad[2][2].text.setText("");
				//tField.setText("");
				//cobo.getItems().setAll("");
				scene.getChildren().remove(line);
		}
		);

		close = new Button("Exit");
		close.setLayoutX(900);
		close.setLayoutY(170);
		close.setOnAction(e5 ->{
				primaryStage.close();
		});

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if ((j == 0 && i == 0) || (j == 0 && i == 2) || (j == 1 && i == 1) || (j == 2 && i == 0)
						|| (j == 2 && i == 2)) {
					Tile tile = new Tile(Color.LIGHTGREEN);
					tile.setTranslateX(j * 200);
					tile.setTranslateY(i * 200);
					tile.setLayoutX(50);
					tile.setLayoutY(70);
					scene.getChildren().add(tile);
					borad[j][i] = tile;
				} else {
					Tile tile = new Tile(Color.LIGHTGREY);
					tile.setTranslateX(j * 200);
					tile.setTranslateY(i * 200);
					tile.setLayoutX(50);
					tile.setLayoutY(70);
					scene.getChildren().add(tile);
					borad[j][i] = tile;
				}

			}
		}

		combos.add(new Combo(borad[0][0], borad[1][0], borad[2][0]));
		combos.add(new Combo(borad[0][1], borad[1][1], borad[2][1]));
		combos.add(new Combo(borad[0][2], borad[1][2], borad[2][2]));
		combos.add(new Combo(borad[0][0], borad[0][1], borad[0][2]));
		combos.add(new Combo(borad[1][0], borad[1][1], borad[1][2]));
		combos.add(new Combo(borad[2][0], borad[2][1], borad[2][2]));
		combos.add(new Combo(borad[0][0], borad[1][1], borad[2][2]));
		combos.add(new Combo(borad[0][2], borad[1][1], borad[2][0]));

		primaryStage.setTitle("4th Proj");
		primaryStage.setScene(new Scene(scene, 600, 700));
		scene.getChildren().addAll(reset, close, winNote);
		primaryStage.show();
	}

	public void random_ai(int player) {

		Random random = new Random();
		int choice;
		do {
			choice = random.nextInt(9);
		} while (borad[choice / 3][choice % 3].text.getText() != "");

		String symbol = "O";

		switch (player) {
		case 1:
			symbol = "X";
			break;
		case 2:
			symbol = "O";
			break;
		}

		borad[choice / 3][choice % 3].text.setText(symbol);
	}

	private class Tile extends StackPane {
		private Text text = new Text();

		public Tile(Color color) {
			text.setFont(Font.font("Calibri", 80));
			Rectangle border = new Rectangle(200, 200);
			border.setFill(color);
			border.setStroke(Color.BLACK);
			getChildren().addAll(border, text);
			
       

			setOnMouseClicked(event -> {
				if (cobo.getValue().equals("2. random") ) {
					if (event.getButton() == MouseButton.PRIMARY) {
						if (gameCont == true) {
							if (text.getText().isEmpty()) {
								if (currTurn == false) {
									text.setText("X");
									currTurn = true;
								}
								checkState();

								// CHECKS FOR ROWS
								if (borad[0][0].text.getText().equals("X") && borad[0][1].text.getText().equals("X")
										&& borad[0][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");

								} else if (borad[0][0].text.getText().equals("O")
										&& borad[0][1].text.getText().equals("O")
										&& borad[0][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[1][0].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[1][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[1][0].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[1][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[2][0].text.getText().equals("X")
										&& borad[2][1].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[2][0].text.getText().equals("O")
										&& borad[2][1].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// CHECKS FOR COLUMNS
								else if (borad[0][0].text.getText().equals("X")
										&& borad[1][0].text.getText().equals("X")
										&& borad[2][0].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][0].text.getText().equals("O")
										&& borad[1][0].text.getText().equals("O")
										&& borad[2][0].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][1].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][1].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][1].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][1].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][2].text.getText().equals("X")
										&& borad[1][2].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][2].text.getText().equals("O")
										&& borad[1][2].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// CHECKS FOR DIAGONALS
								else if (borad[0][0].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][0].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][2].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][0].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][2].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][0].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// ENDS THE GAME IF SOMEONE WINS
								if (winNote.getText().equals("X Wins!") || winNote.getText().equals("O Wins!"))
									gameCont = false;
								// THE DRAW CHECK, ENDS THE GAME IF PASSED
								else if (!(borad[0][0].text.getText().equals(""))
										&& !(borad[0][1].text.getText().equals(""))
										&& !(borad[0][2].text.getText().equals(""))
										&& !(borad[1][0].text.getText().equals(""))
										&& !(borad[1][1].text.getText().equals(""))
										&& !(borad[1][2].text.getText().equals(""))
										&& !(borad[2][0].text.getText().equals(""))
										&& !(borad[2][1].text.getText().equals(""))
										&& !(borad[2][2].text.getText().equals(""))) {
									winNote.setText("Draw!");
									gameCont = false;
								}
							}
						}
					}
					if (currTurn == true && gameCont == true) {
						random_ai(2);
						currTurn = false;
						checkState();
					}
				} else if (cobo.getValue().equals("1. Two players")) {
					if (event.getButton() == MouseButton.PRIMARY) {
						if (gameCont == true) {
							if (text.getText().isEmpty()) {
								if (currTurn == false) {
									text.setText("X");
									currTurn = true;
								} else {
									text.setText("O");
									currTurn = false;
								}
								checkState();

								// CHECKS FOR ROWS
								if (borad[0][0].text.getText().equals("X") && borad[0][1].text.getText().equals("X")
										&& borad[0][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");

								} else if (borad[0][0].text.getText().equals("O")
										&& borad[0][1].text.getText().equals("O")
										&& borad[0][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[1][0].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[1][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[1][0].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[1][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[2][0].text.getText().equals("X")
										&& borad[2][1].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[2][0].text.getText().equals("O")
										&& borad[2][1].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// CHECKS FOR COLUMNS
								else if (borad[0][0].text.getText().equals("X")
										&& borad[1][0].text.getText().equals("X")
										&& borad[2][0].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][0].text.getText().equals("O")
										&& borad[1][0].text.getText().equals("O")
										&& borad[2][0].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][1].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][1].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][1].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][1].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][2].text.getText().equals("X")
										&& borad[1][2].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][2].text.getText().equals("O")
										&& borad[1][2].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// CHECKS FOR DIAGONALS
								else if (borad[0][0].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][0].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][2].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][0].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][2].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][0].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// ENDS THE GAME IF SOMEONE WINS
								if (winNote.getText().equals("X Wins!") || winNote.getText().equals("O Wins!"))
									gameCont = false;
								// THE DRAW CHECK, ENDS THE GAME IF PASSED
								else if (!(borad[0][0].text.getText().equals(""))
										&& !(borad[0][1].text.getText().equals(""))
										&& !(borad[0][2].text.getText().equals(""))
										&& !(borad[1][0].text.getText().equals(""))
										&& !(borad[1][1].text.getText().equals(""))
										&& !(borad[1][2].text.getText().equals(""))
										&& !(borad[2][0].text.getText().equals(""))
										&& !(borad[2][1].text.getText().equals(""))
										&& !(borad[2][2].text.getText().equals(""))) {
									winNote.setText("Draw!");
									gameCont = false;
								}
							}
						}
					}
				}

				else if (cobo.getValue().equals("3. hard")) {
					if (event.getButton() == MouseButton.PRIMARY) {
						if (gameCont == true) {
							if (text.getText().isEmpty()) {
								if (currTurn == false) {
									text.setText("X");
									currTurn = true;
								}

								checkState();

								// CHECKS FOR ROWS
								if (borad[0][0].text.getText().equals("X") && borad[0][1].text.getText().equals("X")
										&& borad[0][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");

								} else if (borad[0][0].text.getText().equals("O")
										&& borad[0][1].text.getText().equals("O")
										&& borad[0][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[1][0].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[1][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[1][0].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[1][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[2][0].text.getText().equals("X")
										&& borad[2][1].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[2][0].text.getText().equals("O")
										&& borad[2][1].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// CHECKS FOR COLUMNS
								else if (borad[0][0].text.getText().equals("X")
										&& borad[1][0].text.getText().equals("X")
										&& borad[2][0].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][0].text.getText().equals("O")
										&& borad[1][0].text.getText().equals("O")
										&& borad[2][0].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][1].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][1].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][1].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][1].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][2].text.getText().equals("X")
										&& borad[1][2].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][2].text.getText().equals("O")
										&& borad[1][2].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// CHECKS FOR DIAGONALS
								else if (borad[0][0].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][2].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][0].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][2].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								} else if (borad[0][2].text.getText().equals("X")
										&& borad[1][1].text.getText().equals("X")
										&& borad[2][0].text.getText().equals("X")) {
									winNote.setText("X Wins!");
								} else if (borad[0][2].text.getText().equals("O")
										&& borad[1][1].text.getText().equals("O")
										&& borad[2][0].text.getText().equals("O")) {
									winNote.setText("O Wins!");
								}
								// ENDS THE GAME IF SOMEONE WINS
								if (winNote.getText().equals("X Wins!") || winNote.getText().equals("O Wins!"))
									gameCont = false;
								// THE DRAW CHECK, ENDS THE GAME IF PASSED
								else if (!(borad[0][0].text.getText().equals(""))
										&& !(borad[0][1].text.getText().equals(""))
										&& !(borad[0][2].text.getText().equals(""))
										&& !(borad[1][0].text.getText().equals(""))
										&& !(borad[1][1].text.getText().equals(""))
										&& !(borad[1][2].text.getText().equals(""))
										&& !(borad[2][0].text.getText().equals(""))
										&& !(borad[2][1].text.getText().equals(""))
										&& !(borad[2][2].text.getText().equals(""))) {
									winNote.setText("Draw!");
									gameCont = false;
								}
							}
						}
					}
					if (currTurn == true && gameCont == true) {
						hardMove(2);
						currTurn = false;
						checkState();
					}
				}

			});

		}

		public double getCenterX() {
			return getTranslateX() + 157;
		}

		public double getCenterY() {
			return getTranslateY() + 150;
		}

		public String getValue() {
			return text.getText();
		}

	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public void hardMove(int player) {

		String symbol = "";
		if (player == 1) {
			symbol = "X";
		} else if (player == 2) {
			symbol = "O";
		}
		Move bestMove = minimax(player, player);
		borad[bestMove.index[0]][bestMove.index[1]].text.setText(symbol);
	}

	public Move minimax(int callingPlayer, int currentPlayer) {
		String enemySymbol = "";
		String callingSymbol = "";
		if (callingPlayer == 1) {
			callingSymbol = "X";
			enemySymbol = "O";
		} else if (callingPlayer == 2) {
			callingSymbol = "O";
			enemySymbol = "X";
		}

		String symbol = "";
		int enemyNumber = 0;
		if (currentPlayer == 1) {
			symbol = "X";
			enemyNumber = 2;
		} else if (currentPlayer == 2) {
			symbol = "O";
			enemyNumber = 1;
		}

		// find available spots
		int[][] availableSpots = emptyIndexes();

		if (winning(enemySymbol)) {
			return new Move(-10);
		} else if (winning(callingSymbol)) {
			return new Move(10);
		} else if (!areThereEmptyIndexes()) {
			return new Move(0);
		}

		// making the list of possible moves
		// moves.add(thingToAdd) appends to the list
		// moves.get(index) to get an element from the list
		List<Move> moves = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (availableSpots[i][j] == 1) {
					// let's make a possible move
					Move move = new Move();
					move.index = new int[] { i, j };
					borad[i][j].text.setText(symbol);
					Move result = minimax(callingPlayer, enemyNumber);
					// save the score for the minimax
					move.score = result.score;
					// then revert the occupied place back to empty, so next guesses can go on
					borad[i][j].text.setText("");
					;
					moves.add(move);
				}
			}
		}

		// when the moves loop has ended, choose the move with the highest score
		int bestMove = 0;

		if (currentPlayer == callingPlayer) {
			int bestScore = -10000;
			for (int i = 0; i < moves.size(); i++) {
				if (moves.get(i).score > bestScore) {
					bestScore = moves.get(i).score;
					bestMove = i;
				}
			}
		} else {
			int bestScore = 10000;
			for (int i = 0; i < moves.size(); i++) {
				if (moves.get(i).score < bestScore) {
					bestScore = moves.get(i).score;
					bestMove = i;
				}
			}
		}

		// minimax returns the best move to the latest function caller
		return moves.get(bestMove);
	}

	public int[][] emptyIndexes() {
		int[][] empties = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (borad[i][j].text.getText().equals("")) {
					empties[i][j] = 1;
				} else {
					empties[i][j] = 0;
				}
			}
		}
		return empties;
	}

	public boolean areThereEmptyIndexes() {
		boolean empties = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (borad[i][j].text.getText().equals("")) {
					empties = true;
				}
			}
		}
		return empties;
	}

	public boolean winning(String player) {
		return (borad[0][0].text.getText().equals(player) && borad[0][1].text.getText().equals(player)
				&& borad[0][2].text.getText().equals(player))
				|| (borad[1][0].text.getText().equals(player) && borad[1][1].text.getText().equals(player)
						&& borad[1][2].text.getText().equals(player))
				|| (borad[2][0].text.getText().equals(player) && borad[2][1].text.getText().equals(player)
						&& borad[2][2].text.getText().equals(player))
				|| (borad[0][0].text.getText().equals(player) && borad[1][0].text.getText().equals(player)
						&& borad[2][0].text.getText().equals(player))
				|| (borad[0][1].text.getText().equals(player) && borad[1][1].text.getText().equals(player)
						&& borad[2][1].text.getText().equals(player))
				|| (borad[0][2].text.getText().equals(player) && borad[1][2].text.getText().equals(player)
						&& borad[2][2].text.getText().equals(player))
				|| (borad[0][0].text.getText().equals(player) && borad[1][1].text.getText().equals(player)
						&& borad[2][2].text.getText().equals(player))
				|| (borad[0][2].text.getText().equals(player) && borad[1][1].text.getText().equals(player)
						&& borad[2][0].text.getText().equals(player));
	}

	class Move {
		int[] index;
		int score;

		Move() {

		}

		Move(int s) {
			score = s;
		}
	}

}
