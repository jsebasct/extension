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
 * Algorithm Behavior for A* Search
 * @author jscruz
 */
public class SearchPathAStar implements SearchPathWay {

	private static final int MAX_SEARCHES_ALLOWED = 3;
	
	private Map<GeographicPoint, MapNode> adjListMapNodes;
	private Deque<LinkedList<GeographicPoint>> searches;
	

	public SearchPathAStar() {
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
		
		return this.aStarSearch(start, goal, nodeSearched);
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start   The starting location
	 * @param goal	  The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how
	 *            to use it.
	 * @return The list of intersections that form the shortest path from start
	 *         to goal (including both start and goal).
	 */
	private List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) 
	{
		long itemsRemoved = 0;
		MapNode nodeGoal = new MapNode(goal);
		LinkedList<GeographicPoint> res = new LinkedList<>();

		ComparatorAStarSearch comparatorA = new ComparatorAStarSearch(nodeGoal);

		// initialization
		PriorityQueue<MapNode> stack = new PriorityQueue<MapNode>(10, comparatorA);
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

			if (!visitedSet.contains(current)) {
				visitedSet.add(current);

				if (current.equals(nodeGoal)) {
					found = true;
					break;
				}

				for (MapEdge edge : current.getEdges()) {

					MapNode neighbor = this.adjListMapNodes.get(edge.getEnd());

					if (!visitedSet.contains(neighbor)) {
						// update n's distance
						double distanceToNeighbor = current.getDistance() + edge.getLength();

						if (distanceToNeighbor < neighbor.getDistance()) {
							neighbor.setDistance(distanceToNeighbor);

							parentMap.put(neighbor, current);
							stack.add(neighbor);

							nodeSearched.accept(neighbor.getLocation());
						}
					}
				}
			}
		}

		System.out.println("Items Removed with A* search: " + itemsRemoved);

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

	private class ComparatorAStarSearch implements Comparator {

		MapNode goalNode = null;

		ComparatorAStarSearch(MapNode goalNode) {
			this.goalNode = goalNode;
		}

		@Override
		public int compare(Object o1, Object o2) {

			MapNode start = (MapNode) o1;
			MapNode end = (MapNode) o2;

			double startDistanceGoal = start.getLocation().distance(goalNode.getLocation());
			double endDistanceGoal = end.getLocation().distance(goalNode.getLocation());

			int res = 0;
			if (start.getDistance() + startDistanceGoal < end.getDistance() + endDistanceGoal) {
				res = -1;
			} else if (start.getDistance() + startDistanceGoal > end.getDistance() + endDistanceGoal) {
				res = 1;
			}
			return res;
		}
	}

	@Override
	public void setAdjListMapNodes(Map<GeographicPoint, MapNode> adjListMapNodes) {
		this.adjListMapNodes = new HashMap<>(adjListMapNodes);
	}
}
