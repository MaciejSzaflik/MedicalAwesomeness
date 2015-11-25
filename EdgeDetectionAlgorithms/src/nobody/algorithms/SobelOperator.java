package nobody.algorithms;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import nobody.util.*;

public class SobelOperator implements IEdgeDetect {

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
	@Override
	public BufferedImage doYourThing(BufferedImage image) {

		BufferedImage gray = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

	    ColorConvertOp op = new ColorConvertOp(image.getColorModel().getColorSpace(),gray.getColorModel().getColorSpace(),null);
	    op.filter(image,gray);
			
		BufferedImage dest = new BufferedImage(gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		for(int x = 1;x<gray.getWidth()-1;x++)
		{
			for(int y = 1;y<gray.getHeight()-1;y++)
			{
				
				int pixel_x = 
					(int) ((Gx[0][0] * getAt(x-1,y-1,gray)) + (Gx[0][1] * getAt(x,y-1,gray)) + (Gx[0][2] * getAt(x+1,y-1,gray)) +
			               (Gx[1][0] * getAt(x-1,y  ,gray)) + (Gx[1][1] * getAt(x,y  ,gray)) + (Gx[1][2] * getAt(x+1,y  ,gray)) +
			               (Gx[2][0] * getAt(x-1,y+1,gray)) + (Gx[2][1] * getAt(x,y+1,gray)) + (Gx[2][2] * getAt(x+1,y+1,gray)));
				
				int pixel_y = 
						(int) ((Gy[0][0] * getAt(x-1,y-1,gray)) + (Gy[0][1] * getAt(x,y-1,gray)) + (Gy[0][2] * getAt(x+1,y-1,gray)) +
				               (Gy[1][0] * getAt(x-1,y  ,gray)) + (Gy[1][1] * getAt(x,y  ,gray)) + (Gy[1][2] * getAt(x+1,y  ,gray)) +
				               (Gy[2][0] * getAt(x-1,y+1,gray)) + (Gy[2][1] * getAt(x,y+1,gray)) + (Gy[2][2] * getAt(x+1,y+1,gray)));

				
				int val = (int) Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));
				val = Utils.clamp(val, 0, 255);

				Color color = new Color(val,val,val);
				dest.setRGB(x, y, color.getRGB());
			}
		}
		
		return dest;
	}
	private int getAt(int x,int y,BufferedImage image)
	{
		return (image.getRGB(x, y) & 0xff);
	}
}
