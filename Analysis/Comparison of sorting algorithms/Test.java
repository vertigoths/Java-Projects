public class Test
{
	public static void main(String[] args)
	{
		int length = 10000;
		int[] array = new int[length];
		fillTheInputArray(array, 2);	//0-1-2-3
		
		long startTime = System.nanoTime();
		SortingClass.bucketSort(array, 10000);
		System.out.println(((System.nanoTime() - startTime) / 1000000f) + " ms");
		
		System.out.println(isCorrectlySorted(array));
	}
	
	private static void fillTheInputArray(int[] array, int type) 
	{
		int num = assignFirstNumber(type, Integer.MAX_VALUE);
		for(int i=0; i<array.length; i++) 
		{
			array[i] = num;
			num = getNumber(num, type, Integer.MAX_VALUE);
		}
	}
	
	private static int getNumber(int current, int type, int max) 
	{
		if(type == 0) 
		{
			return current;
		}
		
		if(type == 1) 
		{
			return (int)(Math.random() * max);
		}
		
		else if(type == 2) 
		{
			return current + (int)(Math.random() * 10);
		}
		
		return current - (int)(Math.random() * 10);
	}
	
	private static int assignFirstNumber(int type, int max) 
	{
		if(type == 0 || type == 1) 
		{
			return (int)(Math.random() * max);
		}
		
		return (int)(Math.random() * (max/2));
	}

	private static boolean isCorrectlySorted(int[] array) 
	{
		int prev = -10000;
		for(int i=0;i<array.length;i++) 
		{
			if(array[i] < prev) 
			{
				return false;
			}
			prev = array[i];
		}
		
		return true;
	}
}
