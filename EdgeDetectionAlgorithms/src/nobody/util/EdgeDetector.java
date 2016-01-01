package nobody.util;

import nobody.algorithms.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class EdgeDetector {
	public enum Algorithms
	{
		SobelOperator,
		PrewittOperator,
		Canny,
		Laplace,
		LoG,
		Frei_Chen
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
				toReturn = new PrewittOperator();
				break;
			case Canny:
				toReturn = new CannyEdgeDetector();
				break;
			case Laplace:
				toReturn = new Laplace();
				break;
			case LoG:
				toReturn = new LoG();
				break;
			case Frei_Chen:
				toReturn = new Frei_Chen();
		}
		return toReturn;
	}
	
	
}


