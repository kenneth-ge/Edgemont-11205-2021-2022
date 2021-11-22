/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rover.ruckus.prototype;

/**
 *
 * @author kenny ge
 */
public class Move implements Operation {

    float originalX = 0, originalY;
    float distTravelled, target;
    public Prototype p;
    
    public Move(float dist, Prototype p){
        this.target = dist;
        this.p = p;
    }
    
    @Override
    public boolean complete() {
        return distTravelled >= target;
    }

    @Override
    public void executeFrame() {
        if(!complete()){
            float delta = p.setMovePower(1);
            distTravelled += delta;
            Handler.updatePolarCartesianDelta(delta);
        }
    }
    
}
