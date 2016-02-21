package roadgraph;

import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {

	private GeographicPoint location;
	private List<MapEdge> edges;
	private double distance;

	public MapNode(GeographicPoint location) {
		this.location = location;
		edges = new LinkedList<>();
	}

	public List<MapEdge> getEdges() {
		return edges;
	}

	public GeographicPoint getLocation() {
		return location;
	}

	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean addEdge(MapEdge edge) {
		boolean res = false;
		if (!this.edges.contains(edge)) {
			res = this.edges.add(edge);
		}
		return res;
	}
	
	

//	@Override
//	public String toString() {
//		return "Node [loc=" + location + "]";
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}
	
	

	@Override
	public String toString() {
		return "MapNode [location=" + location + ", distance=" + distance + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapNode other = (MapNode) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	// @Override
	// public boolean equals(Object obj) {
	// boolean res = false;
	// System.out.println("MapNode.equals has been called");
	// if (obj instanceof MapNode) {
	// MapNode otherNode = (MapNode) obj;
	// res = this.getLocation().equals(otherNode.getLocation());
	// }
	// return res;
	// }

}
