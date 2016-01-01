package nobody.algorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nobody.util.Utils;

public class CannyEdgeDetector implements IEdgeDetect {

	float[][] Gx = {
		      {-1.0f,-2.0f,-1.0f},
		      { 0.0f, 0.0f, 0.0f},
		      { 1.0f, 2.0f, 1.0f}
		    };
	float[][] Gy = {
			  {-1.0f, 0.0f, 1.0f},
		      {-2.0f, 0.0f, 2.0f},
		      {-1.0f, 0.0f, 1.0f}
		    };
	
	int [][] powerTable;
	int [][] angleTable;
	
	@Override
	public BufferedImage doYourThing(BufferedImage Image) {
		
		powerTable = null;
		angleTable = null;
		BufferedImage gray = new BufferedImage(Image.getWidth(),Image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
	    ColorConvertOp op = new ColorConvertOp(Image.getColorModel().getColorSpace(),gray.getColorModel().getColorSpace(),null);
	    op.filter(Image,gray);
		BufferedImage noNoise = new GaussianFilter().doYourThing(gray);
		countGradients(noNoise);
		powerTable = new DoubleThreshold(60,15).doubleThresholdOnTable(powerTable); 
		blobDetection();
		return Utils.imageFromTable(powerTable);
		
	}
	
	
	
	public void countGradients(BufferedImage image)
	{
	
		angleTable = new int[image.getWidth()-1][image.getHeight()-1];
		powerTable = new int[image.getWidth()-1][image.getHeight()-1];
		for(int x = 1;x<image.getWidth()-1;x++)
		{
			for(int y = 1;y<image.getHeight()-1;y++)
			{
				
				int g_x = 
					(int) ((Gx[0][0] * getAt(x-1,y-1,image)) + (Gx[0][1] * getAt(x,y-1,image)) + (Gx[0][2] * getAt(x+1,y-1,image)) +
			               (Gx[1][0] * getAt(x-1,y  ,image)) + (Gx[1][1] * getAt(x,y  ,image)) + (Gx[1][2] * getAt(x+1,y  ,image)) +
			               (Gx[2][0] * getAt(x-1,y+1,image)) + (Gx[2][1] * getAt(x,y+1,image)) + (Gx[2][2] * getAt(x+1,y+1,image)));
				
				int g_y = 
						(int) ((Gy[0][0] * getAt(x-1,y-1,image)) + (Gy[0][1] * getAt(x,y-1,image)) + (Gy[0][2] * getAt(x+1,y-1,image)) +
				               (Gy[1][0] * getAt(x-1,y  ,image)) + (Gy[1][1] * getAt(x,y  ,image)) + (Gy[1][2] * getAt(x+1,y  ,image)) +
				               (Gy[2][0] * getAt(x-1,y+1,image)) + (Gy[2][1] * getAt(x,y+1,image)) + (Gy[2][2] * getAt(x+1,y+1,image)));
				int angleValue = 0;
				if(g_x == 0 && g_y == 0)
					angleValue = 0;
				else if(g_x == 0)
					angleValue = 90;
				else
					angleValue = descritizeAngle(Math.toDegrees((Math.atan((double)g_y/g_x))));
				angleTable[x-1][y-1] = angleValue;				
				powerTable[x-1][y-1]  = (int) Math.sqrt((g_x * g_x) + (g_y * g_y));
			}
			
		}
		
		for(int i = 0;i<powerTable.length;i++)
		{
			for(int j = 0;j<powerTable[i].length;j++)
			{
				if(shouldSuppress(i,j,angleTable[i][j],powerTable))
					powerTable[i][j] = 0;
					
			}
		}
	}
	private void blobDetection()
	{
		for(int i = 0;i<powerTable.length;i++)
		{
			for(int j = 0;j<powerTable[i].length;j++)
			{
				if(neighbourCheck(i,j,255,powerTable))
					powerTable[i][j] = 255;
				else
					powerTable[i][j] = 0;
					
			}
		}
	}
	private int getIntArray(int x,int y,int[][] table)
	{
		if(x < 0 || x >= table.length || y < 0 || y >= table[x].length)
			return Integer.MIN_VALUE;
		else
			return table[x][y];
	}
	private boolean neighbourCheck(int x,int y,int value,int [][] table)
	{
		if(table[x][y] == 0)
			return false;
		else
		{
			boolean one =  getIntArray(x+1,y+1,table) == value || getIntArray(x,y+1,table) == value || getIntArray(x-1,y+1,table) == value;
			boolean two =  getIntArray(x+1,y,table) == value || getIntArray(x-1,y,table) == value;
			boolean three =  getIntArray(x+1,y-1,table) == value || getIntArray(x,y-1,table) == value || getIntArray(x-1,y-1,table) == value;
			return one || two || three;
		}
	}
	private boolean shouldSuppress(int x,int y,int gradientValue,int [][] refereneceTable)
	{
		if(gradientValue == 0)
			return !greaterThan(getIntArray(x,y,refereneceTable),getIntArray(x-1,y,refereneceTable),getIntArray(x+1,y,refereneceTable));
		else if(gradientValue == 45)
			return !greaterThan(getIntArray(x,y,refereneceTable),getIntArray(x+1,y+1,refereneceTable),getIntArray(x-1,y-1,refereneceTable));
		else if(gradientValue == 90)
			return !greaterThan(getIntArray(x,y,refereneceTable),getIntArray(x,y+1,refereneceTable),getIntArray(x,y-1,refereneceTable));
		else if(gradientValue == 135)
			return !greaterThan(getIntArray(x,y,refereneceTable),getIntArray(x-1,y-1,refereneceTable),getIntArray(x+1,y+1,refereneceTable));
		return true;
	}
	
	private boolean greaterThan(int check,int value1,int value2)
	{
		return check > value1 && check > value2;
	}
	private int getAt(int x,int y,BufferedImage image)
	{
		return (image.getRGB(x, y) & 0xff);
	}
	private int descritizeAngle(double d)
	{
		if(checkRange(0,22.5,d) || checkRange(157.5,180,d))
			return 0;
		else if(checkRange(22.5,67.5,d))
			return 45;
		else if(checkRange(67.5,112.5,d))
			return 90;
		else if(checkRange(112.5,157.5,d))
			return 135;
		
		return 0;
	}
	private boolean checkRange(double a,double b,double value)
	{
		return value >= a && value < b;
	}
}
