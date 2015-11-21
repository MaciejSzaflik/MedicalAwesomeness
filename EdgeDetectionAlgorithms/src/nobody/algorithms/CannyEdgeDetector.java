package nobody.algorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nobody.util.Utils;

public class CannyEdgeDetector implements IEdgeDetect {

	float[] Gx = {
		       -0.33f, 0, 0.33f,
		       -0.66f, 0.33f, 0.66f,
		       -0.33f, 0, 0.33f
		    };
	@Override
	public BufferedImage doYourThing(BufferedImage Image) {
		
		BufferedImageOp op = new ConvolveOp( new Kernel(3, 3,Gx));
		BufferedImage dest = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		//BufferedImage noNoise = new GaussianFilter().doYourThing(Image);
		
		File outputfile = new File("image.png");
		dest = op.filter(Image, dest);
        try {
			ImageIO.write(dest, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dest;
		
	}

}
