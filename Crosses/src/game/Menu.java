package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Menu extends JFrame implements ActionListener {
	private ActionButton playButton, resetButton, quitter, updateButton, resetWinsButton;
	private ResolutionSlider slider;
	private Board board;
	private int res;
	private JLabel resTracker;
	private DifficultyGroup difficultyOptions;
	private String difficulty;
	private JLabel winCounter; //will include the wins for both x and y
	
	public void toggleDispose(boolean on) {
		if (on) {
			resetButton.setText("DISPOSE GAME BOARD");
		} else {
			resetButton.setText(" ");
		}
	}
	
	public void togglePlay(boolean on) {
		if (on) {
			playButton.setText("ENTER NEW GAME");
		} else {
			playButton.setText("ENTER GAME");
		}
	}
	public void destroyBoard() {
		board = null;
	}
	public void resetResolutionTracker() {
		if (resTracker != null) {
			resTracker.setText("900");
			res = 900;
			slider.setValue(900);
		}
	}
	public void resetResolutionTracker(int ree) {
		if (resTracker != null) {
			resTracker.setText(Integer.toString(ree));
			res = ree;
			slider.setValue(ree);
		}
	}
	
	
	public Menu() { //calls add buttons function and sets basic properties of JFrame
		super("Crosses -- Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,345);
		setResizable(false);
		setLayout(new GridBagLayout());
		addButtons();
		setVisible(true); //IDEK why this has to be here but the Slider wouldn't appear so this is here now
	}
	
	private void addButtons() { //using GRIDBAGLAYOUT, adds private buttons to the screen (the layout is poor)
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL; //grid bag constraint that's true for everything

		//this applies to playButton
		c.weightx = 1.0;
		c.ipady = 100;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = 0;//<--
		c.gridy = 0;
		
		playButton = new ActionButton("ENTER NEW GAME", this, Color.GREEN);
		playButton.setActionCommand("PLAY"); //because this one uses a class-level action Listener
		add(playButton,c);

		resetButton = new ActionButton(" ", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (board != null) { 
					System.out.println("DISPOSE OF THE BOARD");
					playButton.setText("ENTER NEW GAME");
					resetButton.setText(" ");
					destroyBoard();
					resetResolutionTracker();
				}
			}
		}, new Color(0,0,255));
		
		//this applies to resetButton
		c.ipady = 10;
		c.gridwidth = 1;
		c.gridx = 0;//<--
		c.gridy = 1;
		
		add(resetButton,c);
		
		//this applies to quit button
		c.gridx = 1;//<--
		c.gridy = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		quitter = new ActionButton();
		add(quitter,c);
		
		//this applies to ScreenHeight label
		c.gridx = 0;//<--
		c.gridy = 2;
		c.ipady = 0;
		c.gridwidth = 1;
		
		JLabel jl = new JLabel("Screen Height:");
		jl.setFont(new Font("Tahamo", Font.BOLD, 24));
		add(jl,c);
		
		//This applies to resTracker label
		c.gridx = 1;//<--
		c.gridy = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		resTracker = new JLabel("900");
		resTracker.setFont(new Font("Tahamo", Font.BOLD, 24));
		add(resTracker,c);
		
		//this applies to slider only
		c.gridx = 0;//<--
		c.gridy = 3;
		c.weightx = 1.0;
		c.insets = new Insets(0,0,0,0);
		
		slider = new ResolutionSlider(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ResolutionSlider r = (ResolutionSlider)e.getSource();
				if (!r.getValueIsAdjusting()) {
					if (board == null) {
						res = r.getValue();
						resetResolutionTracker(r.getValue());
					} else {
						slider.setValue(res);
						resetResolutionTracker(res);
					}
				} 
			}
			
		});
		res = slider.getValue(); //initializes res value, if someone doesn't move it
		add(slider, c);
		
		//grid constraints for jradiobuttons
		c.gridx = 0;//<--
		c.gridy = 4;
		c.gridwidth = 1;
		
		//add JRadioButton for difficulty
		difficultyOptions = new DifficultyGroup(this,"TWO PLAYER","EASY","HARD");
		difficultyOptions.getButtons().forEach(radio -> { 
			if (radio.getText() == difficultyOptions.getButtons().get(difficultyOptions.getButtons().size() - 1).getText()) {
				c.gridwidth = GridBagConstraints.REMAINDER; //this makes so the last radio button fills the remainder of the row
			}
			add(radio,c);
			c.gridx++;
		}); 
		difficulty = "TWO PLAYER"; //default difficulty to two player
		
		//this is for win counter
		c.gridx = 4;//<--
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		winCounter = new JLabel(Integer.toString(WinReader.getWins().get(Player.X)) + " : " + Integer.toString(WinReader.getWins().get(Player.Y)));
		winCounter.setFont(new Font("Tahamo", Font.BOLD, 24));
		add(winCounter,c);
		
		//this is for update button
		//c.weightx = 1.5;
		c.gridx = 0;//<--
		c.gridy = 5;
		c.gridwidth = 1;
		
		updateButton = new ActionButton("UPDATE", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				updateWins();
			}
		},Color.orange);
		add(updateButton,c);
		
		//this is for resetWins button
		c.gridx = 2;//<--
		c.gridy = 5;
		c.gridwidth = GridBagConstraints.RELATIVE;
		
		resetWinsButton = new ActionButton("RESET", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WinReader.resetWins();
				updateWins();
			}
		}, Color.MAGENTA);
		
		add(resetWinsButton,c);
	}

	public void updateWins() { //if there's no outside use, make this private
		winCounter.setText(Integer.toString(WinReader.getWins().get(Player.X)) + " : " + Integer.toString(WinReader.getWins().get(Player.Y)));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("PLAY")) {
			togglePlay(false); //enter game
			setVisible(false);
			if (board == null) {
				board = new Board(this,res,difficulty); //Board(menu,screenHeight,playType)
				System.out.println("CREATED BOARD WITH RES: " + res);
				toggleDispose(true); //dispose board
			} else {
				board.setVisible(true);
				System.out.println("SHOWING THE PLAY BOARD");
			}
		} else { // COMMAND IS *NOT* PLAY, SO IT IS A DIFFIUCLTY TYPE 
			//JRadioButtons call their action listener if they are set/initialized, not just if they're clicked -> this code works as a result
			difficulty = e.getActionCommand();
			System.out.println("Set new difficulty to: " + difficulty);
		}
	}

}
