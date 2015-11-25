package nobody.util;

public class Utils {
	
	public static float[] mulArrayByS(float[] array,float scalar)
	{
		for(int i =0;i<array.length;i++)
			array[i]*=scalar;
		return array;
	}
	public static int clamp(int value,int min,int max)
	{
		return Math.max(min, Math.min(max, value));
	}
}
