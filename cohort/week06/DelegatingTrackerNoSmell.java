package Week8;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; //this is thread-safe!
import java.util.concurrent.ConcurrentMap; //this is thread-safe!

public class DelegatingTrackerSmell {
	private final ConcurrentMap<String, Point> locations;
	
	public DelegatingTrackerSmell(Map<String, Point> locations) {
		this.locations = new ConcurrentHashMap<String, Point>(locations);
	}
	
	public synchronized Map<String, Point> getLocations () {
		return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
	}
	
	public synchronized Point getLocation (String id) {
		return locations.get(id);
	}
	
	public synchronized void setLocation (String id, int x, int y) {		
		if (!locations.containsKey(id)) {
			throw new IllegalArgumentException ("No such ID: " + id);			
		}
		
		locations.get(id).set(x, y);
	}

	////// more code related to Delegating Tracker Class //////

}

// sudiptac: this class is not doing much, without more responsibility, this class may become a data class	
class Point {
		private int x, y;
		
		public Point (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public synchronized int[] get() {
			return new int[] {x, y};
		}
		
		public synchronized void set(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// sudiptac: more responsibilities on the point class, such as transformations on points, if needed, 
		// will remove the "data class" code smell

		// rotate a point by degree "rotation"
		public void rotate(int rotation) {
		
		
		}
	
		// scale a point by a scaling factor "scaleFactor"
		public void scale(int scaleFactor) {
		
		
		}
}
