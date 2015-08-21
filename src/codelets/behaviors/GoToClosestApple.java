package codelets.behaviors;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.json.JSONException;
import org.json.JSONObject;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import memory.CreatureInnerSense;
import ws3dproxy.model.Thing;

public class GoToClosestApple extends Codelet {

	private MemoryObject closestAppleMO;
	private MemoryObject selfInfoMO;
	private MemoryObject legsMO;
	private int creatureBasicSpeed;
	private double reachDistance;

	public GoToClosestApple(int creatureBasicSpeed, int reachDistance) {
		this.creatureBasicSpeed=creatureBasicSpeed;
		this.reachDistance=reachDistance;
	}

	@Override
	public void accessMemoryObjects() {
		closestAppleMO=this.getInput("CLOSEST_APPLE");
		selfInfoMO=this.getInput("INNER");
		legsMO=this.getOutput("LEGS");
	}

	@Override
	public void proc() {
		// Find distance between creature and closest apple
		//If far, go towards it
		//If close, stops

		//String appleInfo = closestAppleMO.getInfo();
                Thing closestApple = (Thing) closestAppleMO.getI();
		String selfInfo= selfInfoMO.getInfo();
                CreatureInnerSense cis = (CreatureInnerSense) selfInfoMO.getI();
                //System.out.println("GoToClosestApple: "+appleInfo+" "+selfInfo);

		if(closestApple != null)
		{
			JSONObject jsonAppleInfo=null;
			String appleName="";
			double appleX=0;
			double appleY=0;
			try {
				//jsonAppleInfo = new JSONObject(appleInfo);
				//appleName=jsonAppleInfo.getString("NAME");
				//appleX=jsonAppleInfo.getDouble("X");
				//appleY=jsonAppleInfo.getDouble("Y");
                                appleName = closestApple.getName();
                                appleX = closestApple.getX1();
                                appleY = closestApple.getY1();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String[] selfInfoArray=selfInfo.split(" ");

			String selfName=selfInfoArray[0];
			double selfX=cis.position.getX();
			double selfY=cis.position.getY();

			Point2D pApple = new Point();
			pApple.setLocation(appleX, appleY);

			Point2D pSelf = new Point();
			pSelf.setLocation(selfX, selfY);

			double distance = pSelf.distance(pApple);
			JSONObject message=new JSONObject();
			try {
				if(distance>reachDistance){ //Go to it
                                        message.put("ACTION", "GOTO");
					message.put("X", (int)appleX);
					message.put("Y", (int)appleY);
                                        message.put("SPEED", creatureBasicSpeed);	

				}else{//Stop
					message.put("ACTION", "GOTO");
					message.put("X", (int)appleX);
					message.put("Y", (int)appleY);
                                        message.put("SPEED", 0.0);	
				}
				legsMO.updateInfo(message.toString());
//				System.out.println(message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
//			JSONObject message=new JSONObject();
//			try {
//				message.put("ACTION", "GOTO");
//				message.put("X", "0");
//				message.put("Y", "0");
//                                message.put("SPEED", 0.0);
//				legsMO.updateInfo(message.toString());
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
         //System.out.println("Command: "+legsMO.getInfo());

	}//end proc
        
        @Override
        public void calculateActivation() {
        
        }

}
