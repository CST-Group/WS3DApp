package sensors;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import memory.CreatureInnerSense;
import ws3dproxy.model.Creature;


/**
 *  This class reads information from this agent's state and writes it to an inner sense sensory buffer.
 * @author klaus
 *
 */

public class InnerSense extends Codelet {

	private MemoryObject innerSenseMO;
        private Creature c;
        private CreatureInnerSense cis;

	public InnerSense(Creature nc) {
		c = nc;
	}
	@Override
	public void accessMemoryObjects() {
		innerSenseMO=this.getOutput("INNER");
                cis = (CreatureInnerSense) innerSenseMO.getI();
	}
	
	public void proc() {
             cis.position = c.getPosition();
             cis.pitch = c.getPitch();
             cis.fuel = c.getFuel();
             cis.FOV = c.getFOV();
	}
        
        @Override
        public void calculateActivation() {
        
        }



}
