package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class Quadrant extends JButton {
	//creates a blue theme square that takes in a ActionListener and ActionCommand string
	public Quadrant(String type, ActionListener e) {
		super("");
		setFocusPainted(false);
		setActionCommand(type);
		addActionListener(e); //--> this has been removed. action listener should be defined after all the buttons have been created
		setBackground(new Color(52, 146, 235));
		setBorder(new LineBorder(Color.BLUE,1,false));
		setForeground(Color.white);
		setFont(new Font("Tahoma", Font.BOLD, 72));
	}
}
