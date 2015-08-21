/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import br.unicamp.cst.core.entities.CodeRack;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.RawMemory;
import codelets.motor.HandsActionCodelet;
import codelets.motor.LegsActionCodelet;
import codelets.behaviors.EatClosestApple;
import codelets.behaviors.Forage;
import codelets.behaviors.GoToClosestApple;
import memory.CreatureInnerSense;
import codelets.perception.AppleDetector;
import codelets.perception.ClosestAppleDetector;
import codelets.sensors.InnerSense;
import codelets.sensors.Vision;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import support.MindView;
import ws3dproxy.model.Thing;


/**
 *
 * @author rgudwin
 */
public class ExperimentMain {
	
	
        
        public Logger logger = Logger.getLogger(ExperimentMain.class.getName());
        
        
        public ExperimentMain() {
                //WS3DProxy.logger.setLevel(Level.SEVERE);
                
                // Create Environment
                Environment env=new Environment(); //Creates only a creature and some apples
		AgentMind a = new AgentMind(env);  // Creates the Agent Mind and start it                
            
        }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExperimentMain em = new ExperimentMain();
	}

}
