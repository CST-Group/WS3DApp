package actuators;


import org.json.JSONObject;

import br.unicamp.cogsys.core.entities.Codelet;
import br.unicamp.cogsys.core.entities.MemoryObject;
import java.util.Random;
import org.json.JSONException;
import ws3dproxy.model.Creature;

/**
 *  Legs Action Codelet monitors working storage for instructions and acts on the World accordingly.
 *  
 * @author klaus
 *
 */

public class LegsActionCodelet extends Codelet{

	private MemoryObject legsActionMO;
	private double previousTargetx=0;
	private double previousTargety=0;
	private String previousLegsAction="";
        private Creature c;
        double old_angle = 0;

	public LegsActionCodelet(Creature nc) {
		c = nc;
	}
	
	@Override
	public void accessMemoryObjects() {
		legsActionMO=this.getInput("LEGS");
	}
	
	@Override
	public void proc() {
            
                String comm = legsActionMO.getInfo();
                if (comm == null) comm = "";
                Random r = new Random();
		
		if(!comm.equals("") ){
			
			try {

				JSONObject command=new JSONObject(comm);
                                if (command.has("ACTION")) {
                                    int x=0,y=0;
                                    String action=command.getString("ACTION");
                                    if(action.equals("FORAGE")){
                                                try {
                                                      x = r.nextInt(600);
                                                      y = r.nextInt(800);
                                                 c.moveto(1,x,y);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
						System.out.println("Sending Forage command to agent:****** ("+x+","+y+") **********");							
					}
                                    else if(action.equals("GOTO")){
                                        if (!comm.equals(previousLegsAction)) {
                                            double speed=command.getDouble("SPEED");
					    double targetx=command.getDouble("X");
					    double targety=command.getDouble("Y");
					    System.out.println("Sending move command to agent: ["+targetx+","+targety+"]");
                                            try {
                                                 c.moveto(speed, targetx, targety);
                                            } catch(Exception e) {
                                                 e.printStackTrace();
                                            }
					    previousTargetx=targetx;
					    previousTargety=targety;
                                        }   
				     }else{
					System.out.println("Sending stop command to agent");
                                        try {
                                             c.moveto(0,0,0);
                                        } catch(Exception e) {
                                            e.printStackTrace();
                                        }  
				     }
                                }
				
				
			} catch (JSONException e) {e.printStackTrace();}
		}
		previousLegsAction=legsActionMO.getInfo();
	}//end proc

    @Override
    public void calculateActivation() {
        
    }


}
