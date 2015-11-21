package nobody.algorithms;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import nobody.util.Utils;

public class GaussianFilter implements IEdgeDetect {

	float[] matrix = {
	       2 ,4 ,5 ,4 ,2 ,
	       4 ,9 ,12,9 ,4 ,
	       5, 12,15,12,5 ,
	       4 ,9 ,12,9 ,4 ,
	       2 ,4 ,5 ,4 ,2
	    };
	@Override
	public BufferedImage doYourThing(BufferedImage Image) {
		BufferedImageOp op = new ConvolveOp( new Kernel(5, 5,Utils.mulArrayByS(matrix, (float)1/115)));
		BufferedImage dest = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		return op.filter(Image, dest);
	}

}
