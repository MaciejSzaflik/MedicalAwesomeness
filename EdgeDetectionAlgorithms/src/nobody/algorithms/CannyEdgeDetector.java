package nobody.algorithms;

import java.awt.image.BufferedImage;

public class CannyEdgeDetector implements IEdgeDetect {

	@Override
	public BufferedImage doYourThing(BufferedImage Image) {
		return new GaussianFilter().doYourThing(Image);
	}

}
