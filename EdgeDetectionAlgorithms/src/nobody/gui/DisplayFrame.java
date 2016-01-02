package nobody.gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JButton;

import nobody.algorithms.*;
import nobody.util.EdgeDetector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;

public class DisplayFrame extends JFrame {

	public void PaintCurrentState() {
		// TODO Auto-generated method stub
		
	}
	private int sizeOfFrameX = 1086;
	private int sizeOfFrameY = 660;
	private int sizeOfDisplay = 540;
	
	private final Display beforeImagePanel;
	private final Display afterImagePanel;
	private final JComboBox<EdgeDetector.Algorithms> comboBox;
	
	private EdgeDetector edgeDetector;
	
	public DisplayFrame() {	
		
		edgeDetector = new EdgeDetector();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, sizeOfFrameX, sizeOfFrameY);
		
		beforeImagePanel = createDisplayPanel(0,0,false);
		afterImagePanel = createDisplayPanel(sizeOfDisplay,0,false);
		
		getContentPane().setLayout(null);
		getContentPane().add(beforeImagePanel); 
		getContentPane().add(afterImagePanel); 
		
		JButton btnBlur = new JButton("Detect");
		btnBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEffectAndSwap();
			}
		});
		btnBlur.setBounds(12, 570, 117, 25);
		getContentPane().add(btnBlur);
		
		JButton btnFile = new JButton("Choose file");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				beforeImagePanel.openFileChooser();
			}
		});
		btnFile.setBounds(12, 545, 117, 25);
		getContentPane().add(btnFile);
		

		btnBlur.setBounds(12, 570, 117, 25);
		getContentPane().add(btnBlur);
		
		comboBox = new JComboBox<EdgeDetector.Algorithms>();
		comboBox.setBounds(142, 570, 241, 24);
		getContentPane().add(comboBox);
		
		JButton button = new JButton("Diff");
		button.setBounds(554, 545, 117, 25);
		getContentPane().add(button);
		
		JButton btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(beforeImagePanel.getImageLoaded() == null)
				{
					beforeImagePanel.openFileChooser();
				}
				else
				{
					ShowAllAlgorithmsFrame all = new ShowAllAlgorithmsFrame();
					all.setVisible(true);
					all.Init(beforeImagePanel.getImageLoaded());
				}
			}
		});
		btnShowAll.setBounds(554, 570, 117, 25);
		getContentPane().add(btnShowAll);
		for (EdgeDetector.Algorithms alg : EdgeDetector.Algorithms.values()) {
			comboBox.addItem(alg);
		}
		
	}
	private Display createDisplayPanel(int x,int y,boolean visibleButton)
	{
		Display newDisplay = new Display(sizeOfDisplay,visibleButton); 
		newDisplay.setBounds(x,y, sizeOfDisplay, sizeOfDisplay);
		newDisplay.setBackground(new Color(200,200,200));
		return newDisplay;
	}
	private EdgeDetector.Algorithms getSelected()
	{
		return (EdgeDetector.Algorithms)comboBox.getSelectedItem();
	}
	private void AddEffectAndSwap()
	{
		BufferedImage image = beforeImagePanel.getImageLoaded();
		afterImagePanel.scaleAndSetImage(edgeDetector.DoAlgorithm(getSelected(),image),getSelected().toString());
	}
}
