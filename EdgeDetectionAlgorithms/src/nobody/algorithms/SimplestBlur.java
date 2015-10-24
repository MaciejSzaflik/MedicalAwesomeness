package nobody.algorithms;

import java.awt.image.*;

public class SimplestBlur implements IEdgeDetect {

	float[] matrix = {
	        0.111f, 0.111f, 0.111f, 
	        0.111f, 0.111f, 0.111f, 
	        0.111f, 0.111f, 0.111f, 
	    };

	public BufferedImage doYourThing(BufferedImage Image) {
		BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );
		BufferedImage dest = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		return op.filter(Image, dest);
	}

}
