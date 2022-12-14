import java.util.LinkedList;

public class SortingClass
{
	public static void heapSort(int[] array) 
	{
		buildMaxHeap(array, array.length);
		
		for(int i=array.length-1; i>=1; i--) 	
		{
			swap(array, i, 0);
			maxHeapify(array, 0, i);
		}
	}
	
	//This method tries to find correct place of a number which is located at startIndex of an array, according to maximum heap rules
	private static void maxHeapify(int[] array, int startIndex, int arraySize) 
	{
		int leftIndex = (startIndex * 2) + 1;
		int rightIndex = (startIndex * 2) + 2;
		
		int largestValIndex = startIndex;
		if(leftIndex < arraySize && array[leftIndex] > array[startIndex]) 
		{
			largestValIndex = leftIndex;
		}
		
		if(rightIndex < arraySize && array[rightIndex] > array[largestValIndex]) 
		{
			largestValIndex = rightIndex;
		}
		
		if(largestValIndex != startIndex) 
		{
			swap(array, startIndex, largestValIndex);
			maxHeapify(array, largestValIndex, arraySize);
		}
	}
	
	//This method converts input array into maximum heap form
	private static void buildMaxHeap(int[] array, int arraySize) 
	{
		for(int i=arraySize/2; i>=0; i--) 
		{
			maxHeapify(array, i , arraySize);
		}
	}
	
	private static void swap(int[] array, int firstIndex, int secondIndex) 
	{
		int temp = array[firstIndex];
		array[firstIndex] = array[secondIndex];
		array[secondIndex] = temp;
	}
	
	public static void bucketSort(int[] array, int bucketSize) 
	{
		LinkedList<Integer>[] bucket = createBucket(bucketSize);
		int max = findMaxInArray(array);
		int divider = (max / bucketSize) + 1;
		putNumbersToBucket(array,bucket,divider);
		
		for(int i=0;i<bucket.length;i++) 
		{
			bucket[i] = insertionSortToList(bucket[i]);
		}
		
		int count = 0;
		for(int i=0;i<bucket.length;i++) 
		{
			for(Integer val : bucket[i]) 
			{
				array[count++] = val;
			}
		}
	}
	
	//This function creates buckets which will store integer
	@SuppressWarnings("unchecked")
	private static LinkedList<Integer>[] createBucket(int bucketSize) 
	{
		LinkedList<Integer>[] bucket = new LinkedList[bucketSize + 1];
		
		for(int i=0;i<bucket.length;i++) 
		{
			bucket[i] = new LinkedList<Integer>();
		}
		
		return bucket;
	}
	
	private static int findMaxInArray(int[] array) 
	{
		int max = -10000;
		for(int i=0;i<array.length;i++) 
		{
			max = Math.max(max, array[i]);
		}
		
		return max;
	}
	
	//This method distributes numbers into buckets by using bucket sort rules
	private static void putNumbersToBucket(int[] array, LinkedList<Integer>[] bucket, int divider) 
	{
		for(int i=0;i<array.length;i++) 
		{
			bucket[(array[i] / divider)].addLast(array[i]);
		}
	}
	
	//This function takes list of integers as a parameter and does insertion sort to each list
	private static LinkedList<Integer> insertionSortToList(LinkedList<Integer> list) 
	{
		int[] array = new int[list.size()];
		int count = 0;
		for(Integer val : list) 
		{
			array[count++] = val;
		}
		
		for(int i=1;i<array.length;i++) 
		{
			for(int j=i-1;j>=0;j--) 
			{
				if(array[j] > array[j+1]) 
				{
					swap(array,j,j+1);
				}
				else 
				{
					break;
				}
			}
		}
		
		list = new LinkedList<Integer>();
		
		for(int i=0;i<array.length;i++) 
		{
			list.add(array[i]);
		}
		
		return list;
	}

	//Code is taken from "https://algs4.cs.princeton.edu/23quicksort/QuickDualPivot.java.html"
	public static void dualPivotQuickSort(int[] array, int leftPivotIndex, int rightPivotIndex) 
    { 
        if (rightPivotIndex <= leftPivotIndex) 
        {
        	return;
        }

        if (array[rightPivotIndex] < array[leftPivotIndex]) 
        {
        	swap(array, leftPivotIndex, rightPivotIndex);
        }

        int currentLeftIndex = leftPivotIndex + 1;
        int currentRightIndex = rightPivotIndex - 1;
        int i = leftPivotIndex + 1;
        
        while (i <= currentRightIndex) 
        {
            if(array[i] < array[leftPivotIndex])
            {
            	swap(array, currentLeftIndex++, i++);
            }
            
            else if(array[rightPivotIndex] < array[i]) 
            {
            	swap(array, i, currentRightIndex--);
            }
            
            else 
            {
            	i++;
            }
        }
        
        swap(array, leftPivotIndex, --currentLeftIndex);
        swap(array, rightPivotIndex, ++currentRightIndex);

        dualPivotQuickSort(array, leftPivotIndex, currentLeftIndex-1);
        
        if (array[currentLeftIndex] < array[currentRightIndex]) 
        {
        	dualPivotQuickSort(array, currentLeftIndex+1, currentRightIndex-1);
        }
        
        dualPivotQuickSort(array, currentRightIndex+1, rightPivotIndex);
    }
}
