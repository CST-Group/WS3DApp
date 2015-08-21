package codelets.motor;


import org.json.JSONException;
import org.json.JSONObject;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.Random;
import ws3dproxy.model.Creature;

/**
 *  Hands Action Codelet monitors working storage for instructions and acts on the World accordingly.
 *  
 * @author klaus
 *
 */


public class HandsActionCodelet extends Codelet{

	private MemoryObject handsMO;
	private String previousHandsAction="";
        private Creature c;
        private Random r = new Random();

	public HandsActionCodelet(Creature nc) {
                c = nc;
	}
	
        @Override
	public void accessMemoryObjects() {
		handsMO=this.getInput("HANDS");
	}
	public void proc() {
            
                String command = handsMO.getInfo();

		if(!command.equals("") && (!command.equals(previousHandsAction))){
			JSONObject jsonAction;
			try {
				jsonAction = new JSONObject(command);
				if(jsonAction.has("ACTION") && jsonAction.has("OBJECT")){
					String action=jsonAction.getString("ACTION");
					String objectName=jsonAction.getString("OBJECT");
					if(action.equals("PICKUP")){
                                                try {
                                                 c.putInSack(objectName);
                                                } catch (Exception e) {
                                                    
                                                } 
						System.out.println("Sending Put In Sack command to agent:****** "+objectName+"**********");							
						
						
						//							}
					}
					if(action.equals("EATIT")){
                                                try {
                                                 c.eatIt(objectName);
                                                } catch (Exception e) {
                                                    
                                                }
						System.out.println("Sending Eat command to agent:****** "+objectName+"**********");							
					}
					if(action.equals("BURY")){
                                                try {
                                                 c.hideIt(objectName);
                                                } catch (Exception e) {
                                                    
                                                }
						System.out.println("Sending Bury command to agent:****** "+objectName+"**********");							
					}
				}
//                                else if (jsonAction.has("ACTION")) {
//                                    int x=0,y=0;
//                                    String action=jsonAction.getString("ACTION");
//                                    if(action.equals("FORAGE")){
//                                                try {
//                                                      x = r.nextInt(600);
//                                                      y = r.nextInt(800);
//                                                 c.moveto(1, x,y );
//                                                } catch (Exception e) {
//                                                    
//                                                }
//						System.out.println("Sending Forage command to agent:****** ("+x+","+y+") **********");							
//					}
//                                }
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
//		System.out.println("OK_hands");
		previousHandsAction=handsMO.getInfo();
	}//end proc

    @Override
    public void calculateActivation() {
        
    }


}
