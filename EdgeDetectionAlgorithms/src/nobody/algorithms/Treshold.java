package nobody.algorithms;

import java.awt.image.BufferedImage;

public class Treshold implements IEdgeDetect {

	public int thresholdLevels;
	
	public Treshold(int levels)
	{
		this.thresholdLevels = levels;
	}
	
	@Override
	public BufferedImage doYourThing(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage finalThresholdImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB) ;
		
		int red = 0;
		int green = 0;
		int blue = 0;
		
		for (int x = 0; x < width; x++) {
			try {

				for (int y = 0; y < height; y++) {
					int color = img.getRGB(x, y);

					red = getRed(color);
					green = getGreen(color);
					blue = getBlue(color);

					if((red+green+green)/3 < (int) (thresholdLevels)) {
							finalThresholdImage.setRGB(x,y,mixColor(0, 0,0));
						}
						else {
							finalThresholdImage.setRGB(x,y,mixColor(255, 255,255));
						}
					
				}
			} catch (Exception e) {
				 e.getMessage();
			}
		}
		
		return finalThresholdImage;
	}
	
	private int mixColor(int red, int green, int blue) {
		return red<<16|green<<8|blue;
	}

	public int getRed(int color) {
		return (color & 0x00ff0000)  >> 16;
	}
	
	public int getGreen(int color) {
		return	(color & 0x0000ff00)  >> 8;
	}
	
	public int getBlue(int color) {
		return (color & 0x000000ff)  >> 0;
		
	}

}
