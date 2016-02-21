package roadgraph;

import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;

/**
 * 
 * @author jscruz
 */
public class SearchPathDijkstras implements SearchPathWay {

	private static final int MAX_SEARCHES_ALLOWED = 3;
	
	private Map<GeographicPoint, MapNode> adjListMapNodes;
	private Deque<LinkedList<GeographicPoint>> searches;

	private Comparator<MapNode> distanceComparator = new Comparator<MapNode>() {
		public int compare(MapNode o1, MapNode o2) {
			int res = 0;
			if (o1.getDistance() < o2.getDistance()) {
				res = -1;
			} else if (o1.getDistance() > o2.getDistance()) {
				res = 1;
			}
			return res;
		}
	};
	
	public SearchPathDijkstras() {
		searches = new LinkedList<>();
	}	

	@Override
	public List<GeographicPoint> searchPath(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) 
	{
		if (!this.searches.isEmpty()) {
			Iterator<LinkedList<GeographicPoint>> iteratorSearch = searches.iterator();
			
			boolean keepLookingSearches = true;
			while (iteratorSearch.hasNext() && keepLookingSearches) {
				LinkedList<GeographicPoint> search = iteratorSearch.next();
				
				boolean add = false;
				List<GeographicPoint> res = new LinkedList<>();
				
				for (GeographicPoint gp : search) {
					if (gp.equals(start)) {
						keepLookingSearches = false;
						add = true;
					}
					if (add) {
						res.add(gp);
					}
				}
				
				if (!res.isEmpty()) {
					return res;
				}
			}
		}
		
		return dijkstra(start, goal, nodeSearched);
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal  The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	private List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3
		long itemsRemoved = 0;
		MapNode nodeGoal = new MapNode(goal);
		LinkedList<GeographicPoint> res = new LinkedList<>();

		// initialization
		PriorityQueue<MapNode> stack = new PriorityQueue<MapNode>(10, distanceComparator);
		Set<MapNode> visitedSet = new HashSet<>();
		Map<MapNode, MapNode> parentMap = new HashMap<>();

		// distances to infinity
		for (Map.Entry<GeographicPoint, MapNode> entry : this.adjListMapNodes.entrySet()) {
			entry.getValue().setDistance(Long.MAX_VALUE);
		}

		// bfs algo
		MapNode mapStartNode = this.adjListMapNodes.get(start);
		mapStartNode.setDistance(0);

		stack.add(mapStartNode);
		// System.out.println("++>Stak adding:" + start);

		boolean found = false;
		while (!stack.isEmpty() && !found) {
			MapNode current = stack.poll();
			itemsRemoved++;
			// System.out.println("Now Current is:" + current);

			if (!visitedSet.contains(current)) {
				visitedSet.add(current);

				if (current.equals(nodeGoal)) {
					found = true;
					break;
				}

				for (MapEdge edge : current.getEdges()) {
					MapNode neighbour = this.adjListMapNodes.get(edge.getEnd());

					if (!visitedSet.contains(neighbour)) {
						// System.out.println("Visitando:" + neighbour);

						double distanceToNeighbor = current.getDistance() + edge.getLength();

						if (distanceToNeighbor < neighbour.getDistance()) {
							neighbour.setDistance(distanceToNeighbor);
							// System.out.println("Updating distance: " +
							// distanceToNeighbor);

							parentMap.put(neighbour, current);
							stack.add(neighbour);

							nodeSearched.accept(neighbour.getLocation());
						}
					}
				}
			}
		}

		System.out.println("Items Removed with dijkstra:" + itemsRemoved);

		// adding the retrieving data to the response list
		if (found) {
			res.add(goal);
			MapNode nodeTo = new MapNode(goal);

			do {
				nodeTo = parentMap.get(nodeTo);
				res.addFirst(nodeTo.getLocation());

			} while (!nodeTo.getLocation().equals(start));
			
			// limit and store queue
			if (this.searches.size() == MAX_SEARCHES_ALLOWED) {
				this.searches.pollFirst();
			}
			this.searches.addLast(res);
		}

		return res;
	}

	@Override
	public void setAdjListMapNodes(Map<GeographicPoint, MapNode> adjListMapNodes) {
		this.adjListMapNodes = new HashMap<>(adjListMapNodes);
	}

}
