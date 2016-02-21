package roadgraph;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import geography.GeographicPoint;

/**
 * 
 * @author jscruz
 */
public interface SearchPathWay {

	/**
	 * All Search Paths must implement this method
	 * @param start
	 * @param goal
	 * @param nodeSearched to be able to check the behavior from the UI.
	 * @return
	 */
	public List<GeographicPoint> searchPath(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched);

	/**
	 * 
	 * @param adjListMapNodes
	 */
	public void setAdjListMapNodes(Map<GeographicPoint, MapNode> adjListMapNodes);
	
}
