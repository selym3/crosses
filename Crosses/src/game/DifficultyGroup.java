package game;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class DifficultyGroup extends ButtonGroup {
	private ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>(); //this might not be needed
	
	public ArrayList<JRadioButton> getButtons() {
		return buttons;
	}
	
	public DifficultyGroup(ActionListener e, String... buttonNames) {
		super();
		int i = 0;
		for (String title : buttonNames) {
			JRadioButton temp = new JRadioButton(title, i == 0 ? true : false);
			temp.setActionCommand(title);
			temp.addActionListener(e);
			buttons.add(temp);
			add(buttons.get(i));
			i++;
		}
	}
}
