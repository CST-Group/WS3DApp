package sensors;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.List;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Thing;

/**
 * Vision codelet is responsible for getting vision information 
 * from the environment. It returns all objects in the visual field
 *  an its properties.
 *  
 *  @author klaus
 */
//TODO How about implementing getvs 0 in Client?
public class Vision extends Codelet{
    
	private MemoryObject visionMO;
        private Creature c;


	public Vision(Creature nc) {
            c = nc;		

	}

	@Override
	public void accessMemoryObjects() {
		visionMO=this.getOutput("VISION");
	}

	@Override
	public void proc() {
             c.updateState();
             List<Thing> lt = c.getThingsInVision();
             //System.out.println("Vision:" + lt.toString());
             visionMO.setI(lt);
             Class cl = List.class;
             visionMO.setT(cl);		
	}//end proc()

	@Override
	public void calculateActivation() {

	}



}// class end		





