package nobody.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import nobody.util.EdgeDetector;
import nobody.util.Utils;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Diff extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private int sizeOfFrameX = 1086;
	private int sizeOfFrameY = 660;
	
	private Display mainDisplay;
	private Display smallDisplayTop;
	private Display smallDisplayBottom;
	
	private EdgeDetector edgeDetector;
	private JComboBox<EdgeDetector.Algorithms> topCombo;
	private JComboBox<EdgeDetector.Algorithms> bottomCombo;
	
	private BufferedImage image;
	
	public Diff(final BufferedImage image)
	{
		edgeDetector = new EdgeDetector();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 0, sizeOfFrameX, sizeOfFrameY);
		getContentPane().setLayout(null);
		
		smallDisplayTop = new Display(100,false); 
		smallDisplayTop.setBounds(0, 0, 268, 276);
		getContentPane().add(smallDisplayTop);
		
		smallDisplayBottom = new Display(100, false);
		smallDisplayBottom.setBounds(0, 276, 268, 276);
		getContentPane().add(smallDisplayBottom);
		
		final Display mainDisplay = new Display(100, false);
		mainDisplay.setBounds(293, 0, 781, 660);
		getContentPane().add(mainDisplay);
		
		topCombo = new JComboBox();
		topCombo.setBounds(12, 585, 127, 24);
		getContentPane().add(topCombo);
		
		bottomCombo = new JComboBox();
		bottomCombo.setBounds(12, 624, 127, 24);
		getContentPane().add(bottomCombo);
		
		JButton btnDiff = new JButton("Diff");
		btnDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smallDisplayTop.scaleAndSetImage(edgeDetector.DoAlgorithm(getSelectedTop(), image), getSelectedTop().toString());
				smallDisplayBottom.scaleAndSetImage(edgeDetector.DoAlgorithm(getSelectedBottom(), image), getSelectedBottom().toString());
			
				mainDisplay.scaleAndSetImage(
						Utils.diffTheImages(
								smallDisplayTop.getImageLoaded(), 
								smallDisplayBottom.getImageLoaded()), "diff");
			}
		});
		btnDiff.setBounds(151, 608, 117, 25);
		getContentPane().add(btnDiff);
		
		for (EdgeDetector.Algorithms alg : EdgeDetector.Algorithms.values()) {
			topCombo.addItem(alg);
			bottomCombo.addItem(alg);
		}
		this.image = image;
	}
	private EdgeDetector.Algorithms getSelectedTop()
	{
		return (EdgeDetector.Algorithms)topCombo.getSelectedItem();
	}
	private EdgeDetector.Algorithms getSelectedBottom()
	{
		return (EdgeDetector.Algorithms)bottomCombo.getSelectedItem();
	}
	
}
