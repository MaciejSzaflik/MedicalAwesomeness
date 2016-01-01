package nobody.algorithms;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import nobody.util.Utils;

public class Frei_Chen implements IEdgeDetect {

	float[] g1 = 
		      { 1.0f, 1.414f,1.0f,
		        0.0f, 0.0f, 0.0f,
		        -1.0f, -1.414f,-1.0f};
	float[] g2 = 
		      { 1.0f, 0,-1.0f,
		        1.414f, 0.0f, -1.414f,
		        1.0f, 0,-1.0f};
	float[] g3 = 
		      { 0.0f, -1.0f,1.414f,
		        1.0f, 0.0f, -1.0f,
		        -1.414f, 1.0f, 0.0f};
	float[] g4 = 
		      { 1.414f, -1.0f,0.0f,
		        -1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f,-1.414f};
	
	float[] g5 = 
		      { 0.0f, 1.0f,0.0f,
		        -1.0f, 0.0f, -1.0f,
		        0.0f, 1.0f,0.0f};
	float[] g6 = 
		      { -1.0f, 0.0f,1.0f,
		        -1.0f, 0.0f, 1.0f,
		        1.0f, 1.0f,-1.0f};
	float[] g7 = 
		      { 1.0f, -2.0f,1.0f,
		        -2.0f, 4.0f, -2.0f,
		        1.0f, -2.0f,1.0f};
	float[] g8 = 
				{ -2.0f, 1.0f,-2.0f,
		        1.0f, 4.0f,1.0f,
		        -2.0f, 1.0f,-2.0f};
	float[] g9 = 
				{ 1.0f, 1.0f,1.0f,
		        1.0f, 1.0f, 1.0f,
		        1.0f, 1.0f,1.0f};
	
	@Override
	public BufferedImage doYourThing(BufferedImage Image) {
		BufferedImageOp op1 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g1, sqrtParam())));
		BufferedImageOp op2 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g2, sqrtParam())));
		BufferedImageOp op3 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g3, sqrtParam())));
		BufferedImageOp op4 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g4, sqrtParam())));
		
		BufferedImageOp op5 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g5, 0.5f)));
		BufferedImageOp op6 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g6, 0.5f)));
		BufferedImageOp op7 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g7, 0.16667f)));
		BufferedImageOp op8 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g8, 0.16667f)));
		BufferedImageOp op9 = new ConvolveOp( new Kernel(3, 3,Utils.mulArrayByS(g9, 0.33333f)));
		
		BufferedImage dest1 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage dest2 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage dest3 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage dest4 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		BufferedImage dest5 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage dest6 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage dest7 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage dest8 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage dest9 = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		BufferedImage M = sumTheImages(op1.filter(Image, dest1),op2.filter(Image, dest2),op3.filter(Image, dest3),op4.filter(Image, dest4));
		BufferedImage S = sumTheImages(op5.filter(Image, dest5),op6.filter(Image, dest6),op7.filter(Image, dest7),op8.filter(Image, dest8),op9.filter(Image, dest9),M);
		return M;
		//return applyNormalizationFactor(M,S);
	}
	public BufferedImage applyNormalizationFactor(BufferedImage M, BufferedImage S)
	{
		int[][] sum = new int[M.getWidth()][ S.getHeight()];
		for(int i = 0;i<M.getWidth();i++)
		{
			for(int j = 0;j<M.getHeight();j++)
			{
				int mFactor = Utils.getAt(i, j, M);
				int sFactor = Utils.getAt(i, j, S);
				sum[i][j] = (int) ((double)mFactor/sFactor);
			}		
		}
		return Utils.imageFromTable(sum); 
	}
	
	public BufferedImage sumTheImages(BufferedImage ...bufferedImages)
	{
		int[][] sum = new int[bufferedImages[0].getWidth()][ bufferedImages[0].getHeight()];
		for(int i = 0;i<sum.length;i++)
		{
			for(int j = 0;j<sum[i].length;j++)
			{
				sum[i][j] = 0;
			}		
		}
		
		for(BufferedImage image : bufferedImages)
		{
			for(int i = 0;i<image.getWidth();i++)
			{
				for(int j = 0;j<image.getHeight();j++)
				{
					sum[i][j]+= Utils.getAt(i, j, image)*Utils.getAt(i, j, image);
				}		
			}
		}
		return Utils.imageFromTable(sum);
	}
	
	
	
	private float sqrtParam()
	{
		return (float) ((float)1/(Math.sqrt(2)*2));
	}

}
