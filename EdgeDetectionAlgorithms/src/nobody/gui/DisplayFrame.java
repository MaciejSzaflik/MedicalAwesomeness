package nobody.gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JButton;

import nobody.algorithms.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

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
		
		JButton btnBlur = new JButton("Blur");
		btnBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEffectAndSwap();
			}
		});
		btnBlur.setBounds(12, 570, 117, 25);
		getContentPane().add(btnBlur);
	}
	private Display createDisplayPanel(int x,int y,boolean visibleButton)
	{
		Display newDisplay = new Display(sizeOfDisplay,visibleButton); 
		newDisplay.setBounds(x,y, sizeOfDisplay, sizeOfDisplay);
		newDisplay.setBackground(new Color(200,200,200));
		return newDisplay;
	}
	
	private void AddEffectAndSwap()
	{
		BufferedImage image = beforeImagePanel.getImageLoaded();
		SobelOperator canny = new SobelOperator();
		afterImagePanel.scaleAndSetImage(canny.doYourThing(image));
	}
}
