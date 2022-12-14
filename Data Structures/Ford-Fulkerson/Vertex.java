import java.util.LinkedList;

public class Vertex
{
	private String name;
	private LinkedList<Edge> edges;
	
	public Vertex(String name) 
	{
		this.name = name;
		this.edges = new LinkedList<Edge>();
	}

	public void addEdge(Edge edge) 
	{
		this.edges.add(edge);
	}
	
	public int getSize() 
	{
		return this.edges.size();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public Iterable<Edge> getEdges()
	{
		return this.edges;
	}
}
