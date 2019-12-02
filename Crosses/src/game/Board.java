package game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Board extends JFrame implements ActionListener { //Board is acting as a JFrame. A weird mutated child. All the methods like add (and the constructor called using super) are from the JFrame class
	private ArrayList<Quadrant> buttons; //Quadrant is a child of JButton with a specific constructor
	private boolean winner;
	
	private ActionButton quitter, goBackButton, resetButton;
	
	private String difficulty;
	
	public Menu menu;
	
	public String getDifficulty() {
		return difficulty;
	}
	
	public Board(Menu m, int res,String playType) { //calls constructor of JFrame and sets properties to act as a JFrame
		super("Crosses -- Board");
		menu = m;
		winner = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize((int)(res * 0.75),res);
		setResizable(false);
		difficulty = playType;
		setLayout(new GridLayout(4,3)); //grid layout of a JFrame allows you to choose a number of rows and cols and it will size added components according to the window size
		initButtons();
	}

	private void resetBoard() {
		for (int i = 0; i < bVals.length;i++) {
			bVals[i] = "";
		}
		System.out.print("BVals after reset: "); printBVal();
		winner = false;
		if (alternate  > -1) {
			alternate = 0;
		}
		for (Quadrant b : buttons) {
			b.setText("");
		}
	}
	
	private void initButtons() { //defines buttons as a array of 9 buttons to have a reference to them. Creates 9 quadrants then adds them to the array and the frame
		buttons = new ArrayList<Quadrant>();
		for (int i = 0;i < 9;i++) {
			//make sure you create a reference to all of your Quadrants in an array
			buttons.add(new Quadrant(difficulty, this));
			add(buttons.get(i)); //adds this button to the frame. has reference to the button array* 
		}
		
		quitter = new ActionButton();
		add(quitter);
		
		goBackButton = new ActionButton("GO BACK",new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("SHOWING THE MENU");
				setVisible(false);
				menu.setVisible(true);
			}
		}, new Color(0,255,0));
		add(goBackButton);
		
		resetButton = new ActionButton("RESET BOARD", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Reset board");
				resetBoard();
			}
		}, Color.black);
		add(resetButton);
	}
	
	private int alternate; // if actionCommand is 2-PLAYERS then use this to track turns, etc.. If not, leave it blank
	@Override
	public void actionPerformed(ActionEvent e) {
		Quadrant target = (Quadrant)e.getSource();
		if (e.getActionCommand().equals("TWO PLAYER")) { 
			if (!winner) {
				if (target.getText() == "") {
					if (alternate%2 == 0) {
						changeButton(target,"X");
					} else {
						changeButton(target,"O");
					}
					alternate++;
					if (isWinning() != "false") {
						if (isWinning() == "tie") {
							WinDialog exitDialog = new WinDialog(this,"There is a tie..."); //this refers to game.Board
						} else {
							WinReader.writeWinsFor(isWinning() == "X" ? Player.X : Player.Y); //ONLY WRITE WINS IF IT'S TWO PLAYER!!!
							WinDialog exitDialog = new WinDialog(this,isWinning() == "X" ? "X has won" : "O has won");
						}
					}
				}
			}
		} else if (e.getActionCommand().equals("EASY")) {
			if (!winner) { //checks if something has won yet
				if (target.getText() == "") {
					changeButton(target,"X");
					if (isWinning() != "false") { //is this the right spot to check for wins
						winner = true;
						if (isWinning() == "tie") {
							WinDialog exitDialog = new WinDialog(this,"There is a tie...");
						} else {
							WinDialog exitDialog = new WinDialog(this,isWinning() == "X" ? "You have won" : "You have lost");
						}
					} else {
						//have the ai play here
						playRandom();
						if (isWinning() != "false") { //is this the right spot to check for wins
							winner = true;
							if (isWinning() == "tie") {
								WinDialog exitDialog = new WinDialog(this,"There is a tie...");
							} else {
								WinDialog exitDialog = new WinDialog(this,isWinning() == "X" ? "You have won" : "You have lost");
							}
						}
					}
				} //create feedback for clicking an invalid button here
			}
		} else if (e.getActionCommand().equals("HARD")) {
			if (!winner) { //checks if something has won yet
				if (target.getText() == "") {
					changeButton(target,"X");
					bVals[buttons.indexOf(target)] = "X";
					if (isWinning() != "false") { //is this the right spot to check for wins
						winner = true;
						if (isWinning() == "tie") {
							WinDialog exitDialog = new WinDialog(this,"There is a tie...");
						} else {
							WinDialog exitDialog = new WinDialog(this,isWinning() == "X" ? "You have won" : "You have lost");
						}
					} else {
						//have the ai play here
						playMinimax();
						if (isWinning() != "false") { //is this the right spot to check for wins
							winner = true;
							if (isWinning() == "tie") {
								WinDialog exitDialog = new WinDialog(this,"There is a tie...");
							} else {
								WinDialog exitDialog = new WinDialog(this,isWinning() == "X" ? "You have won" : "You have lost");
							}
						}
					}
				} //create feedback for clicking an invalid button here
			}
		}
	}
	
	private void changeButton(Quadrant target, String newText) {
		target.setText(newText);
	}
	
	private String isWinning() { //has all possible win conditions nad loops through all of them
		int[][] triplets = { {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };
		for (int[] w : triplets) { //w is a possible combination of numbers
			if (buttons.get(w[0]).getText() == buttons.get(w[1]).getText() && buttons.get(w[1]).getText() == buttons.get(w[2]).getText() && buttons.get(w[0]).getText() != "") {
				for (int z = 0;z < w.length;z++) {
					if (buttons.get(w[0]).getText() == "X") {
						buttons.get(w[z]).setForeground(Color.GREEN);
					} else {
						buttons.get(w[z]).setForeground(Color.RED);
					}
				}
				return buttons.get(w[0]).getText();
			}
		}
		
		boolean isTie = true;
		for (Quadrant b : buttons) {
			if (b.getText() == "") { //if any of them aren't empty
				isTie = false;
			}
		}
		if (isTie) {
			return "tie";
		} else {
			return "false";
		}
	}
	
	private void playRandom() {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (int i = 0;i < 9;i++) {
			if (buttons.get(i).getText() == "") {
				indices.add(i);
			}
		}
		if (indices.size() > 0) {
			Random rn = new Random();
			Quadrant target = buttons.get(indices.get(rn.nextInt(indices.size())));
			target.setText("O");
		}
	}
	
	private String[] bVals = {"","","","","","","","",""}; //store state of the real game board here --> check ActionListener under "HARD" for when bVals is altered
	
	private void playMinimax() {
		printBVal();
		int[] result = minimax(2, Player.Y);
		bVals[result[1]] = "O";
		buttons.get(result[1]).setText("O");
	}
	
	private int[][] triplets = { {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };
	
	private void printBVal() {
		for (String val : bVals) {
			System.out.print(" |" + (val == "" ? "E" : val) + "| ");
		}	
		System.out.println(" ");
	}
	
	private int[] minimax(int level, Player player) { //PLAYER Y IS COMPUTER && PLAYER 0 IS PLAYER
		List<Integer> nextMoves = generateMoves(bVals);
		
		int bestScore = (player == Player.Y) ? Integer.MIN_VALUE : Integer.MAX_VALUE; //Make sure that this is correct. For now: Comptuer sets this to minimize..
		int currentScore;
		int bestMove = -1;
 
		if (level == 0 || nextMoves.isEmpty()) {
			bestScore = evaluate(bVals); //all possible moves
		} else {
			for (int move : nextMoves) { //temporarily act out this move
				bVals[move] = (player == Player.Y ? "O" : "X");
				if (player == Player.Y) {
					currentScore = minimax(level - 1, Player.X)[0];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestMove = move;
					}
				} else {
					currentScore = minimax(level - 1, Player.Y)[0];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestMove = move;
					} 				
				}
				//undo move
				bVals[move] = "";
			}
		}
		
		return new int[] {bestScore, bestMove};
	}
	
	private List<Integer> generateMoves(String[] bVals) {
		List<Integer> n = new ArrayList<Integer>();
		if (hasWon(bVals,Player.X) || hasWon(bVals, Player.Y)) {
			return n;
		} else {
			for (int i = 0;i < bVals.length;i++) {
				if (!bVals[i].equals("X") && !bVals[i].equals("O")) {
					n.add(i);
				}
			}
		}
		return n;
	}
	
	private int evaluate(String[] bVals) {
		int score = 0;
		
		for (int[] winCondition : triplets) {
			score += evaluateLine(bVals, winCondition);
		}
		return score;
	}
	
	private int evaluateLine(String[] bVals, int[] winCondition) {
		int score = 0;
		
		//cell 1
		if (bVals[winCondition[0]].equals("O")) { 
			score = 1; //cell 1 is computer
		} else if (bVals[winCondition[0]].equals("X")) {
			score = -1; //cell 1 is player
		}
		
		//cell 2
        if (bVals[winCondition[1]].equals("O")) {
        	if (score == 1) {   // cell 1 was computer
        		score = 10;
        	} else if (score == -1) {  // cell 1 was player
        		return 0;
        	} else {  // cell 1 was empty
        		score = 1;
        	}
        } else if (bVals[winCondition[1]].equals("X")) {
        	if (score == -1) { // cell 1 was player
        		score = -10;
        	} else if (score == 1) { // cell 1 was computer
        		return 0;
            } else {  // cell 1 was empty
            	score = -1;
            }
        }
        
        //cell 3
        if (bVals[winCondition[2]].equals("0")) {
        	if (score > 0) {  // cell 1 and/or cell 2 was computer
               score *= 10;
            } else if (score < 0) {  // cell 1 and/or cell 2 was oppSeed
               return 0;
            } else {  // cell 1 and cell 2 were empty
               score = 1;
            }
         } else if (bVals[winCondition[2]].equals("X")) {
            if (score < 0) {  // cell 1 and/or cell 2 was player
               score *= 10;
            } else if (score > 1) {  // cell 1 and/or cell 2 was computer
               return 0;
            } else {  // cell 1 and cell 2 were empty
               score = -1;
            }
         }
		return score;
	}
	
	private boolean hasWon(String[] bVals, Player player) {
		int[][] triplets = { {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };
		for (int[] w : triplets) {
			if (bVals[w[0]] == bVals[w[1]] && bVals[w[2]] == bVals[w[1]] && bVals[w[0]] == (player == Player.X ? "X" : "O") ) {
				return true;
			}
		}
		return false;
	}
}
