package nobody.gui;

import java.awt.Color;

import javax.swing.JFrame;

public class DisplayFrame extends JFrame {

	public void PaintCurrentState() {
		// TODO Auto-generated method stub
		
	}
	private int sizeOfFrameX = 1200;
	private int sizeOfFrameY = 660;
	private int sizeOfDisplay = 540;
	
	private final Display beforeImagePanel;
	private final Display afterImagePanel;
	
	public DisplayFrame() {	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, sizeOfFrameX, sizeOfFrameY);
		
		beforeImagePanel = createDisplayPanel(0,0,true);
		afterImagePanel = createDisplayPanel(sizeOfDisplay,0,false);
		
		getContentPane().setLayout(null);
		getContentPane().add(beforeImagePanel); 
		getContentPane().add(afterImagePanel); 
	}
	private Display createDisplayPanel(int x,int y,boolean visibleButton)
	{
		Display newDisplay = new Display(sizeOfDisplay,visibleButton); 
		newDisplay.setBounds(x,y, sizeOfDisplay, sizeOfDisplay);
		newDisplay.setBackground(new Color(200,200,200));
		return newDisplay;
	}

}
