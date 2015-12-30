package nobody.algorithms;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import nobody.util.Utils;

public class Laplace implements IEdgeDetect {
	
	float[] matrix = {
		       -1 ,-1 ,-1 ,-1 ,-1 ,
		       -1 ,-1 ,-1 ,-1 ,-1 ,
		       -1 ,-1 , 24,-1 ,-1 ,
		       -1 ,-1 ,-1 ,-1 ,-1 ,
		       -1 ,-1 ,-1 ,-1 ,-1
		    };
		@Override
		public BufferedImage doYourThing(BufferedImage Image) {
			BufferedImageOp op = new ConvolveOp( new Kernel(5, 5,matrix));
			BufferedImage dest = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
			return op.filter(Image, dest);
		}

}
