package behaviors;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.json.JSONException;
import org.json.JSONObject;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import memory.CreatureInnerSense;
import java.util.List;
import ws3dproxy.model.Thing;

public class EatClosestApple extends Codelet {

	private MemoryObject closestAppleMO;
	private MemoryObject innerSenseMO;
        private MemoryObject knownMO;
	private int reachDistance;
	private MemoryObject handsMO;
        Thing closestApple;
        CreatureInnerSense cis;
        List<Thing> known;

	public EatClosestApple(int reachDistance) {
		this.reachDistance=reachDistance;
	}

	@Override
	public void accessMemoryObjects() {
		closestAppleMO=this.getInput("CLOSEST_APPLE");
		innerSenseMO=this.getInput("INNER");
		handsMO=this.getOutput("HANDS");
                knownMO = this.getOutput("KNOWN_APPLES");
	}

	@Override
	public void proc() {
                String appleName="";
                closestApple = (Thing) closestAppleMO.getI();
                cis = (CreatureInnerSense) innerSenseMO.getI();
                known = (List<Thing>) knownMO.getI();
		//Find distance between closest apple and self
		//If closer than reachDistance, eat the apple
		
		if(closestApple != null)
		{
			double appleX=0;
			double appleY=0;
			try {
				appleX=closestApple.getX1();
				appleY=closestApple.getY1();
                                appleName = closestApple.getName();
                                

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double selfX=cis.position.getX();
			double selfY=cis.position.getY();

			Point2D pApple = new Point();
			pApple.setLocation(appleX, appleY);

			Point2D pSelf = new Point();
			pSelf.setLocation(selfX, selfY);

			double distance = pSelf.distance(pApple);
			JSONObject message=new JSONObject();
			try {
				if(distance<reachDistance){ //eat it						
					message.put("OBJECT", appleName);
					message.put("ACTION", "EATIT");
					handsMO.updateInfo(message.toString());
                                        DestroyClosestApple();
				}else{
					handsMO.updateInfo("");	//nothing
				}
				
//				System.out.println(message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			handsMO.updateInfo("");	//nothing
		}
        //System.out.println("Before: "+known.size()+ " "+known);
        
        //System.out.println("After: "+known.size()+ " "+known);
	//System.out.println("EatClosestApple: "+ handsMO.getInfo());	

	}
        
        @Override
        public void calculateActivation() {
        
        }
        
        public void DestroyClosestApple() {
           int r = -1;
           int i = 0;
           for (Thing t : known) {
              if (closestApple != null) 
                 if (t.getName().equals(closestApple.getName())) r = i;
              i++;
           }   
           if (r != -1) known.remove(r);
           closestApple = null;
        }

}
