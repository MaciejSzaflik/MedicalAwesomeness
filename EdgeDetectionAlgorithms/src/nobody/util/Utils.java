package nobody.util;

public class Utils {
	
	public static float[] mulArrayByS(float[] array,float scalar)
	{
		for(int i =0;i<array.length;i++)
			array[i]*=scalar;
		return array;
	}
}
