package behaviors;


import br.unicamp.cogsys.core.entities.Codelet;
import br.unicamp.cogsys.core.entities.MemoryObject;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import ws3dproxy.model.Thing;

/** 
 * 
 * @author klaus
 * 
 * 
 */

public class Forage extends Codelet {
    
        private MemoryObject knownMO;
        private List<Thing> known;
        private MemoryObject legsMO;


	/**
	 * Default constructor
	 */
	public Forage(){
		
	}

	@Override
	public void proc() {
            known = (List<Thing>) knownMO.getI();
            if (known.size() == 0) {
		JSONObject message=new JSONObject();
			try {
				message.put("ACTION", "FORAGE");
				legsMO.updateInfo(message.toString());
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }            
		
	}

	@Override
	public void accessMemoryObjects() {
            knownMO = this.getInput("KNOWN_APPLES");
            legsMO=this.getOutput("LEGS");

		// TODO Auto-generated method stub
		
	}
        
        @Override
        public void calculateActivation() {
            
        }


}
