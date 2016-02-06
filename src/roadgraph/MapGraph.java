/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	
	//TODO: Add your member variables here in WEEK 2
	//private Map<GeographicPoint, ArrayList<GeographicPoint>> adjListsMap;
	private Map<GeographicPoint, LinkedList<GeographicPoint>> adjListsMap;
	private int vertexNumber;
	private int edgeNumber;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		vertexNumber = 0;
		edgeNumber = 0;
		//adjListsMap = new HashMap<GeographicPoint, ArrayList<GeographicPoint>>();
		adjListsMap = new HashMap<GeographicPoint, LinkedList<GeographicPoint>>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return vertexNumber;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		return this.adjListsMap.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return edgeNumber;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * 
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		boolean res = false;
		System.out.println("Adding a Vertex:" + location);
		
		if (location != null && !this.adjListsMap.containsKey(location)) {
			//this.adjListsMap.put(location, new ArrayList<GeographicPoint>(0));
			this.adjListsMap.put(location, new LinkedList<GeographicPoint>());
			vertexNumber++;
			res = true;
		}
		
		return res;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * 
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * 
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) 
			throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		//System.out.println("Adding edge from: "+ from + " to: " + to);
		
		// validations
		if (from == null || to == null || roadName == null || roadType == null) {
			throw new IllegalArgumentException("Some argument is null and that is not allowed");
		}
		
		if (length < 0) {
			throw new IllegalArgumentException("length argument can not be less than zero");
		}
		
		if (!this.adjListsMap.containsKey(from) || !this.adjListsMap.containsKey(to)) {
			throw new IllegalArgumentException("from or to are not vertex");
		}
		
		// adding edge
		List<GeographicPoint> arrayList = this.adjListsMap.get(from);
		if (!arrayList.contains(to)) {
			edgeNumber++;
			arrayList.add(to);
		}
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		LinkedList<GeographicPoint> res = new LinkedList<>();
		
		// initialization
		Deque<GeographicPoint> stack = new LinkedList<GeographicPoint>();
		Set<GeographicPoint> visitedSet = new HashSet<>();
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		
		// bfs algo
		stack.push(start);
		visitedSet.add(start);
		//System.out.println("++>Stak adding:" + start);
		
		boolean found = false;
		while (!stack.isEmpty() && !found) {
			GeographicPoint current = stack.pop();
			if (current.equals(goal)) {
				found = true;
			}
			
			for (GeographicPoint neighbour : this.getNeighbours(current)) {
				
				if (!visitedSet.contains(neighbour)) {
					visitedSet.add(neighbour);
					parentMap.put(neighbour, current);
					stack.add(neighbour);
					
					// Hook for visualization.  See writeup.
					//System.out.println("++>Stak adding:" + neighbour);
					nodeSearched.accept(neighbour);
				}
			}
		}
		
		// adding the retrieving data to the response list
		if (found) {
			res.add(goal);
			GeographicPoint path = goal;
			
			do {
				path = parentMap.get(path);
				res.addFirst(path);
			} while (!path.equals(start));
		}
		
		return res;
	}
	

	private List<GeographicPoint> getNeighbours(GeographicPoint current) {
		// TODO Auto-generated method stub
		return this.adjListsMap.get(current);
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}
	
	

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/**
	 * Generate string representation of adjacency list
	 * @return the String
	 */
	public String adjacencyString() {
		String s = "Adjacency list";
		s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";

		for (GeographicPoint v : adjListsMap.keySet()) {
			s += "\n\t"+v+":::::: ";
			for (GeographicPoint w : adjListsMap.get(v)) {
				s += w+"||| ";
			}
		}
		return s;
	}
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...\n");
		
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		
		/*** hand test
		GeographicPoint p1 = new GeographicPoint(4.0, 1.0);
		boolean a1 = theMap.addVertex(p1);
		System.out.println("Adding one: " + a1);
		
		GeographicPoint p2 = new GeographicPoint(4.0, 1.1);
		boolean a2 = theMap.addVertex(p2);
		System.out.println("Adding same?: " + a2);
		
		theMap.addEdge(p1, p2, "main", "someType", 2.3);
		***/
		
		System.out.println("DONE.");
		System.out.println(theMap.adjacencyString());
		
		GeographicPoint start = new GeographicPoint(1.0, 1.0);
		GeographicPoint goal = new GeographicPoint(8.0, -1.0);
		
		long startTime = System.nanoTime();
		List<GeographicPoint> bfs = theMap.bfs(start, goal);
		long endTime = System.nanoTime();
		
		long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
		
		System.out.println("Resultado " + duration + "\n :");
		System.out.println(bfs);
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		*/
		
	}
	
}
