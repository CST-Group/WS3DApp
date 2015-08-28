/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;
import codelets.behaviors.EatClosestApple;
import codelets.behaviors.Forage;
import codelets.behaviors.GoToClosestApple;
import codelets.motor.HandsActionCodelet;
import codelets.motor.LegsActionCodelet;
import codelets.perception.AppleDetector;
import codelets.perception.ClosestAppleDetector;
import codelets.sensors.InnerSense;
import codelets.sensors.Vision;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import memory.CreatureInnerSense;
import support.MindView;
import ws3dproxy.model.Thing;

/**
 *
 * @author rgudwin
 */
public class AgentMind {
    
    private static int creatureBasicSpeed=1;
    private static int reachDistance=50;
    
    public AgentMind(Environment env) {
                // Create RawMemory and Coderack
                Mind m = new Mind();
                //RawMemory rawMemory=RawMemory.getInstance();
	        //CodeRack codeRack=CodeRack.getInstance();
                
                // Declare Memory Objects
	        MemoryObject legsMO;
	        MemoryObject handsMO;
                MemoryObject visionMO;
                MemoryObject innerSenseMO;
                MemoryObject closestAppleMO;
                MemoryObject knownApplesMO;
                
                //Initialize Memory Objects
                legsMO=m.createMemoryObject("LEGS");
		handsMO=m.createMemoryObject("HANDS");
                List<Thing> vision_list = Collections.synchronizedList(new ArrayList<Thing>());
		visionMO=m.createMemoryObject("VISION",vision_list);
                CreatureInnerSense cis = new CreatureInnerSense();
		innerSenseMO=m.createMemoryObject("INNER", cis);
                Thing closestApple = null;
                closestAppleMO=m.createMemoryObject("CLOSEST_APPLE", closestApple);
                List<Thing> knownApples = Collections.synchronizedList(new ArrayList<Thing>());
                knownApplesMO=m.createMemoryObject("KNOWN_APPLES", knownApples);
                
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
                m.insertCodelet(vision); //Creates a vision sensor
		
		Codelet innerSense=new InnerSense(env.c);
		innerSense.addOutput(innerSenseMO);
                m.insertCodelet(innerSense); //A sensor for the inner state of the creature
		
		// Create Actuator Codelets
		Codelet legs=new LegsActionCodelet(env.c);
		legs.addInput(legsMO);
                m.insertCodelet(legs);

		Codelet hands=new HandsActionCodelet(env.c);
		hands.addInput(handsMO);
                m.insertCodelet(hands);
		
		// Create Perception Codelets
                Codelet ad = new AppleDetector();
                ad.addInput(visionMO);
                ad.addOutput(knownApplesMO);
                m.insertCodelet(ad);
                
		Codelet closestAppleDetector = new ClosestAppleDetector();
		closestAppleDetector.addInput(knownApplesMO);
		closestAppleDetector.addInput(innerSenseMO);
		closestAppleDetector.addOutput(closestAppleMO);
                m.insertCodelet(closestAppleDetector);
		
		// Create Behavior Codelets
		Codelet goToClosestApple = new GoToClosestApple(creatureBasicSpeed,reachDistance);
		goToClosestApple.addInput(closestAppleMO);
		goToClosestApple.addInput(innerSenseMO);
		goToClosestApple.addOutput(legsMO);
                m.insertCodelet(goToClosestApple);
		
		Codelet eatApple=new EatClosestApple(reachDistance);
		eatApple.addInput(closestAppleMO);
		eatApple.addInput(innerSenseMO);
		eatApple.addOutput(handsMO);
                eatApple.addOutput(knownApplesMO);
                m.insertCodelet(eatApple);
                
                Codelet forage=new Forage();
		forage.addInput(knownApplesMO);
                forage.addOutput(legsMO);
                m.insertCodelet(forage);
		
		// Start Cognitive Cycle
		m.start(); 
    }             
    
}
