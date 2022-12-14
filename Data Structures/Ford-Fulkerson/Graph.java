import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graph
{
	private HashMap<String,Vertex> vertices;
	private HashMap<String,Edge> edges;
	
	private HashMap<Edge,Integer> bottleneckEdges;
	
	
	public Graph() 
	{
		this.vertices = new HashMap<String,Vertex>();
		this.edges = new HashMap<String,Edge>();
	}
	
	
	public void addEdge(String source,String destination,int cost) 
	{
		String edgeName = source + "-->" + destination;
		
		Edge edge = edges.get(edgeName);
		
		if(edge!=null) 
		{
			edge.increaseCost(cost);
		}
		
		else 
		{
			Vertex sourceVertex = vertices.get(source);
			if(sourceVertex == null) 
			{
				sourceVertex = new Vertex(source);
				vertices.put(source, sourceVertex);
			}
			
			Vertex destinationVertex = vertices.get(destination);
			if(destinationVertex == null) 
			{
				destinationVertex = new Vertex(destination);
				vertices.put(destination, destinationVertex);
			}
			
			edge = new Edge(source,destination,cost);
			sourceVertex.addEdge(edge);
			destinationVertex.addEdge(edge);
			
			edges.put(edgeName, edge);
		}
	}
	
	//Ford-Fulkerson algorithm used in this project due to I couldn't find any proper solution
	//https://github.com/Speedy-Consoles/Ford-Fulkerson 
	//as mentioned above general structure of the code based on the open source code
	//however i will explain the algorithm and reasons behind, for example what will happen when we applied a path to the residual graph
	//which won't lead to the maximum package answer.
	public void getMaximumPackages(String source, String destination) 
	{
		bottleneckEdges = new HashMap<>();	//we will hold edges which causes bottleneck and needs to be increased first to send more packages to the destination
		
		//Residual graph, which holds edge's and current package distribution related to that edge
		//we can see this as a graph which will have packages send on the edges
		HashMap<Edge, Integer> packageDistribution = new HashMap<>();	
		
		for(Edge edge : edges.values()) 
		{
			packageDistribution.put(edge, 0);	//every edge currently doesn't transfer any package
		}
		
		LinkedList<Edge> possiblePath;	//reference of possible path which we will get from Ford-Fulkerson specialized path search algorithm
		while(true) 
		{
			possiblePath = getPath(source,destination,packageDistribution);	  //trying to find a path
			
			if(possiblePath == null)   //if there is no path left to cover then algorithm finishes
			{
				break;
			}
			
			int minPackagesToSend = Integer.MAX_VALUE;	 //this variable will help us to find smallest edge cost on the path
			Edge bottleneckEdge = null;
			String currentVertex = source;	
			
			PriorityQueue<Integer> maxPackagesCanBeSendThroughEdges = new PriorityQueue<Integer>();	//by using min pq we will hold bottleneck edges max capacities
			
			for(Edge edge : possiblePath) 
			{
				int maxPackageCanBeSendInEdge;	//this variable will help us to calculate maximum packages can be send through the edge
				if(edge.getSource().equals(currentVertex)) 	//forward edge
				{
					//for example the cost of edge is 9 and the distributed package on that edge is currently 4, so we can send max 5 packages on this edge
					maxPackageCanBeSendInEdge = edge.getCost() - packageDistribution.get(edge);	
					currentVertex = edge.getDestination();	// move forward
				}
				
				else 	//backward edge
				{
					maxPackageCanBeSendInEdge = packageDistribution.get(edge);	
					currentVertex = edge.getSource();	//get back
				}
				
				if(maxPackageCanBeSendInEdge < minPackagesToSend) 
				{
					minPackagesToSend = maxPackageCanBeSendInEdge;
					bottleneckEdge = edge;
				}
				maxPackagesCanBeSendThroughEdges.add(maxPackageCanBeSendInEdge);
			}
			
			//in every path we have at least one bottleneck
			if(!bottleneckEdge.getSource().equals(source)) 	//we don't need to increase edges starting from source at first
			{
				maxPackagesCanBeSendThroughEdges.remove();	//first is the bottleneck edge's package send so we ignore it
				if(maxPackagesCanBeSendThroughEdges.peek() == minPackagesToSend) 
				{
					//we add 1 because if we have more than one bottleneck edge, our algorithm needs to work
					bottleneckEdges.put(bottleneckEdge, maxPackagesCanBeSendThroughEdges.peek() - minPackagesToSend + 1);
				}
				else 
				{
					bottleneckEdges.put(bottleneckEdge, maxPackagesCanBeSendThroughEdges.peek() - minPackagesToSend);	//we are trying to equalize our bottleneck edge from this path to the second bottleneck edge
				}
			}
			
			currentVertex = source;
			for(Edge edge : possiblePath) 
			{
				if(edge.getSource().equals(currentVertex)) 	//we send packages to our forward edges
				{
					packageDistribution.put(edge, packageDistribution.get(edge) + minPackagesToSend);
					currentVertex = edge.getDestination();
				}
				
				else  //we remove packages from backward edges because if we send through package on that edge we won't find the maximum answer
				{
					packageDistribution.put(edge, packageDistribution.get(edge) - minPackagesToSend);
					currentVertex = edge.getSource();
				}
			}
		}
		
		int maximumPackagesCanBeSend = 0;
		Vertex sourceVertex = vertices.get(source);
		
		//lastly we add every edge cost on the residual graph's source vertex and it's the maximum packages send to the destination vertex
		if(sourceVertex != null) 
		{
			for(Edge edge : sourceVertex.getEdges()) 
			{
				maximumPackagesCanBeSend += packageDistribution.get(edge);
			}
		}
		
		System.out.println("From " + source + " to " + destination + ": " + maximumPackagesCanBeSend + " amount of packages can be send at maximum!");
	}

	
	//in this function we find a path from source to destination which doesn't covered before
	//we do it by using residual graph and our graph, so for example on our first iteration on the small graph
	//lets assume we start from A to B to E to C and min edge cost is 4 and in our residual graph, we have packages send on each of this edges
	//so we can assume we found a path and send 4 packages now we start over again and come to this function
	//and looking for path, we start from A and goes to B because 9-4 = 5, then we check B-E edge and we see that we have already send 
	//amount of package that can this edge can hold(4-4=0) so we can say that if we see 0 we check another edge and goes to D.
	//Algorithm based on whenever we see 0 we change edge or go back to vertex and look again, indeed we have backward edges again.
	//which takes crucial part to find maximum packages can be send
	//computes breadth first search algorithm
	public LinkedList<Edge> getPath(String source, String destination, HashMap<Edge,Integer> packageDistribution)
	{
		//this hash map will help us to finalize the path when we arrive to destination vertex
		//it will hold name of the vertex we've used and the edge to find a path
		//and we will use this as to avoid going back
		HashMap<String,Edge> parent = new HashMap<>();	
		
		LinkedList<String> verticesToBeVisited = new LinkedList<>();	//we will use linked list to traverse vertices in order, bfs.
		
		parent.put(source, null);
		verticesToBeVisited.add(source);
		
		boolean isPathFound = false;
		
		while(verticesToBeVisited.size() != 0) 
		{
			LinkedList<String> newVerticesToBeVisited = new LinkedList<>();	//we can't add value to linkedlist while we are traversing through it so we need another object
			
			//we will traverse vertices until we arrive to the destination vertex, we will traverse only once each vertex until
			//we found a way to the destination vertex or there won't be any path at all
			for(String vertexName : verticesToBeVisited) 	
			{
				Vertex currentVertex = vertices.get(vertexName);
				for(Edge edge : currentVertex.getEdges()) 	//we will check every edge on the vertex, it can been seen that we don't do distinction between incoming and outgoing edges
				{
					//if this is a forward edge and if we didn't add destination vertex of the edge into hash map and if we have space to send packages
					//then add that edge into hash map and check if we arrived to the destination, if not we need to visit that vertex too, in other iterations we don't currently know when depends on the size of list
					if((edge.getSource().equals(vertexName)) && (parent.get(edge.getDestination()) == null)  && (packageDistribution.get(edge) < edge.getCost()))
					{
						parent.put(edge.getDestination(), edge);
						if(edge.getDestination().equals(destination)) 
						{
							isPathFound = true;
							break;
						}
						newVerticesToBeVisited.add(edge.getDestination());
					}
					//if it's a backward edge and didn't add source vertex to the hash map and if we currently send package through that edge
					//we visit the source of the edge again to find better augmented path
					else if((edge.getDestination().equals(vertexName)) && (parent.get(edge.getSource()) == null) && (packageDistribution.get(edge) > 0)) 
					{
						parent.put(edge.getSource(), edge);
						if(edge.getSource().equals(destination)) 
						{
							isPathFound = true;
							break;
						}
						newVerticesToBeVisited.add(edge.getSource());
					}
				}
				if(isPathFound) 
				{
					break;
				}
			}
			if(isPathFound) 
			{
				break;
			}
			
			verticesToBeVisited = newVerticesToBeVisited;		//change the reference 
		}
		
		if(!isPathFound) 	
		{
			return null;
		}
		
		String vertexName = destination;
		LinkedList<Edge> path = new LinkedList<>();
		
		//from here we know that we have found a path
		//we start from destination vertex and by using hash map we go back until source vertex and we add every edge into path
		while(!vertexName.equals(source)) 
		{
			Edge edge = parent.get(vertexName);
			path.addFirst(edge);
			if(edge.getSource().equals(vertexName)) 
			{
				vertexName = edge.getDestination();
			}
			else 
			{
				vertexName = edge.getSource();
			}
		}
		return path;
	}

	
	public void displayEdgesThatCausesBottleneck() 
	{
		System.out.println("-----------------------------");
		System.out.println("Edges needs to be increased at first are:");
		for(Edge edge : bottleneckEdges.keySet()) 
		{
			System.out.println(edge.getSource() + " -- " + edge.getDestination());
		}
		System.out.println("-----------------------------");
	}
	
	
	public void increaseEdgesThatCausesBottleneck() 
	{
		System.out.println("-----------------------------");
		for(Edge edge : bottleneckEdges.keySet()) 
		{
			System.out.print(edge.toString());
			edge.increaseCost(bottleneckEdges.get(edge));
			System.out.println(" -> " + edge.getCost());
		}
		System.out.println("-----------------------------");
		System.out.println("After improvements we can now: ");
	}
	
	//My personal algorithm was based around to send packages to vertices that won't "eat" packages as much as possible.
	//it did worked on small graph luckily because I missed many exceptions, such as what happens if i send package to vertex which no way
	//that package will end up on the destination. I thought about that and found a solution "okay let's send package but firstly make sure there is
	//a path to destination vertex", the runtime went high and it wasn't enough and even if there is path it doesn't know it's a path that will lead
	//to "maximum answer". I tried some other things but I got memory errors so I decided to use fulkerson which is min cut-max flow algorithm
	//for example in one of my tries I got 36k as an answer by using my algorithm but ford-fulkerson gives 127k
	//I could have tried and worked more on my algorithm but my first approach was wrong so no way I could have ended with the correct answer
	//that's why i choosed to use this algorithm over mine and source code because i couldn't understand backward edges properly and 
	//how to residual graph or path finding
	/*
	public void calculateMaxPackagesFromSourceToDestination(String source,String destination) 
	{
		Vertex sourceVertex = vertices.get(source);	
		Vertex destinationVertex = vertices.get(destination);
		
		if((sourceVertex == null) || (destinationVertex == null))
		{
			System.out.println("Either source or destination vertex doesn't exist it may be both doesn't exist!");
		}
		
		else
		{
			LinkedList<Vertex> verticesToBeVisited = new LinkedList<>();
			sourceVertex.setCurrentPackage(sourceVertex.getOutgoingEdgesCost());
			verticesToBeVisited.addLast(sourceVertex);
			
			boolean isEdgeChoiceRequired = false;
			
			while(verticesToBeVisited.size() != 0) 
			{
				Vertex currentVertex = verticesToBeVisited.removeFirst();
				currentVertex.setTraversed(true);
				
				isEdgeChoiceRequired = false;
				Heap<Integer,Vertex> tempVertexHolder = new Heap<>(10);
				
				if(currentVertex != destinationVertex) 
				{
					if(currentVertex.getCurrentPackage() >= currentVertex.getOutgoingEdgesCost()) 
					{
						for(Edge outgoingEdge : currentVertex.getOutgoingEdges()) 
						{
							Vertex oppositeVertex = outgoingEdge.getOpposite(currentVertex);
							
							oppositeVertex.addPackage(outgoingEdge.getCost(), currentVertex);
							
							if(!oppositeVertex.isTraversed()) 
							{
								verticesToBeVisited.addLast(oppositeVertex);
								oppositeVertex.setTraversed(true);
							}
						}
						currentVertex.setCurrentPackage(0);
					}
					
					else 
					{
						isEdgeChoiceRequired = true;
						
						for(Edge outgoingEdge : currentVertex.getOutgoingEdges()) 
						{
							Vertex oppositeVertex = outgoingEdge.getOpposite(currentVertex);
							
							if(oppositeVertex == destinationVertex) 
							{
								tempVertexHolder.insert(Integer.MAX_VALUE, oppositeVertex);
							}
							
							else 
							{
								tempVertexHolder.insert(oppositeVertex.getOutgoingEdgesCost() - oppositeVertex.getCurrentPackage(), oppositeVertex);
							}
							
							oppositeVertex.setPossibleIncomingPackageCost(outgoingEdge.getCost());
						}
					}
					
					if(isEdgeChoiceRequired) 
					{
						while((currentVertex.getCurrentPackage() != 0) && (tempVertexHolder.size() != 0))
						{
							int emptySpace = tempVertexHolder.peek().getKey();
							Vertex bestPossibleVertex = tempVertexHolder.removeRoot().getValue();

							int possiblePackageToSend = Math.min(emptySpace, bestPossibleVertex.getPossibleIncomingPackageCost());
							
							bestPossibleVertex.addPackage(Math.min(possiblePackageToSend,currentVertex.getCurrentPackage()), currentVertex);
							
							if(!bestPossibleVertex.isTraversed()) 
							{
								verticesToBeVisited.addLast(bestPossibleVertex);
								bestPossibleVertex.setTraversed(true);
							}
						}
					}
				}
			}
			
			System.out.println(destination +" takes " + destinationVertex.getCurrentPackage() + " packages from " + source);
		}
	}
	
	*/
}