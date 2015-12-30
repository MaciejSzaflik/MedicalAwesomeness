package nobody.algorithms;

import java.awt.image.BufferedImage;

import nobody.util.Utils;

public class DoubleThreshold implements IEdgeDetect {

	public int upperThreshold;
	public int lowerThreshold;
	
	public DoubleThreshold(int upperThreshold,int lowerThreshold)
	{
		this.upperThreshold = upperThreshold;
		this.lowerThreshold = lowerThreshold;
	}
	@Override
	public BufferedImage doYourThing(BufferedImage Image) {
		int height = Image.getHeight();
		int width = Image.getWidth();
		BufferedImage finalThresholdImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB) ;
		
		int red = 0;
		int green = 0;
		int blue = 0;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int color = Utils.getAt(x, y, Image);
				int toSet = 0;
				if(color>=upperThreshold)
					toSet = 255;
				else if(color>=lowerThreshold)
					toSet = 127;
				else
					toSet = 0;
				
				finalThresholdImage.setRGB(x,y,mixColor(toSet, toSet,toSet));
			}
		}
		return finalThresholdImage;
	}
	private int mixColor(int red, int green, int blue) {
		return red<<16|green<<8|blue;
	}

}
