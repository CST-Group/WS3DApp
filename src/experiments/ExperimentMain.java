/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package experiments;

import br.unicamp.cogsys.core.entities.CodeRack;
import br.unicamp.cogsys.core.entities.Codelet;
import br.unicamp.cogsys.core.entities.MemoryObject;
import br.unicamp.cogsys.core.entities.RawMemory;
import actuators.HandsActionCodelet;
import actuators.LegsActionCodelet;
import behaviors.EatClosestApple;
import behaviors.Forage;
import behaviors.GoToClosestApple;
import environments.EnvironmentMain;
import memory.CreatureInnerSense;
import perception.AppleDetector;
import perception.ClosestAppleDetector;
import sensors.InnerSense;
import sensors.Vision;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import windows.MindView;
import ws3dproxy.model.Thing;


/**
 *
 * @author rgudwin
 */
public class ExperimentMain {
	
	private static int creatureBasicSpeed=1;
	private static int reachDistance=50;
        
        public Logger logger = Logger.getLogger(ExperimentMain.class.getName());
        
        
        public ExperimentMain() {
                //WS3DProxy.logger.setLevel(Level.SEVERE);
                
                // Create Environment
                EnvironmentMain env=new EnvironmentMain(); //Creates only a creature and some apples
		
                // Create RawMemory and Coderack
                RawMemory rawMemory=RawMemory.getInstance();
	        CodeRack codeRack=CodeRack.getInstance();
                
                // Declare Memory Objects
	        MemoryObject legsMO;
	        MemoryObject handsMO;
                MemoryObject visionMO;
                MemoryObject innerSenseMO;
                MemoryObject closestAppleMO;
                MemoryObject knownApplesMO;
                
                //Initialize Memory Objects
                legsMO=rawMemory.createMemoryObject("LEGS");
		handsMO=rawMemory.createMemoryObject("HANDS");
                List<Thing> vision_list = Collections.synchronizedList(new ArrayList<Thing>());
		visionMO=rawMemory.createMemoryObject("VISION",List.class, vision_list);
                CreatureInnerSense cis = new CreatureInnerSense();
		innerSenseMO=rawMemory.createMemoryObject("INNER", CreatureInnerSense.class, cis);
                Thing closestApple = null;
                closestAppleMO=rawMemory.createMemoryObject("CLOSEST_APPLE", Thing.class, closestApple);
                List<Thing> knownApples = Collections.synchronizedList(new ArrayList<Thing>());
                knownApplesMO=rawMemory.createMemoryObject("KNOWN_APPLES", List.class, knownApples);
                
                // Create and Populate MindViewer
                MindView mv = new MindView("MindView");
                mv.addMO(knownApplesMO);
                mv.addMO(visionMO);
                mv.addMO(closestAppleMO);
                mv.addMO(innerSenseMO);
                mv.addMO(handsMO);
                mv.addMO(legsMO);
                mv.StartTimer();
                mv.setVisible(true);
		
		// Create Sensor Codelets	
		Codelet vision=new Vision(env.c);
		vision.addOutput(visionMO);
                codeRack.insertCodelet(vision); //Creates a vision sensor
		
		Codelet innerSense=new InnerSense(env.c);
		innerSense.addOutput(innerSenseMO);
                codeRack.insertCodelet(innerSense); //A sensor for the inner state of the creature
		
		// Create Actuator Codelets
		Codelet legs=new LegsActionCodelet(env.c);
		legs.addInput(legsMO);
                codeRack.insertCodelet(legs);

		Codelet hands=new HandsActionCodelet(env.c);
		hands.addInput(handsMO);
                codeRack.insertCodelet(hands);
		
		// Create Perception Codelets
                Codelet ad = new AppleDetector();
                ad.addInput(visionMO);
                ad.addOutput(knownApplesMO);
                codeRack.insertCodelet(ad);
                
		Codelet closestAppleDetector = new ClosestAppleDetector();
		closestAppleDetector.addInput(knownApplesMO);
		closestAppleDetector.addInput(innerSenseMO);
		closestAppleDetector.addOutput(closestAppleMO);
                codeRack.insertCodelet(closestAppleDetector);
		
		// Create Behavior Codelets
		Codelet goToClosestApple = new GoToClosestApple(creatureBasicSpeed,reachDistance);
		goToClosestApple.addInput(closestAppleMO);
		goToClosestApple.addInput(innerSenseMO);
		goToClosestApple.addOutput(legsMO);
                codeRack.insertCodelet(goToClosestApple);
		
		Codelet eatApple=new EatClosestApple(reachDistance);
		eatApple.addInput(closestAppleMO);
		eatApple.addInput(innerSenseMO);
		eatApple.addOutput(handsMO);
                eatApple.addOutput(knownApplesMO);
                codeRack.insertCodelet(eatApple);
                
                Codelet forage=new Forage();
		forage.addInput(knownApplesMO);
                forage.addOutput(legsMO);
                codeRack.insertCodelet(forage);
		
		// Start Cognitive Cycle
		codeRack.start(); 
            
        }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExperimentMain em = new ExperimentMain();
	}

}
