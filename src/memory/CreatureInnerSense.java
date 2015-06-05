/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memory;

import java.awt.Polygon;
import ws3dproxy.model.WorldPoint;

/**
 *
 * @author rgudwin
 */
public class CreatureInnerSense {
    public WorldPoint position;
    public double pitch;
    public double fuel;
    public Polygon FOV;
    
    public String toString() {
        if (position != null)
            return("Position: "+(int)position.getX()+","+(int)position.getY()+" Pitch: "+pitch+" Fuel: "+fuel);
        else 
            return("Position: null,null"+" Pitch: "+pitch+" Fuel: "+fuel);
    }
}

