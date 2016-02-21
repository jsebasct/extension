/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
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

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	
	private Map<GeographicPoint, MapNode> adjListMapNodes;
	
	private SearchPathWay searchWayDJ;
	private SearchPathWay searchWayAStar;
	
	private int edgeNumber;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		edgeNumber = 0;
		adjListMapNodes = new HashMap<GeographicPoint, MapNode>();
		
		this.searchWayAStar = new SearchPathAStar();
		this.searchWayDJ = new SearchPathDijkstras();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return this.adjListMapNodes.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * W2
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		 return this.adjListMapNodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
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
		boolean res = false;
		System.out.println("Adding a Vertex:" + location);
		
		if (location != null && !this.adjListMapNodes.containsKey(location)) {
			this.adjListMapNodes.put(location, new MapNode(location));
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

		//System.out.println("Adding edge from: "+ from + " to: " + to);
		
		// validations
		if (from == null || to == null || roadName == null || roadType == null) {
			throw new IllegalArgumentException("Some argument is null and that is not allowed");
		}
		
		if (length < 0) {
			throw new IllegalArgumentException("length argument can not be less than zero");
		}
		
		if (!this.adjListMapNodes.containsKey(from) || !this.adjListMapNodes.containsKey(to)) {
			throw new IllegalArgumentException("from or to are not vertex");
		}
		
		// adding edge
		MapNode node = this.adjListMapNodes.get(from);
		MapEdge edgeNode = new MapEdge(from, to, roadName, roadType, length);
		
		if ( node.addEdge(edgeNode) ) {
			edgeNumber++;
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
	 *  Implement this method in WEEK 2
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		LinkedList<GeographicPoint> res = new LinkedList<>();

		// initialization
		Deque<GeographicPoint> stack = new LinkedList<GeographicPoint>();
		Set<GeographicPoint> visitedSet = new HashSet<>();
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();

		// bfs algo
		stack.push(start);
		visitedSet.add(start);
		// System.out.println("++>Stak adding:" + start);

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

					// Hook for visualization. See writeup.
					// System.out.println("++>Stak adding:" + neighbour);
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
		
		//return this.adjListsMap.get(current);
		
		MapNode node = this.adjListMapNodes.get(current);
		
		List<GeographicPoint> res = new LinkedList<>();
		for (MapEdge nodeEdge : node.getEdges()) {
			res.add(nodeEdge.getEnd());
		}
		
		return res;
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
		System.out.println("++++++++++++++++++++Starting Dijkstra Search++++++++++++++++++++");
		
		searchWayDJ.setAdjListMapNodes(this.adjListMapNodes);
		return searchWayDJ.searchPath(start, goal, nodeSearched);
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
		System.out.println("++++++++++++++++++++Starting A* Search++++++++++++++++++++");
		
		searchWayAStar.setAdjListMapNodes(this.adjListMapNodes);
		return searchWayAStar.searchPath(start, goal, nodeSearched);
	}

	/**
	 * Generate string representation of adjacency list
	 * @return the String
	 */
	public String adjacencyString() {
		String s = "Adjacency list";
		s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";
		
		for (GeographicPoint v : adjListMapNodes.keySet()) {
			s += "\n\t"+v+":::::: ";
			MapNode mapNode = adjListMapNodes.get(v);
			for (MapEdge w : mapNode.getEdges()) {
				s += w.getEnd()+"||| ";
			}
		}
		
		return s;
	}
}
