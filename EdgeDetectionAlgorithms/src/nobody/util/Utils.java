package nobody.util;

import java.awt.Color;
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
	public static BufferedImage imageFromTable(int[][] table)
	{
		int width = table.length;
		int height = table[0].length;
		BufferedImage finalThresholdImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB) ;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				finalThresholdImage.setRGB(x,y,Utils.mixColor(table[x][y], table[x][y],table[x][y]));
			}
		}
		return finalThresholdImage;
	}
	public static BufferedImage imageFromTableRGB(int[][] table)
	{
		int width = table.length;
		int height = table[0].length;
		BufferedImage finalThresholdImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB) ;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int value = Utils.clamp(table[x][y], 0, 255);
				finalThresholdImage.setRGB(x,y, (new Color(value,value,value).getRGB()));
			}
		}
		return finalThresholdImage;
	}
}
