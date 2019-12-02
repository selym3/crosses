package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WinDialog extends JDialog {
	//takes in a Parent Jframe (it's only board right now, should change that) and it opens a dialog that will exit the program
	public WinDialog(Board parent, String label) { //this doesn't really need to take in JLabel, it could just take in a string
		super(parent, "Crosses -- Win Screen"); //call constructor for JDialog with the title and parent (Board instance)
		setLayout(new GridLayout(0,1));
		setVisible(true);
		setBounds(0,0,500,345);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Font defFont = new Font("Tahoma", Font.BOLD, 64);
		JLabel newLabel = new JLabel(label);		
		newLabel.setHorizontalAlignment(JLabel.CENTER);
		newLabel.setFont(defFont);
		add(newLabel);
		
		ActionButton exiter = new ActionButton();
		add(exiter);
		
		ActionButton returner = new ActionButton("RETURN TO MENU", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.menu.updateWins();
				parent.menu.setVisible(true);
				parent.dispose();
				parent.menu.destroyBoard();
				parent.menu.togglePlay(true); //enter NEW game
				parent.menu.toggleDispose(false); //no dispose button text
				parent.menu.resetResolutionTracker();
			}
		}, Color.BLUE);
		add(returner);
	}
}
