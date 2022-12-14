import java.io.File;
import java.util.Scanner;

public class Test
{
	public static void searchTheWords(Graph graph) 
	{
		Scanner file = null;
		try 
		{
			file=new Scanner(new File("C:\\Users\\zombi\\Desktop\\mygraph.txt"),"UTF-8");
		}
		catch(Exception e) 
		{
			System.out.println("File doesn't exist!");
		}
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				String[] array = file.nextLine().split("\\s+");
				
				graph.addEdge(array[0], array[1], Integer.parseInt(array[2]));
			}
			file.close();
		}
	}
		
	
	public static void main(String[] args)
	{
		Graph graph = new Graph();
		
		searchTheWords(graph);
		
		graph.getMaximumPackages("A", "C");
		
		graph.increaseEdgesThatCausesBottleneck();
			
		graph.getMaximumPackages("A", "C");
	}

}
