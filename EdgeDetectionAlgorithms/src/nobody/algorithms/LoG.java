package nobody.algorithms;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class LoG implements IEdgeDetect {

	float[] matrix = {
		       0,1,1,2,2,2,1,1,0,
		       1,2,4,5,5,5,4,2,1,
		       1,4,5,3,0,3,5,4,2,
		       2,5,3,-12,-24,-12,3,5,2,
		       2,5,0,-24,-40,-25,0,5,2,
		       2,5,3,-12,-24,-12,3,5,2,
		       1,4,5,3,0,3,5,4,2,
		       1,2,4,5,5,5,4,2,1,
		       0,1,1,2,2,2,1,1,0
		    };

		@Override
		public BufferedImage doYourThing(BufferedImage Image) {
			BufferedImageOp op = new ConvolveOp( new Kernel(9, 9,matrix));
			BufferedImage dest = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
			return op.filter(Image, dest);
		}

}
