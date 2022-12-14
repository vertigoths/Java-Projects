//Default Edge class
public class Edge
{
	private int cost;
	private String source;
	private String destination;
	
	public Edge(String source, String destination, int cost) 
	{
		this.source = source;
		this.destination = destination;
		this.cost = cost;
	}
	
	public int getCost() 
	{
		return this.cost;
	}
	
	public void increaseCost(int cost) 
	{
		this.cost += cost;
	}
	
	public String getSource() 
	{
		return this.source;
	}
	
	public String getDestination() 
	{
		return this.destination;
	}
	
	@Override
	public String toString() 
	{
		return this.source + "-" + this.destination + " | " + this.cost;
	}
}
