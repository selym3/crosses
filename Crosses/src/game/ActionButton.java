package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class ActionButton extends JButton implements ActionListener {
	
	public ActionButton() { //WITH ONLY PARENT CLASS, IT CREATES A RED BUTTON TO ACTIVATE EXIT DIALOG
		super("EXIT");
		setFocusPainted(false);
		addActionListener(this); 
		setBackground(new Color(250, 52, 52));
		setBorder(new LineBorder(new Color(255,77,77),1,false));
		setForeground(Color.white);
		setFont(new Font("Tahoma", Font.BOLD, 24));
	}
	public ActionButton(String title, ActionListener e, Color c) { //WITH title AND custom action listener, it creates custom button
		super(title);
		setFocusPainted(false);
		addActionListener(e); //--> this has been removed. action listener should be defined after all the buttons have been created
		setBackground(c);
		setBorder(new LineBorder(tintColor(c),1,false));
		setForeground(Color.white);
		setFont(new Font("Tahoma", Font.BOLD, 24));
	}
	
	public static Color tintColor(Color c) { //i dont think this works or it doesnt work with Color.color
		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();
		
		return new Color(red > 10 ? red - 10 : red, green > 10 ? green - 10 : green,  blue > 10 ? blue - 10 : blue);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
