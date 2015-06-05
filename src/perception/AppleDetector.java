/**
 * 
 */
package perception;

import br.unicamp.cogsys.core.entities.MemoryObject;
import br.unicamp.cogsys.core.entities.RawMemory;
import br.unicamp.cogsys.perception.Perception;
import java.util.Collections;
import java.util.List;
import ws3dproxy.model.Thing;

/**
 * Detect apples in the vision field.
 * 	This class detects a number of things related to apples, such as if there are any within reach,
 * any on sight, if they are rotten, and so on.
 * 
 * @author klaus
 *
 */
public class AppleDetector extends Perception{
	//debug variables
	private boolean printMap=false;
	private RawMemory rm=RawMemory.getInstance();

	private double minDist=50; //minimum distance to be considered within reach
	private double xs=-100,ys=-100;
	private double[] closest_apple={-1.0,-1.0,-1.0}; //{[x,y,d}
	private boolean closest_apple_is_rotten=false;
	private boolean apple_at_reach=false;
	private boolean knows_apple=false;
        private MemoryObject visionMO;
        private MemoryObject knownApplesMO;
        

	public AppleDetector(){
		
	}

	@Override
	public void accessMemoryObjects() {
		this.visionMO=this.getInput("VISION");
		this.knownApplesMO=this.getOutput("KNOWN_APPLES");
	}

	@Override
	public void proc() {
            List<Thing> vision;
            List<Thing> known;
            vision = Collections.synchronizedList((List<Thing>) visionMO.getI());
            known = Collections.synchronizedList((List<Thing>) knownApplesMO.getI());
            for (Thing t : vision) {
               boolean found = false;
               synchronized(known) {
                  for (Thing e : known)
                    if (t.getName().equals(e.getName())) {
                      found = true;
                      break;
                    }
               }
               if (found == false && t.getName().contains("PFood") && !t.getName().contains("NPFood")) known.add(t);
            }    
		
	}// end proc
        
        @Override
        public void calculateActivation() {
        
        }


}//end class


