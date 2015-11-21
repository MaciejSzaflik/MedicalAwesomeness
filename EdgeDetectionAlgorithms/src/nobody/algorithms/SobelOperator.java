package nobody.algorithms;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SobelOperator implements IEdgeDetect {

	float[] Gx = {
		      -1.0f,-2.0f,-1.0f,
		       0.0f, 0.0f, 0.0f,
		       1.0f, 2.0f, 1.0f
		    };
	float Gy[] = {
		       -1.0f, 0.0f, 1.0f,
		       -2.0f, 0.0f, 2.0f,
		       -1.0f, 0.0f, 1.0f
		    };
	@Override
	public BufferedImage doYourThing(BufferedImage Image) {
		BufferedImageOp opX = new ConvolveOp( new Kernel(3, 3,Gx));
		BufferedImageOp opY = new ConvolveOp( new Kernel(3, 3,Gy));
		BufferedImage dest = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		File outputfile = new File("image.png");
		dest = opX.filter(Image, dest);
		dest = opY.filter(dest, null);
		
		return dest;
	}
}
