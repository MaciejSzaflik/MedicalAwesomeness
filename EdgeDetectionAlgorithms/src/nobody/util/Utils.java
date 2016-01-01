package nobody.util;

import java.awt.image.BufferedImage;

public class Utils {
	
	public static float[] mulArrayByS(float[] array,float scalar)
	{
		for(int i =0;i<array.length;i++)
			array[i]*=scalar;
		return array;
	}
	public static int clamp(int value,int min,int max)
	{
		return Math.max(min, Math.min(max, value));
	}
	public static int getAt(int x,int y,BufferedImage image)
	{
		return (image.getRGB(x, y) & 0xff);
	}
	public static int mixColor(int red, int green, int blue) {
		return red<<16|green<<8|blue;
	}
}
