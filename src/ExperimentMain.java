/*****************************************************************************
 * Copyright 2007-2015 DCA-FEEC-UNICAMP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *    Klaus Raizer, Andre Paraense, Ricardo Ribeiro Gudwin
 *****************************************************************************/

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rgudwin
 */
public class ExperimentMain {
	
	
        
        public Logger logger = Logger.getLogger(ExperimentMain.class.getName());
        
        
        public ExperimentMain() {
                //WS3DProxy.logger.setLevel(Level.SEVERE);
                Logger.getLogger("codelets").setLevel(Level.SEVERE);
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
