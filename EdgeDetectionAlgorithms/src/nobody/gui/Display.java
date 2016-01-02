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
import javax.swing.border.BevelBorder;


public class Display extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int realSize;
	private JButton ChooseFileButton;
	private JLabel imageLabel;
	private JLabel titleLabel;
	private BufferedImage imageLoaded;
	public BufferedImage getImageLoaded()
	{
		return imageLoaded;
	}

	public Display(int size,boolean chooseButtonVisible)
	{
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 0, 133, 26);
		add(panel);
		
		titleLabel = new JLabel("none");
		panel.add(titleLabel);
		titleLabel.setBackground(Color.GREEN);
		titleLabel.setForeground(Color.BLACK);
		
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
					openFileChooser();
				}
			}
		);
		add(ChooseFileButton);
	}
	public void openFileChooser()
	{
		final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
		int returnVal = fc.showOpenDialog(ChooseFileButton);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			setImageFromFile(fc.getSelectedFile());
			removeButton();
		}
	}
	public void setImageFromFile(File file)
	{
		try {
			imageLoaded = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scaleAndSetImage(imageLoaded,"orginal");
	}
	public void scaleAndSetImage(BufferedImage image,String titleStr)
	{
		if(imageLabel!=null)
		{
			imageLabel.setIcon(null);
			imageLabel.revalidate();
		}
		AffineTransform at = new AffineTransform();
		
		float scaleParameter = Math.min((float)getHeight()/image.getHeight(),(float)getWidth()/image.getWidth());
		at.scale(scaleParameter ,scaleParameter );
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		
		int newWidth = (int) (image.getWidth()*scaleParameter);
		int newHeight = (int) (image.getHeight()*scaleParameter);
		
		BufferedImage after = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		
		after = scaleOp.filter(image, after);
		
		imageLoaded = after;
		imageLabel = new JLabel(new ImageIcon(after));
		imageLabel.setSize(newWidth, newHeight);
		add(imageLabel);
		
		titleLabel.setText(titleStr);
		
		this.revalidate();
		this.repaint();
	}
	
	public void removeButton()
	{
		if(ChooseFileButton == null)
			return;
		remove(ChooseFileButton);
		ChooseFileButton.setVisible(false);
		ChooseFileButton.setEnabled(false);
		
		this.revalidate();
		this.repaint();
		
	}
}
