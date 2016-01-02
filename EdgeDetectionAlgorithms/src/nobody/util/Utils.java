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
	public static int getRed(int color) {
		return (color & 0x00ff0000)  >> 16;
	}
	
	public static int getGreen(int color) {
		return	(color & 0x0000ff00)  >> 8;
	}
	
	public static int getBlue(int color) {
		return (color & 0x000000ff)  >> 0;
		
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
	public static BufferedImage diffTheImages(BufferedImage alfa, BufferedImage beta)
	{
		alfa = alfa.getSubimage(0, 0, Math.min(alfa.getWidth(), beta.getWidth()), Math.min(alfa.getHeight(), beta.getHeight()));
		beta = beta.getSubimage(0, 0, Math.min(alfa.getWidth(), beta.getWidth()), Math.min(alfa.getHeight(), beta.getHeight()));

		BufferedImage diff = new BufferedImage(alfa.getWidth(),alfa.getHeight(),BufferedImage.TYPE_INT_RGB) ;
		for (int x = 0; x < diff.getWidth(); x++) {
			for (int y = 0; y < diff.getHeight(); y++) {
				int alfaVal = getRed(alfa.getRGB(x, y));
				int betaVal = getRed(beta.getRGB(x, y));
				
				if(diffRange(alfaVal,betaVal,100))
					diff.setRGB(x,y, (new Color(255,0,0).getRGB()));
				else if(diffRangeLesser(alfaVal,betaVal,20))
				{
					if(alfaVal < 30 && betaVal < 30)
						diff.setRGB(x,y, (new Color(0,0,0).getRGB()));
					else
						diff.setRGB(x,y, (new Color(0,255,0).getRGB()));
				}
			}
		}
		return diff;
	}
	public static boolean diffRange(int val1,int val2,int range)
	{
		return Math.abs(val1 - val2) >= range;
	}
	public static boolean diffRangeLesser(int val1,int val2,int range)
	{
		return Math.abs(val1 - val2) < range;
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
