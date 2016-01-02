package nobody.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import nobody.util.EdgeDetector;

public class ShowAllAlgorithmsFrame extends JFrame {
	
	private int sizeOfFrameX = 1086;
	private int sizeOfFrameY = 660;
	private int rowsCount = 2;
	private int coulumnsCount = 2;
	private int displayX = 100;
	private int displayY = 100;
	
	private List<Display> displays = new ArrayList<Display>();
	
	private EdgeDetector edgeDetector;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShowAllAlgorithmsFrame()
	{
		edgeDetector = new EdgeDetector();
		int numberOfAlgorithms = EdgeDetector.Algorithms.values().length;
		
		displayY = sizeOfFrameY/coulumnsCount;
		rowsCount = (int) Math.ceil((float)numberOfAlgorithms/coulumnsCount);
		displayX = sizeOfFrameX/rowsCount;
		
		for(int i = 0;i<numberOfAlgorithms;i++)
		{
			Display d = createDisplayPanel((i/coulumnsCount)*displayX,i%coulumnsCount*displayY,false);
			displays.add(d);
			getContentPane().add(d);
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 0, sizeOfFrameX, sizeOfFrameY);
		getContentPane().setLayout(null);
	}
	public void Init(BufferedImage image)
	{
		EdgeDetector.Algorithms[] alg = EdgeDetector.Algorithms.values();
		int index = 0;
		for(Display d : displays)
		{
			(new Thread(new SetterImage(d,edgeDetector,alg[index],image))).start();
			index++;
		}
	}
	
	private static class SetterImage implements Runnable {
		private Display d;
		private EdgeDetector eD; 
		private EdgeDetector.Algorithms alg;
		private BufferedImage img;
		public SetterImage(Display d, EdgeDetector eD, EdgeDetector.Algorithms alg,BufferedImage img)
		{
			this.d = d;
			this.eD = eD;
			this.alg = alg;
			this.img = img;
		}
	    public void run() {
			d.scaleAndSetImage(eD.DoAlgorithm(alg,img), alg.toString());
	    }
}
	
	
	private Display createDisplayPanel(int x,int y,boolean visibleButton)
	{
		Display newDisplay = new Display(100,visibleButton); 
		newDisplay.setBounds(x,y, displayX, displayY);
		newDisplay.setBackground(new Color(200,200,200));
		return newDisplay;
	}

}
