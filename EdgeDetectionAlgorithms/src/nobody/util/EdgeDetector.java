package nobody.util;

import nobody.algorithms.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class EdgeDetector {
	public enum Algorithms
	{
		SobelOperator,
		PrewittOperator,
		Canny
	}
	
	public EdgeDetector()
	{
		
	}
	public BufferedImage DoAlgorithm(Algorithms algorithm, BufferedImage image)
	{
		IEdgeDetect edgeDector = getAlgorithm(algorithm);
		return edgeDector.doYourThing(image);
	}
	private IEdgeDetect getAlgorithm(Algorithms algorithm)
	{
		IEdgeDetect toReturn = null;
		switch(algorithm)
		{
			case SobelOperator:
				toReturn = new SobelOperator();
				break;
			case PrewittOperator:
				toReturn = new SobelOperator();
				break;
			case Canny:
				toReturn = new CannyEdgeDetector();
				break;
		}
		return toReturn;
	}
}


