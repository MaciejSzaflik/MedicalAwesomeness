package nobody.gui;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Display extends JPanel {
	
	private int realSize;
	private JButton ChooseFileButton;
	private JLabel imageLabel;
	private BufferedImage imageLoaded;

	public Display(int size,boolean chooseButtonVisible)
	{
		setLayout(new BorderLayout(0, 0));
		
		if(chooseButtonVisible)
			AddFileChooserButton();
		
		realSize = size;
	}
	private void AddFileChooserButton()
	{
		ChooseFileButton = new JButton("Choose a file");
		ChooseFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
				int returnVal = fc.showOpenDialog(ChooseFileButton);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setImageFromFile(fc.getSelectedFile());
					removeButton();
				}
				
			}
			}
		);
		add(ChooseFileButton);
	}
	
	public void setImageFromFile(File file)
	{
		try {
			imageLoaded = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		AffineTransform at = new AffineTransform();
		float scaleParameter = (float)getHeight()/imageLoaded.getHeight();
		at.scale(scaleParameter ,scaleParameter );
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		int newWidth = (int) (imageLoaded.getWidth()*scaleParameter);
		int newHeight = (int) (imageLoaded.getHeight()*scaleParameter);
		BufferedImage after = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		after = scaleOp.filter(imageLoaded, after);
		
		imageLoaded = after;
		imageLabel = new JLabel(new ImageIcon(after));
		add(imageLabel);
	}
	
	public void removeButton()
	{
		remove(ChooseFileButton);
		ChooseFileButton.setVisible(false);
		ChooseFileButton.setEnabled(false);
		
		this.revalidate();
		this.repaint();
		
	}
	
	
}
