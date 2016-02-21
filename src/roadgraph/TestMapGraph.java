package roadgraph;

import java.util.List;

import geography.GeographicPoint;
import util.GraphLoader;

public class TestMapGraph {

	public static void main(String[] args)
	{
//		System.out.print("Making a new map...");
//		MapGraph theMap = new MapGraph();
//		System.out.print("DONE. \nLoading the map...\n");
//		
//		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
//		GraphLoader.loadRoadMap("data/mod3/map2.txt", theMap);
		
		/*** hand test
		GeographicPoint p1 = new GeographicPoint(4.0, 1.0);
		boolean a1 = theMap.addVertex(p1);
		System.out.println("Adding one: " + a1);
		
		GeographicPoint p2 = new GeographicPoint(4.0, 1.1);
		boolean a2 = theMap.addVertex(p2);
		System.out.println("Adding same?: " + a2);
		
		theMap.addEdge(p1, p2, "main", "someType", 2.3);
		***/
		
//		System.out.println("DONE.");
//		System.out.println(theMap.adjacencyString());
		
//		GeographicPoint start = new GeographicPoint(1.0, 1.0);
//		GeographicPoint goal = new GeographicPoint(8.0, -1.0);
		
//		GeographicPoint start = new GeographicPoint(7.0, 3.0);
//		GeographicPoint goal = new GeographicPoint(4.0, -1.0);
//		
//		double distance = start.distance(goal);
//		System.out.println("Distance: " + distance);
//		
//		long startTime = System.nanoTime();
//		List<GeographicPoint> bfs = theMap.bfs(start, goal);
//		List<GeographicPoint> bfs = theMap.dijkstra(start, goal);
//		List<GeographicPoint> bfs = theMap.aStarSearch(start, goal);
//		long endTime = System.nanoTime();
//		
//		long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
//		
//		System.out.println("Duracion: " + duration + "\n :");//entre 58 y 76 con dij
//		System.out.println(bfs);
		
//		String resultExpected = "[Lat: 1.0, Lon: 1.0, Lat: 4.0, Lon: 1.0, Lat: 7.0, Lon: 3.0, Lat: 8.0, Lon: -1.0]";
//		if (resultExpected.equals(bfs.toString())) {
//			System.out.println("==> Pass !! :)");
//		} else {
//			System.out.println("==> Check your code");
//		}
//		
//		String resultExpected = "[Lat: 1.0, Lon: 1.0, Lat: 4.0, Lon: 1.0, Lat: 5.0, Lon: 1.0, Lat: 6.5, Lon: 0.0, Lat: 8.0, Lon: -1.0]";
//		if (resultExpected.equals(bfs.toString())) {
//			System.out.println("==> Pass !! :)");
//		} else {
//			System.out.println("==> Check your code");
//		}
		
		// You can use this method for testing.  
		
		// Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		//GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");

//		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
//		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		GeographicPoint start = new GeographicPoint(1, 1);
		GeographicPoint end = new GeographicPoint(8, -1);
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
//		List<GeographicPoint> route = theMap.aStarSearch(start,end);
		
		System.out.println(route);
		
		
		start = new GeographicPoint(5, 1);
		end = new GeographicPoint(8, -1);
		
		List<GeographicPoint> route2 = theMap.dijkstra(start,end);
//		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		
		System.out.println(route2);
		
		
		
		start = new GeographicPoint(4, 0);
		end = new GeographicPoint(8, -1);
		
		List<GeographicPoint> route3 = theMap.dijkstra(start,end);
//		List<GeographicPoint> route3 = theMap.aStarSearch(start,end);
		
		System.out.println(route3);
		
		start = new GeographicPoint(4, -1);
		end = new GeographicPoint(8, -1);
		
		List<GeographicPoint> route4 = theMap.dijkstra(start,end);
//		List<GeographicPoint> route4 = theMap.aStarSearch(start,end);
		
		System.out.println(route4);
		
		
//		Comparator<MapNode> distanceComparator = new Comparator<MapNode>(){
//				public int compare(MapNode o1, 	MapNode o2) {
//					int res = 0;
//					if (o1.getDistance() < o2.getDistance()) {
//						res = -1;
//					} else if (o1.getDistance() < o2.getDistance()) {
//						res = 1;
//					}
//					return res;
//				}
//		};
//		
//		GeographicPoint g1 = new GeographicPoint(1.0, 1.0);
//		GeographicPoint g2 = new GeographicPoint(8.0, -1.0);
//		
//		MapNode n1 = new MapNode(g1);
//		n1.setDistance(4);
//		
//		MapNode n2 = new MapNode(g1);
//		n2.setDistance(8);
//		
//		MapNode n3 = new MapNode(g1);
//		n3.setDistance(5);
//		
//		PriorityQueue<MapNode> stack = new PriorityQueue<MapNode>(10, distanceComparator);
//		stack.add(n3);
//		stack.add(n2);
//		stack.add(n1);
//		
//		
//		MapNode poll = stack.poll();
//		System.out.println("Nodo Obtenido: " + poll);
//		
//		poll = stack.poll();
//		System.out.println("Nodo Obtenido: " + poll);
	}
}
