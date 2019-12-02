package game;

import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class ResolutionSlider extends JSlider{
	public ResolutionSlider(ChangeListener l) {
		super(JSlider.HORIZONTAL,300,1200,900);
		setMajorTickSpacing(300);
		setMinorTickSpacing(30);
		setPaintTicks(true);
		setPaintLabels(true);
		setSnapToTicks(true);
		addChangeListener(l);
		setFont(new Font("Serif", Font.ITALIC, 15));
	}
}
