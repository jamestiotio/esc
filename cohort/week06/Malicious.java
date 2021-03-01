import java.lang.Object;
import java.util.HashMap;

class exercise3 {
	
 private static final HashMap<Integer, String> hm = new HashMap<Integer, String>();
 
 /* public static HashMap<Integer, String> getHM() {

		// we return a clone of the hashmap if you do not want it to be modified outside
		return new HashMap<Integer, String>(hm);
	} */

	//methods are skipped

}

public class Malicious {

	public static void main(String args[]) {

		exercise3 e3 = new exercise3();

		e3.hm.put(2, "sa");
		e3.hm.put(2, "sass"); 
		
		/* HashMap<Integer,String> hmmm = e3.getHM();	
		hmmm.put(2, "sa");
		hmmm.put(2, "sass"); */
	}
}

